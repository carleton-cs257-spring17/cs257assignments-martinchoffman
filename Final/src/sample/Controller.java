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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
	@FXML private AnchorPane gameBoard;
	@FXML private Base base;
	@FXML private Label waveLabel;
	@FXML private Label moneyLabel;
	@FXML private Label baseHealthLabel;

	private ArrayList<Ball> enemyList = new ArrayList<Ball>();
	private List<GridPane> grids = new ArrayList<GridPane>();
	private List<Shot> shots = new ArrayList<Shot>();
    private Boolean started = false;

    final private double FRAMES_PER_SECOND = 60.0;

	final private double TILE_SIZE = 30.0;
    final private double TILE_PADDING = 10.0;

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
						shotAnimation();
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

    private int counter = 0;
    private void shotAnimation() {
    	if (counter % 30 == 0) {
			for (Node node : grids.get(0).getChildren()) {
				if (node instanceof Turret) {
					Shot shot = ((Turret) node).fire();
					shots.add(shot);
					gameBoard.getChildren().add(shot);
				}
			}
		}

		for (Shot shot : shots) {
    		if (shot.getCenterY() < 10 || shot.getCenterY() > 800) {
    			gameBoard.getChildren().remove(shot);
    			shots.remove(shot);
			}
    		shot.step();
    		collisionDectection();
		}

		counter++;
	}

	private void collisionDectection() {

    	for (Ball ball : enemyList) {
    		for (Shot shot : shots) {
    			if (shot.getCenterY() - shot.getRadius() < ball.getCenterY() + ball.getRadius() &&
						shot.getCenterY() + shot.getRadius() > ball.getCenterY() - ball.getRadius() &&
						shot.getCenterX() + shot.getRadius() > ball.getCenterX() - ball.getRadius() &&
						shot.getCenterX() - shot.getRadius() < ball.getCenterX() + ball.getRadius()) {
    				enemyList.remove(ball);
    				gameBoard.getChildren().remove(ball);
					roundOverCheck(enemyList);
				}
			}
		}
	}
	private int ballSpawner = 0;
    private void updateAnimation() {
		if (started == false) {
			tilePane();
			started = true;
        }

        if (ballSpawner % 100 == 0 && this.numEnemy < this.enemyLimit) {
			this.numEnemy++;
			enemyList.add(createNewBall());
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
               /* if (this.numEnemy < this.enemyLimit) {
                    enemyList.add(createNewBall());
                    this.numEnemy++;
                } */
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
				roundOverCheck(enemyList);
            }

            // Move the sprite.
            ball.step();
            ballSpawner++;
        }
    }

    private void tilePane() {
    	GridPane grid1 = createNewTilePane(5, 80, 1045, 280);
		//GridPane grid2 = createNewTilePane(117, 370, 1157, 570);

		this.grids.add(grid1);
		//this.grids.add(grid2);

		gameBoard.getChildren().add(grid1);
		//gameBoard.getChildren().add(grid2);
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

				int col = i;
				int row = j;
				imgV.setOnMouseClicked(c -> handle(c, row, col));

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

    }

    public void roundOverCheck(ArrayList<Ball> enemyList) {
        if (enemyList.size() < 1) {
            System.out.println("round over");
			gameBoard.getChildren().removeAll(shots);
			shots.clear();
			this.timer.cancel();
			this.peacePhase = true;
        }
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
    	clicked = !clicked;
	}

	public void handle(MouseEvent mouseEvent, int row, int col) {
		if (clicked == true) {
			Turret turret = new Turret();
			this.money -= turret.getCost();

			turret.setPosition(row, col, grids.get(0).getLayoutY(), grids.get(0).getLayoutX(), TILE_SIZE + TILE_PADDING);

			turret.setFitWidth(TILE_SIZE);
			turret.setFitHeight(TILE_SIZE);

			Image img = new Image("file:res/Tower.png");
			turret.setImage(img);
			gameBoard.getChildren().add(turret);
			grids.get(0).add(turret, col, row);
			System.out.println(turret.getPosition());

			for (Node child : grids.get(0).getChildren()) {
				if (GridPane.getRowIndex(child) == null) {
					System.out.println("Null " + child);
				} else if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
					Point2D point = turret.getPosition();

					// System.out.println(child);
					// System.out.println(point);

					Rectangle rect = new Rectangle();
					rect.setFill(Color.BLACK);
					rect.setWidth(10);
					rect.setHeight(10);
					rect.setLayoutX(point.getX());
					rect.setLayoutY(point.getY());
					gameBoard.getChildren().add(rect);
				}
			}
			clicked = !clicked;
		}
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
