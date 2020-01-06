package game;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField bet_input;
    @FXML
    private Button bet_submit;
    @FXML
    private Label player_bankroll;
    @FXML
    private Label opp_bankroll;


    private Poker game = new Poker();
    private final Stage thisStage;

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
        moves.setEditable(false);
        check.setVisible(false);
        raise.setVisible(false);
        fold.setVisible(false);
        clearTable();
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

        raise.setOnAction(e ->{

        });

        fold.setOnAction(e -> fold());

        bet_submit.setOnAction(e ->{
            if(bet_input.getText() == null | checkBet(bet_input.getText())){
                game.setPlayerBet(Integer.parseInt(bet_input.getText())); //make this cleaner later, by making checkBet input string & return int
                setBetControlVisibility(false);
                if(game.getPhase() == 1){                       //If it is the start of the game,
                    try {
                        setBets();                                  //take and set bets , like normal
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        deal();                                 //And also deal the cards
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
                else {                                          //If it is not the start of the game
                    try {
                        setBets();                                 //No need to deal cards
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        raise.setOnAction(e -> {
            try {
                raise();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void check() throws InterruptedException, FileNotFoundException {
        moves.appendText("You check\n");
        if(!game.playerCheck()){
            //playerCheck() returns a boolean, opponentRaise
            //if false, CPU calls
            //if true. CPU Raises
            moves.appendText("CPU checks\n");
            continueGame();
        } else {
            //player checks, opponent raises

        }
    }

    private void continueGame() throws FileNotFoundException {
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

    private void compare() throws FileNotFoundException {
        oppcard1.setImage(new Image(new FileInputStream(game.getOpponentCards().get(0).getImage())));
        System.out.println("Opp card 1: " + game.getOpponentCards().get(0).getImage());
        oppcard2.setImage(new Image(new FileInputStream(game.getOpponentCards().get(1).getImage())));
        game.compareHands();
        endGame();
    }

    private void turn() throws FileNotFoundException {
        clearBets();
        Card turnCard = game.turn();
        turn.setImage(new Image(new FileInputStream(turnCard.getImage())));
    }

    private void river() throws FileNotFoundException {
        clearBets();
        Card riverCard = game.river();
        river.setImage(new Image(new FileInputStream(riverCard.getImage())));
    }

    private void flop() throws FileNotFoundException {
        clearBets();
        ArrayList<Card> flopCards = game.flop();
        flop1.setImage(new Image(new FileInputStream(flopCards.get(0).getImage())));
        flop2.setImage(new Image(new FileInputStream(flopCards.get(1).getImage())));
        flop3.setImage(new Image(new FileInputStream(flopCards.get(2).getImage())));

    }

    private void raise() throws InterruptedException {
        moves.appendText("Player raises $" + game.getPlayerBet() + "\n");
        setBetControlVisibility(true);
        setButtonVisibility(false);
        if(!game.playerRaise()){
            moves.appendText("CPU Calls\n");
        } else{
            moves.appendText("CPU Raises\n");
        }

    }

    private void fold(){
        game.playerFold();
        endGame();
    }

    private void play() throws IOException {

        player_bankroll.setText("$" + game.getPlayerMoney());
        opp_bankroll.setText("$" + game.getOpponentMoney());
        pot_total.setText("");
        play.setVisible(false);
        clearTable();
        moves.appendText("");
        game.play();
        setBetControlVisibility(true);
    }

    private void deal() throws FileNotFoundException {
        setBets();
        ArrayList<ArrayList<Card>> dealtCards = game.deal();
        playercard1.setImage(new Image(new FileInputStream(dealtCards.get(0).get(0).getImage())));
        playercard2.setImage(new Image(new FileInputStream(dealtCards.get(0).get(1).getImage())));
        oppcard1.setImage(new Image(new FileInputStream("src\\resources\\FACE_DOWN_CARD.png")));
        oppcard2.setImage(new Image(new FileInputStream("src\\resources\\FACE_DOWN_CARD.png")));
        clearBets();
    }

    private void clearTable() throws FileNotFoundException {
        setBetControlVisibility(false);
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

    private void setBets() throws FileNotFoundException {
        setButtonVisibility(true);
        if(game.didPlayerRaise()){
            game.setDidPlayerRaise(false);
            setButtonVisibility(true);
        }

        System.out.println(game.getOpponentBet());
        your_bet.setText("$" + game.getPlayerBet());
        opponent_bet.setText("$" + game.getOpponentBet());
        player_bankroll.setText("$" + game.getPlayerMoney());
        opp_bankroll.setText("$" + game.getOpponentMoney());
        pot_total.setText("$" + game.getPotTotal());
        continueGame();
    }

    private void clearBets(){
        your_bet.setText("");
        opponent_bet.setText("");

    }

    private void setBetControlVisibility(Boolean b){
        bet_input.setVisible(b);
        bet_submit.setVisible(b);
    }

    private void setButtonVisibility(boolean b){
        check.setVisible(b);
        raise.setVisible(b);
        fold.setVisible(b);
    }

    private void endGame(){
        moves.appendText(game.getGameOutcome());
        moves.setScrollTop(Double.MAX_VALUE);

        setButtonVisibility(false);
        play.setVisible(true);
    }

    private boolean checkBet(String a){
        return true;
    }
}
