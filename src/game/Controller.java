package game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

class Controller {
    @FXML
    private ImageView playercard1;
    @FXML
    private ImageView playercard2;
    @FXML
    private ImageView flop1;
    @FXML
    private ImageView flop2;
    @FXML
    private ImageView flop3;
    @FXML
    private ImageView turn;
    @FXML
    private ImageView river;
    @FXML
    private ImageView oppcard1;
    @FXML
    private ImageView oppcard2;
    @FXML
    private Label your_bet;
    @FXML
    private Label opponent_bet;
    @FXML
    private Button play;
    @FXML
    private Button check;
    @FXML
    private Button fold;
    @FXML
    private Button raise;
    @FXML
    private Label pot_total;
    @FXML
    private TextArea moves;

    private Poker game = new Poker();
    private final Stage thisStage;

    private String movesText;
    //TODO: Find a better way to keep track of the moves, because this is kinda awful

    Controller() throws IOException{
        this.thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/PokerScreen.fxml"));
        loader.setController(this);
        thisStage.setScene(new Scene(loader.load()));
        thisStage.setTitle("Poker");
    }

    void showStage(){
        thisStage.show();
    }

    @FXML
    private void initialize() throws IOException {
            clearTable();
            movesText = "";
            play.setOnAction(e -> {
                try {
                    play();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            check.setOnAction(e -> {
                try {
                    check();
                } catch (InterruptedException | FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

            raise.setOnAction(e -> {
                raise();
            });
    }

    private void check() throws InterruptedException, FileNotFoundException {
        movesText = movesText + "You check\n";
        moves.setText(movesText);
        if(!game.playerCheck()){
            movesText = movesText + "Opponent checks\n";
            moves.setText(movesText);
            switch(game.getPhase()){
                case 4://pre flop
                    flop();
                    break;
                case 7: //pre turn
                    turn();
                    break;
                case 10: //pre river
                    river();
                    break;
                case 13://compare hands
                    compare();
                    break;
            }
        }
    }

    private void compare(){
        //game.compare();
    }

    private void turn() throws FileNotFoundException {
        Card turnCard = game.turn();
        turn.setImage(new Image(new FileInputStream(turnCard.getImage())));
    }

    private void river() throws FileNotFoundException {
        Card riverCard = game.river();
        river.setImage(new Image(new FileInputStream(riverCard.getImage())));
    }

    private void flop() throws FileNotFoundException {
        ArrayList<Card> flopCards = game.flop();
        flop1.setImage(new Image(new FileInputStream(flopCards.get(0).getImage())));
        flop2.setImage(new Image(new FileInputStream(flopCards.get(1).getImage())));
        flop3.setImage(new Image(new FileInputStream(flopCards.get(2).getImage())));

    }

    private void raise(){

    }

    private void play() throws IOException {
        play.setVisible(false);
        game.play();
        setBets();
        ArrayList<ArrayList<Card>> dealtCards = game.deal();
        playercard1.setImage(new Image(new FileInputStream(dealtCards.get(0).get(0).getImage())));
        playercard2.setImage(new Image(new FileInputStream(dealtCards.get(0).get(1).getImage())));
        oppcard1.setImage(new Image(new FileInputStream("src\\resources\\FACE_DOWN_CARD.png")));
        oppcard2.setImage(new Image(new FileInputStream("src\\resources\\FACE_DOWN_CARD.png")));
    }

    private void clearTable() throws FileNotFoundException {
        //sets all card images to BLANK_CARD
        Image no_card = new Image(new FileInputStream("src\\resources\\BLANK_CARD.png"));
        flop1.setImage(no_card);
        flop2.setImage(no_card);
        flop3.setImage(no_card);
        turn.setImage(no_card);
        river.setImage(no_card);
        oppcard1.setImage(no_card);
        oppcard2.setImage(no_card);
        playercard1.setImage(no_card);
        playercard2.setImage(no_card);
    }

    private void setBets(){
        your_bet.setText("$" + game.getPlayerBet());
        opponent_bet.setText("$" + game.getOpponentBet());
        pot_total.setText("$" + game.getPotTotal());
    }
}
