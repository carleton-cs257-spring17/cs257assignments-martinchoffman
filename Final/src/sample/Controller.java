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
    @FXML private Ball ball;


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
        double ballCenterX = this.ball.getCenterX() + this.ball.getLayoutX();
        double ballCenterY = this.ball.getCenterY() + this.ball.getLayoutY();
        double ballRadius = this.ball.getRadius();


        // Bounce off walls
        double ballVelocityX = this.ball.getVelocityX();
        double ballVelocityY = this.ball.getVelocityY();

        if (ballCenterX + ballRadius >= this.gameBoard.getWidth() - 140  && ballVelocityX > 0) {
            this.ball.setVelocityX(0);
            this.ball.setVelocityY(3);


        } else if (ballCenterX - ballRadius < 50 && ballVelocityX < 0) {
            this.ball.setVelocityX(0);
            this.ball.setVelocityY(3);


        } else if (ballCenterY + ballRadius >= 350 && ballCenterX + ballRadius > 150 && ballVelocityY > 0) {
            this.ball.setVelocityY(0);
            this.ball.setVelocityX(-3);

        } else if (ballCenterY - ballRadius >= 600 && ballVelocityY > 0) {
            this.ball.setVelocityY(0);
            this.ball.setVelocityX(3);

        } else if (ballCenterX + ballRadius >= this.gameBoard.getWidth() - 140 && ballCenterY - ballRadius >= 600) {
            return;
        }

        // Move the sprite.
        this.ball.step();
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
