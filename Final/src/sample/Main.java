/**
 * Main.java
 * Chris Tordi & Martin Hoffman, 25 May 2017
 *
 * The main program for setting up a tower defense game.
 */

package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
        Parent root = (Parent)loader.load();
        HomeScreen homeScreen = loader.getController();
        root.setId("homeScreen");
        primaryStage.setTitle("");


        Scene scene = new Scene(root, 1170, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
