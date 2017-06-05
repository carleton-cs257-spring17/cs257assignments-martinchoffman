/**
 * Created by alextordi on 6/5/17.
 */
package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Creates Homescreen window
 */
public class HomeScreen {

    @FXML private AnchorPane homeScreen;

    public void initialize() {

    }

    public void onStartGame(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent)loader.load();
        Controller controller = loader.getController();
        root.setId("gameBoard");
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Tower Defense");

        Scene scene = new Scene(root, 1170, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
        closeWindow();


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    /**
     * Closes homescreen window
     */
    public void closeWindow() {
        Scene scene = homeScreen.getScene();
        Stage closing = new Stage();
        closing.setScene(scene);
        closing.hide();
    }
}


