package game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

public class blackjack_Controller extends Application {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    int chips = 10;
    Stage thisStage;
    int playerCardIndex;
    int dealerCardIndex;
    int hitStandButtonIndex;
    GridPane cards = new GridPane();
    BlackJack game = new BlackJack();
    Scene thisScene;
    BorderPane border = new BorderPane();
    HBox header = new HBox();
    FlowPane menu = new FlowPane();

    Text yourCards = new Text("Your cards");
    Text dealerCards = new Text("Dealer cards");

    ImageView playerCard1 = new ImageView(new Image(new FileInputStream("src\\resources\\BLANK_CARD.png")));
    ImageView playerCard2 = new ImageView(new Image(new FileInputStream("src\\resources\\BLANK_CARD.png")));
    ImageView dealerCardUp = new ImageView(new Image(new FileInputStream("src\\resources\\BLANK_CARD.png")));
    ImageView dealerCardDown = new ImageView(new Image(new FileInputStream("src\\resources\\BLANK_CARD.png")));
    Button play = new Button("Play");
    Button hit = new Button("Hit");
    Button stand = new Button("Stand");


    public blackjack_Controller() throws FileNotFoundException {
        thisStage = new Stage();
        initUI(thisStage);
        thisStage.setScene(thisScene);
        thisStage.setTitle("Blackjack");

    }


    public void showStage(){
        thisStage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public void initUI(Stage stage) {
        header.setPadding(new Insets(10,10,10,10));
        header.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(10, 10, 10, 10));
        menu.setAlignment(Pos.CENTER);

        cards.setStyle("-fx-background-color: green");
        cards.setPadding(new Insets(10, 10, 10, 10));
        cards.setAlignment(Pos.CENTER);
        cards.setVgap(30);
        cards.setHgap(10);
        cards.setMinSize(375, 200);



        play.setOnAction(event -> {
            play();
        });

        hit.setOnAction(event -> {
            Card newCard = game.hit();
            String newCardString = newCard.getName();
            ImageView playerCard3 = null;
            try {
                playerCard3 = new ImageView(new Image(new FileInputStream("src\\resources\\"+newCardString+".png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //shows new card
            cards.add(playerCard3, playerCardIndex, 3);
            playerCardIndex++;

            //moves hit/stand buttons relative to cards
            cards.getChildren().remove(hit);
            cards.getChildren().remove(stand);
            hitStandButtonIndex++;
            cards.add(hit, hitStandButtonIndex,5);
            cards.add(stand, hitStandButtonIndex,6);

            if(game.over21()){
                showDealerSecondCard();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("BUST! You lose!");
                alert.showAndWait();
                nextHand();
            }
        });

        stand.setOnAction(event -> {
            showDealerSecondCard();


            boolean dealerTurn = true;
            while(dealerTurn){
                Card newDealerCard = game.dealerHit();
                String newDealerCardString = newDealerCard.getName();
                ImageView newDCard = null;
                try {
                    newDCard = new ImageView(new Image(new FileInputStream("src\\resources\\"+newDealerCardString+".png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //shows new card
                cards.add(newDCard, dealerCardIndex, 0);
                dealerCardIndex++;
                dealerTurn = game.isDealerTurn();
            }
            if(game.dealerBust()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Dealer Bust! You win");
                alert.showAndWait();
                nextHand();

            } else {
                switch(game.compareDealerAndPlayer()){
                    case 0:
                        alert.setContentText("You lose!");
                        alert.showAndWait();
                        nextHand();
                        break;
                    case 1:
                        alert.setContentText("You win!");
                        alert.showAndWait();
                        nextHand();
                        break;
                    case 2:
                        alert.setContentText("Push");
                        alert.showAndWait();
                        nextHand();
                        break;
                }
            }
        });

        border.setCenter(menu);
        menu.getChildren().add(play);
        HBox quit = new HBox();
        quit.setPadding(new Insets(10));

        thisScene = new Scene(border, 800, 600);
        stage.setScene(thisScene);
    }



    public void play(){
        //When drawing new cards, this tells where the new card should be placed on the gui
        playerCardIndex = 2;
        dealerCardIndex = 2;
        hitStandButtonIndex = 2;
        header.getChildren().clear();
        border.setCenter(cards);

        //playerCard row index: 3
        //dealercard row index: 0
        cards.add(playerCard1, 0, 3);
        cards.add(playerCard2, 1, 3);
        cards.add(yourCards, 1,4);

        cards.add(dealerCardUp, 0, 0);
        cards.add(dealerCardDown, 1,0);
        cards.add(dealerCards, 1,1);

        cards.add(hit, hitStandButtonIndex,5);
        cards.add(stand, hitStandButtonIndex,6);

        placeBet();
        game.start();
        String p1 = game.getPlayerCards().get(0).getName();
        String p2 = game.getPlayerCards().get(1).getName();

        String d1 = game.getDealerCards().get(0).getName();
        String ddown = game.getDealerCards().get(1).getName();

        try {
            playerCard1.setImage(new Image(new FileInputStream("src\\resources\\"+p1+".png")));
            playerCard2.setImage(new Image(new FileInputStream("src\\resources\\"+p2+".png")));
            dealerCardUp.setImage(new Image(new FileInputStream("src\\resources\\"+d1+".png")));
            dealerCardDown.setImage(new Image(new FileInputStream("src\\resources\\FACE_DOWN_CARD.png")));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(game.blackjack()){
            alert.setContentText("BLACKJACK! You win!");
            alert.showAndWait();
            nextHand();
        }
    }

    private void showDealerSecondCard(){
        //Shows dealer second card
        Card dealerSecondCard = game.getDealerSecondCard();
        String dealerCardString = dealerSecondCard.getName();
        ImageView dealerCard2 = null;
        try {
            dealerCard2 = new ImageView(new Image(new FileInputStream("src\\resources\\"+dealerCardString+".png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        cards.add(dealerCard2, 1, 0);
        //end showing dealer second card
    }

    public void nextHand(){
        clearTable();
        //placeBet();
        setTable();
        if(game.blackjack()){
            alert.setContentText("BLACKJACK! You win!");
            alert.showAndWait();
            nextHand();
        }
    }

    void clearTable(){
        cards.getChildren().clear();
    }

    void setTable(){
        game.newGame();
        cards.add(playerCard1, 0, 3);
        cards.add(playerCard2, 1, 3);
        cards.add(yourCards, 1,4);

        cards.add(dealerCardUp, 0, 0);
        cards.add(dealerCardDown, 1,0);
        cards.add(dealerCards, 1,1);

        cards.add(hit, hitStandButtonIndex,5);
        cards.add(stand, hitStandButtonIndex,6);
        String p1 = game.getPlayerCards().get(0).getName();
        String p2 = game.getPlayerCards().get(1).getName();

        String d1 = game.getDealerCards().get(0).getName();
        String ddown = game.getDealerCards().get(1).getName();
        try {
            playerCard1.setImage(new Image(new FileInputStream("src\\resources\\"+p1+".png")));
            playerCard2.setImage(new Image(new FileInputStream("src\\resources\\"+p2+".png")));
            dealerCardUp.setImage(new Image(new FileInputStream("src\\resources\\"+d1+".png")));
            dealerCardDown.setImage(new Image(new FileInputStream("src\\resources\\FACE_DOWN_CARD.png")));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void placeBet(){
        String chipString = String.format("Balance: %d chips", chips);
        TextInputDialog dialog = new TextInputDialog(chipString);
        dialog.setTitle("Place bets");
        dialog.setContentText("Place your bet now:");
        Optional<String> result = dialog.showAndWait();
    }


}