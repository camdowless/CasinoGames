package game;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;



public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException{
        menu_Controller controller = new menu_Controller();
        controller.showStage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
