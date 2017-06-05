/**
 * Controller.java
 * Chris Tordi & Martin Hoffman, 25 May 2017
 *
 * The controller for our tower defense game. This program manages all game data, updates view, and runs game engine.
 */

package sample;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
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
    private int ballSpawner;

    public Controller() {
        this.paused = false;
		this.peacePhase = true;

		this.money = 30;
		this.wave = 0;

        this.enemyLimit = 1;
        this.numEnemy = 0;
        this.ballSpawner = 0;
    }

	/**
	 * Controls game time
	 */
	private void startTimer() {
        this.ballSpawner = 0;
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

	/**
	 * Initializes game stats to begin game
	 */
    public void initialize() {
		this.baseHealthLabel.setText(String.format("Base Health: %d", base.getHealth()));
		this.moneyLabel.setText(String.format("Money: %d", this.money));
		this.waveLabel.setText(String.format("Wave: %d", this.wave));


    }

	/**
	 * Updates position of shots using current shot position and current shot velocity
	 * Checks for collisions
	 */
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

	/**
	 * checks for collisions between enemy and shots
	 * If collision is detected then enemy health, gameboard, score, and money are updated
	 * Checks to see if round is over
	 */
	private void collisionDectection() {
    	for (Ball ball : enemyList) {
    		for (Shot shot : shots) {
    			if (shot.getCenterY() - shot.getRadius() < ball.getCenterY() + ball.getRadius() &&
						shot.getCenterY() + shot.getRadius() > ball.getCenterY() - ball.getRadius() &&
						shot.getCenterX() + shot.getRadius() > ball.getCenterX() - ball.getRadius() &&
						shot.getCenterX() - shot.getRadius() < ball.getCenterX() + ball.getRadius()) {
    				enemyList.remove(ball);
    				gameBoard.getChildren().remove(ball);
    				score += 10;
    				this.money += 2;
                    this.moneyLabel.setText(String.format("Money: %d", this.money));
					roundOverCheck(enemyList);
				}
			}
		}
	}

	/**
	 * Updates the position of each enemy unit on gameboard
	 * Uses current position and current velocity to determine new position
	 * monitors when new balls should be added during wave
	 * Creates tile panes
	 */
    private void updateAnimation() {
		if (started == false) {
			tilePane();
			started = true;
        }

		//adds new enemies during round
        if (ballSpawner % 100 == 0 && this.numEnemy <= this.enemyLimit) {
			enemyList.add(createNewBall());
            this.numEnemy++;

		}

		//updates position for all enemies in view
		for (Ball ball: enemyList) {
            double ballCenterX = ball.getCenterX() + ball.getLayoutX();
            double ballCenterY = ball.getCenterY() + ball.getLayoutY();
            double ballRadius = ball.getRadius();

            double ballVelocityX = ball.getVelocityX();
            double ballVelocityY = ball.getVelocityY();

            //allows enemies to navigate path
            if (ballCenterX + ballRadius >= this.gameBoard.getWidth() - 60 && ballCenterY - ballRadius < 400 && ballVelocityX > 0) {
                ball.setVelocityX(0);
                ball.setVelocityY(3);
            } else if (ballCenterX - ballRadius < 50 && ballVelocityX < 0) {
                ball.setVelocityX(0);
                ball.setVelocityY(3);
            } else if (ballCenterY + ballRadius >= 350 && ballCenterX + ballRadius > 150 && ballVelocityY > 0) {
                ball.setVelocityY(0);
                ball.setVelocityX(-3);
            } else if (ballCenterY - ballRadius >= 600 && ballVelocityY > 0) {
                ball.setVelocityY(0);
                ball.setVelocityX(3);
            }  else if (ballCenterX + ballRadius >= this.gameBoard.getWidth() - 60 && ballCenterY - ballRadius >= 600 && ballVelocityX > 0) {
                try {
                    baseHit (ball);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                enemyList.remove(ball);
				roundOverCheck(enemyList);
            }

            // Moves enemies
            ball.step();

        }
        ballSpawner++;
    }

	/**
	 * intitiates gridpane object
	 *
	 */
    private void tilePane() {
    	GridPane grid1 = createNewTilePane(5, 80, 1045, 280);
		//GridPane grid2 = createNewTilePane(117, 370, 1157, 570);

		this.grids.add(grid1);
		//this.grids.add(grid2);

		gameBoard.getChildren().add(grid1);
		//gameBoard.getChildren().add(grid2);
	}

	/**
	 * Fills each gridpane with smaller tiles
	 * sets features of smaller panes
	 * passes tile information to mouseclicked method
	 * @param left_x
	 * @param top_y
	 * @param right_x
	 * @param bot_y
	 * @return grid
	 */
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

	/**
	 * Creates new enemy
	 * Sets initial velocity and size
	 * @return ball
	 */
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

	/**
	 * Performs actions when main base is hit by an enemy
	 * Updates base health
	 * Removes ball from gameboard view
	 * @param ball
	 */
    private void baseHit(Ball ball) throws IOException {
        gameBoard.getChildren().remove(ball);
        base.damage();
        if (base.getHealth() <= 0) {
            System.out.println("Hello");
			gameBoard.getChildren().remove(base);
			windowHandler("homeScreen.fxml", "homeScreen");
		}
        this.baseHealthLabel.setText(String.format("Base Health: %d", base.getHealth()));
    }

	/**
	 * Checks to see if game is over
	 * Removes shots from view if over
	 * @param enemyList
	 */
	public void roundOverCheck(ArrayList<Ball> enemyList) {
        if (enemyList.size() < 1 && this.numEnemy >= this.enemyLimit) {
			gameBoard.getChildren().removeAll(shots);
			shots.clear();
			score += 100 * wave;
			this.money += 5 * wave;
            this.moneyLabel.setText(String.format("Money: %d", this.money));
			this.timer.cancel();
			this.peacePhase = true;
        }
    }

	/**
	 * Brings up menu options
	 * Gives user choice to restart game
	 * @param actionEvent
	 */
    public void onPauseButton(ActionEvent actionEvent) {
        if (this.paused == false && this.peacePhase == false) {
            this.timer.cancel();
            this.paused = true;
        } else if (this.paused == false && this.peacePhase == true) {

        } else {
            this.startTimer();
            this.paused = false;
        }
    }


    /** Facilitates buying towers feature
     * updates money
     * Lets user place tower
     */
    private boolean clicked = false;
    private int towerDirection = 1;
    public void onBuyTurretButton(ActionEvent actionEvent) {
        if (actionEvent.getSource().toString().contains("Tower1")) {
            towerDirection = 1;
        } else if (actionEvent.getSource().toString().contains("Tower2")){
            towerDirection = 2;
        } else if (actionEvent.getSource().toString().contains("Tower3")) {
            towerDirection = 3;
        } else if (actionEvent.getSource().toString().contains("Tower4")) {
            towerDirection = 4;
        }

    	Turret t = new Turret();
    	if (money >= t.getCost()) {
			clicked = !clicked;
		}
	}

	/**
	 * Places turret where user clicks mouse
	 * Sets turret size and image
	 * @param mouseEvent
	 * @param row
	 * @param col
	 */
	public void handle(MouseEvent mouseEvent, int row, int col) {
		if (clicked == true) {
			Turret turret = new Turret();
			turret.setDirection(towerDirection);
			this.money -= turret.getCost();
			this.moneyLabel.setText(String.format("Money: %d", this.money));

			turret.setPosition(row, col, grids.get(0).getLayoutY(), grids.get(0).getLayoutX(), TILE_SIZE + TILE_PADDING);

			turret.setFitWidth(TILE_SIZE);
			turret.setFitHeight(TILE_SIZE);

			Image img = new Image("file:res/Tower.png");
			turret.setImage(img);
			gameBoard.getChildren().add(turret);
			grids.get(0).add(turret, col, row);
			clicked = !clicked;
		}
	}

    /** Triggers waves of enemies
     * Determines number of enemies in each wave
     */
    public void onWaveButton(ActionEvent actionEvent) {
        if (this.paused == false && peacePhase == true) {
            this.wave++;
            this.numEnemy = 0;
            this.enemyLimit = 2 * this.wave + 1;
            this.waveLabel.setText(String.format("Wave: %d", this.wave));
            this.startTimer();
            peacePhase = false;
        }
	}

    /**
     * Launches new tower defense game
     * @param actionEvent
     * @throws IOException
     */
	public void onNewGame(ActionEvent actionEvent) throws IOException {
        String window = "sample.fxml";
        String id = "gameBoard";
        windowHandler(window, id);
    }

    /**
     * Closes current game and creates new game
     * @param text
     * @throws IOException
     */
	public void windowHandler(String text, String id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(text));
        Parent root = (Parent)loader.load();
        if (text.equals("sample.fxml")) {
            Controller controller = loader.getController();

        } else {
            HomeScreen homeScreen = loader.getController();
        }
        root.setId(id);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Tower Defense");

        Scene scene = new Scene(root, 1170, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();

        Stage stage = (Stage) gameBoard.getScene().getWindow();
        stage.close();

        closeWindow();
    }

    /**
     * Closes gameboard window
     */
    public void closeWindow() {
        Scene scene = gameBoard.getScene();
        Stage closing = new Stage();
        closing.setScene(scene);
        closing.hide();
    }
}
