/**
 * Controller.java
 * Chris Tordi & Martin Hoffman, 25 May 2017
 *
 * The controller for something
 */

package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements EventHandler<KeyEvent> {
    final private double FRAMES_PER_SECOND = 60.0;

    @FXML private Button pauseButton;
    @FXML private Label scoreLabel;
    @FXML private AnchorPane gameBoard;
    @FXML private Button menuButton;
    @FXML private Button waveButton;


    private int score;
    private boolean paused;
    private Timer timer;

    public Controller() {
        this.paused = false;
        this.score = 0;
    }

    public void initialize() {
        this.startTimer();
    }

    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateAnimation();
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    private void updateAnimation() {
    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }
	// Pauses game and brings up menu
    public void onMenuButton(ActionEvent actionEvent) {

    }
    /* Facilitates buying towers feature
     * updates money
     * Lets user place tower
     */
    public void onBuyTowerButton(ActionEvent actionEvent) {

    }
    /* Triggers waves of enemies
     * Determines number of enemies in each wave
     * Checks to make sure previous wave is over
     */
    public void onWaveButton(ActionEvent actionEvent) {

	}


}
