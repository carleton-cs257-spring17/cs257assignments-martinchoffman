/**
 * Controller.java
 * Chris Tordi & Martin Hoffman, 25 May 2017
 *
 * The controller for something
 */

package sample;

import com.sun.org.apache.xerces.internal.dom.ChildNode;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements EventHandler<KeyEvent> {
    private ArrayList<Ball> enemyList = new ArrayList<Ball>();
    private Boolean started = false;

    final private double FRAMES_PER_SECOND = 60.0;

    final private double TILE_SIZE = 30.0;
    final private double TILE_PADDING = 10.0;

    @FXML private AnchorPane gameBoard;
	@FXML private Base base;
	@FXML private Label waveLabel;
	@FXML private Label moneyLabel;
	@FXML private Label baseHealthLabel;

	private Timer timer;
	private boolean paused;
	private boolean peacePhase;
	private int score;
	private int money;
    private int wave;
    private int enemyLimit;
    private int numEnemy;

    public Controller() {
        this.paused = false;
		this.peacePhase = true;

		this.money = 0;
		this.wave = 0;

        this.enemyLimit = 1;
        this.numEnemy = 0;
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

    public void initialize() {
		this.baseHealthLabel.setText(String.format("Base Health: %d", base.getHealth()));
		this.moneyLabel.setText(String.format("Money: %d", this.money));
		this.waveLabel.setText(String.format("Wave: %d", this.wave));
    }

    private void updateAnimation() {
		if (started == false) {
			tilePane();
			started = true;
        }

		this.moneyLabel.setText(String.format("Money: %d", this.money));

		for (Ball ball: enemyList) {
            double ballCenterX = ball.getCenterX() + ball.getLayoutX();
            double ballCenterY = ball.getCenterY() + ball.getLayoutY();
            double ballRadius = ball.getRadius();

            // Bounce off walls
            double ballVelocityX = ball.getVelocityX();
            double ballVelocityY = ball.getVelocityY();

            if (ballCenterX + ballRadius >= this.gameBoard.getWidth() - 60 && ballVelocityX > 0) {
                ball.setVelocityX(0);
                ball.setVelocityY(3);
                if (this.numEnemy < this.enemyLimit) {
                    enemyList.add(createNewBall());
                    this.numEnemy++;

                }
            } else if (ballCenterX - ballRadius < 50 && ballVelocityX < 0) {
                ball.setVelocityX(0);
                ball.setVelocityY(3);
            } else if (ballCenterY + ballRadius >= 350 && ballCenterX + ballRadius > 150 && ballVelocityY > 0) {
                ball.setVelocityY(0);
                ball.setVelocityX(-3);
            } else if (ballCenterY - ballRadius >= 600 && ballVelocityY > 0) {
                ball.setVelocityY(0);
                ball.setVelocityX(3);
            }  else if (ballCenterX + ballRadius >= this.gameBoard.getWidth() - 140 && ballCenterY - ballRadius >= 600 && ballVelocityX > 0) {
                baseHit (ball);
                enemyList.remove(ball);
            }

            // Move the sprite.
            ball.step();
        }
    }

    private void tilePane() {
    	GridPane grid1 = createNewTilePane(5, 80, 1045, 280);
		GridPane grid2 = createNewTilePane(117, 370, 1157, 570);

		gameBoard.getChildren().add(grid1);
		gameBoard.getChildren().add(grid2);
	}

    private GridPane createNewTilePane(double left_x, double top_y, double right_x, double bot_y) {
		GridPane grid = new GridPane();
		grid.setLayoutX(left_x);
		grid.setLayoutY(top_y);

		grid.setHgap(TILE_PADDING);
		grid.setVgap(TILE_PADDING);

		grid.setId("grid");

		Image img = new Image("file:res/inactiveTower.png");
		double width = right_x - left_x;
		double height = bot_y - top_y;
		for (int i = 0; i < width / (TILE_PADDING + TILE_SIZE); i ++) {
			for (int j = 0; j < height / (TILE_PADDING + TILE_SIZE); j++) {
				ImageView imgV = new ImageView();
				imgV.setImage(img);
				imgV.setFitHeight(TILE_SIZE);
				imgV.setFitWidth(TILE_SIZE);
				imgV.setId("tile");
				grid.add(imgV, i, j);
			}
		}
		return grid;
	}

    private Ball createNewBall() {
        Ball ball = new Ball();
        ball.setFill(Color.BLUE);
        ball.setCenterX(50);
        ball.setCenterY(50);
        ball.setVelocityX(3);
        ball.setVelocityY(0);
        ball.setRadius(15);
        gameBoard.getChildren().add(ball);

        return ball;
    }

    private void baseHit(Ball ball) {
        gameBoard.getChildren().remove(ball);
        if (base.getHealth() <= 0) {
			gameBoard.getChildren().remove(base);
		} else {
			base.damage();
		}
        this.money = this.money + 15;
        this.baseHealthLabel.setText(String.format("Base Health: %d", base.getHealth()));
        roundOverCheck(enemyList);
    }

    public void roundOverCheck(ArrayList<Ball> enemyList) {
        if (enemyList.size() == 1) {
            System.out.println("round over");
            this.timer.cancel();
            this.peacePhase = true;
        }
    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }

	// Pauses game and brings up menu
    public void onMenuButton(ActionEvent actionEvent) {
        if (this.paused == false) {
            this.timer.cancel();
            this.paused = true;
        } else {
            this.startTimer();
            this.paused = false;
        }
    }

    /* Facilitates buying towers feature
     * updates money
     * Lets user place tower
     */
    private boolean clicked = false;
    public void onBuyTurretButton(ActionEvent actionEvent) {
		if (clicked) {
			Turret turret = new Turret();
			turret.setPos(CLICKED_TILE);
			// Get tile index in Grid Pane and change image
		} else {
			Turret turret = new Turret();
			this.money -= turret.getCost();
			// Make available tiles visible
		}
		clicked = !clicked;
	}

    /* Triggers waves of enemies
     * Determines number of enemies in each wave
     * Checks to make sure previous wave is over
     */
    public void onWaveButton(ActionEvent actionEvent) {
        if (this.paused == false && peacePhase == true) {

            this.wave++;
            this.numEnemy = 0;
            this.enemyLimit = this.enemyLimit * this.wave + 1;
            this.waveLabel.setText(String.format("Wave: %d", this.wave));
            this.startTimer();
            peacePhase = false;
            enemyList.add(createNewBall()); //first ball of wave
        }

	}
}
