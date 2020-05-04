package game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

class menu_Controller {

    private final Stage thisStage;
    private Player player;

    @FXML
    private Label user;
    @FXML
    private Label money;
    @FXML
    private Button rules;
    @FXML
    private Button logout;
    @FXML
    private Button slots;
    @FXML
    private Button blackjack;
    @FXML
    private Button roulette;
    @FXML
    private Button solitaire;
    @FXML
    private Button poker;

    menu_Controller() throws IOException{
        thisStage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../game/FXML/main_screen.fxml"));
        loader.setController(this);
        player = new Player("", 100000);
        thisStage.setScene(new Scene(loader.load()));
        thisStage.setTitle("Menu Screen");
    }


    void showStage(){
        thisStage.show();
    }

    @FXML
    private void initialize(){

        user.setText(player.get_name());
        money.setText(player.get_money().toString());

       blackjack.setOnAction(e-> {
            try {
                open_blackjack();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
       });
        roulette.setOnAction(e-> {
            try {
                open_roulette();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        poker.setOnAction(e-> {
            try {
                open_poker();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void open_blackjack() throws Exception {
        blackjack_Controller controller = new blackjack_Controller();
        thisStage.hide();
        controller.showStage();
    }

    private void open_roulette() throws IOException {
        roulette_Controller controller = new roulette_Controller(this);
        thisStage.hide();
        controller.showStage();
    }

    private void open_poker() throws IOException {
        poker_Controller controller = new poker_Controller(this);
        thisStage.hide();
        controller.showStage();
    }


    Player get_player() {
        return player;
    }


}