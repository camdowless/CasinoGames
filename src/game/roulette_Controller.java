package game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

class roulette_Controller {

    private final Stage thisStage;
    private final menu_Controller menu_controller;
    private Roulette game = new Roulette();
    @FXML
    private ArrayList<Circle> wheel = new ArrayList<>();
    @FXML
    private Label winlose;
    @FXML
    private Circle green_0;
    @FXML
    private Circle black_28;
    @FXML
    private Circle red_9;
    @FXML
    private Circle black_26;
    @FXML
    private Circle red_30;
    @FXML
    private Circle black_11;
    @FXML
    private Circle red_7;
    @FXML
    private Circle black_20;
    @FXML
    private Circle red_32;
    @FXML
    private Circle black_17;
    @FXML
    private Circle red_5;
    @FXML
    private Circle black_22;
    @FXML
    private Circle red_34;
    @FXML
    private Circle black_15;
    @FXML
    private Circle red_3;
    @FXML
    private Circle black_24;
    @FXML
    private Circle red_36;
    @FXML
    private Circle black_13;
    @FXML
    private Circle red_1;
    @FXML
    private Circle green_00;
    @FXML
    private Circle red_27;
    @FXML
    private Circle black_10;
    @FXML
    private Circle red_25;
    @FXML
    private Circle black_29;
    @FXML
    private Circle red_12;
    @FXML
    private Circle black_8;
    @FXML
    private Circle red_19;
    @FXML
    private Circle black_31;
    @FXML
    private Circle red_18;
    @FXML
    private Circle black_6;
    @FXML
    private Circle red_21;
    @FXML
    private Circle black_33;
    @FXML
    private Circle red_16;
    @FXML
    private Circle black_4;
    @FXML
    private Circle red_23;
    @FXML
    private Circle black_35;
    @FXML
    private Circle red_14;
    @FXML
    private Circle black_2;
    @FXML
    private RadioButton selRed;
    @FXML
    private RadioButton selBlack;
    @FXML
    private RadioButton selGreen;
    @FXML
    private Button go;
    @FXML
    private Button close;
    @FXML
    private TextField bet;
    @FXML
    private Label user;
    @FXML
    private Label money;
    private Scene rouletteScene;
    private Player player;

    roulette_Controller(menu_Controller menu_controller) throws IOException {
        this.menu_controller = menu_controller;
        thisStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../game/FXML/roulette_screen.fxml"));
        loader.setController(this);
        Scene thisScene = new Scene(loader.load());
        rouletteScene = thisScene;
        thisStage.setScene(thisScene);
        thisStage.setTitle("Slots Screen");
    }

    void showStage() {
        thisStage.show();
    }

    @FXML
    private void initialize(){
        this.player = menu_controller.get_player();
        user.setText(player.get_name());
        money.setText("$"+player.get_money());
        wheel.add(green_0);
        wheel.add(black_28);
        wheel.add(red_9);
        wheel.add(black_26);
        wheel.add(red_30);
        wheel.add(black_11);
        wheel.add(red_7);
        wheel.add(black_20);
        wheel.add(red_32);
        wheel.add(black_17);
        wheel.add(red_5);
        wheel.add(black_22);
        wheel.add(red_34);
        wheel.add(black_15);
        wheel.add(red_3);
        wheel.add(black_24);
        wheel.add(red_36);
        wheel.add(black_13);
        wheel.add(red_1);
        wheel.add(green_00);
        wheel.add(red_27);
        wheel.add(black_10);
        wheel.add(red_25);
        wheel.add(black_29);
        wheel.add(red_12);
        wheel.add(black_8);
        wheel.add(red_19);
        wheel.add(black_31);
        wheel.add(red_18);
        wheel.add(black_6);
        wheel.add(red_21);
        wheel.add(black_33);
        wheel.add(red_16);
        wheel.add(black_4);
        wheel.add(red_23);
        wheel.add(black_35);
        wheel.add(red_14);
        wheel.add(black_2);

        makeCirclesDisappear();
        go.setOnAction(e -> spin());

        close.setOnAction(e ->{
            menu_controller.showStage();
            thisStage.close();
        });
    }

    private void spin()   {


        makeCirclesDisappear();
        String selection;
        if(selRed.isSelected()){
            selection = "red";
        } else if (selBlack.isSelected()){
            selection = "black";
        } else if (selGreen.isSelected()){
            selection = "green";
        }else{
            System.err.println("Must select a color to play");
            return;
        }
        if(checkBet()) {
            int playerBet = Integer.parseInt(bet.getText());
            int balanceBeforeSpin = player.get_money() - playerBet;
            player.setMoney(balanceBeforeSpin);
            money.setText("$"+player.get_money());
            String result = game.play(selection, playerBet);

            //result is a string, which is equal to a Circle's fx:id
            Circle resultCircle = (Circle) rouletteScene.lookup("#" + result);
            go.setDisable(true);
            animateSpin(resultCircle);

        }
        else{
            VBox box = new VBox();
            Text text = new Text("Insufficient funds");
            box.getChildren().add(text);
            Scene scene = new Scene(box);
            Stage popup = new Stage();
            popup.setScene(scene);
            popup.showAndWait();
            go.setDisable(false);
        }
    }

    private void makeCirclesDisappear(){
        for(Circle c : wheel){
            c.setVisible(false);
        }
    }


    private void displayWinnings(){
        int balanceAfterSpin = player.get_money() + game.getWinnings();
        player.setMoney(balanceAfterSpin);
        money.setText("$"+balanceAfterSpin);
        if(game.isAWin()){
            winlose.setText("You win:\n$"+game.getWinnings());
        }else{
            winlose.setText("You lose:\n$"+game.getBet());
        }
    }

    private void animateSpin(Circle result) {
        new Thread(() -> {
            for(int i = 0; i < 2; i++){
                ListIterator<Circle> it = wheel.listIterator();
                Circle start = it.next();
                start.setVisible(true);
                while(it.hasNext()){
                    Platform.runLater(() -> {
                        Circle prev = it.previous();
                        it.next();
                        Circle next = it.next();
                        prev.setVisible(false);
                        next.setVisible(true);

                    });
                    try{
                        Thread.sleep(50);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                black_2.setVisible(false);
                green_0.setVisible(true);
            }

            ListIterator<Circle> it = wheel.listIterator();
            final Circle[] current = {it.next()};

            while(!current[0].equals(result)){
                Platform.runLater(() -> {
                    Circle prev = it.previous();
                    current[0] = it.next();
                    Circle next = it.next();
                    if(!current[0].equals(result)) {
                        prev.setVisible(false);
                        next.setVisible(true);
                    }
                });
                try{
                    Thread.sleep(150);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            Platform.runLater(this::displayWinnings);
            go.setDisable(false);
        }).start();
    }

    private boolean checkBet(){
        if (isInteger(bet.getText())){
            return player.get_money() >= new Integer(bet.getText());
        } else {
            return false;
        }
    }

    private boolean isInteger(String b) {
        try {
            Integer.parseInt(b);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}