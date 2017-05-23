/**
 * SpriteWorld.java
 * Jeff Ondich, 10/29/14.
 *
 * The Application subclass for the Sprite sample for CS257.
 */
package sprites;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SpriteWorld extends Application {
    private ArrayList<Sprite> spriteList;

    final private double SCENE_WIDTH = 500;
    final private double SCENE_HEIGHT = 400;
    final private double FRAMES_PER_SECOND = 20.0;

    @Override
    public void start(Stage primaryStage) {
        // Since this application will be multi-threaded, we want to make
        // sure to terminate all the threads when the user closes the single window.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        // Set up the Stage.
        Group root = new Group();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        this.spriteList = new ArrayList<Sprite>();

        Sprite sprite = new hoffmanm2();
        sprite.setName("Geralt");
        sprite.setSize(76, 77);
        sprite.setPosition(74, 51);
        sprite.setVelocity(10, 7);
        root.getChildren().add(sprite);
        this.spriteList.add(sprite);

        // Show the UI.
        primaryStage.setScene(scene);
        primaryStage.show();

        // Set up the timer to run the animation. Note that there are
        // more standard ways to run an animation with JavaFX, but this one
        // is extremely explicit, and easiest to understand and control at first.
        this.setUpAnimationTimer();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void setUpAnimationTimer() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateAnimation();
                    }
                });
            }
        };

        final long startTimeInMilliseconds = 0;
        final long repetitionPeriodInMilliseconds = 100;
        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        Timer timer = new java.util.Timer();
        timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    private void updateAnimation() {
        for (Sprite sprite : this.spriteList) {
            // Change sprite's velocity to create a bounce if it has hit a wall.
            Point2D position = sprite.getPosition();
            Point2D size = sprite.getSize();
            Point2D velocity = sprite.getVelocity();
            if (position.getX() + size.getX() >= SCENE_WIDTH && velocity.getX() > 0) {
                sprite.setVelocity(-velocity.getX(), velocity.getY());
            } else if (position.getX() < 0  && velocity.getX() < 0) {
                sprite.setVelocity(-velocity.getX(), velocity.getY());
            } else if (position.getY() + size.getY() >= SCENE_HEIGHT && velocity.getY() > 0) {
                sprite.setVelocity(velocity.getX(), -velocity.getY());
            } else if (position.getY() < 0 && velocity.getY() < 0) {
                sprite.setVelocity(velocity.getX(), -velocity.getY());
                sprite.makeSound();
            }

            // Move the sprite.
            sprite.step();
        }
    }
}
