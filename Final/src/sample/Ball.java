/**
 * Ball.java
 *  Most of the class was created by Jeff Ondich, 10/29/14.
 *  Updated by Chris Tordi and Martin Hoffman
 *
 * A sample subclass of Sprite for CS257.
 */

package sample;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    @FXML private double velocityX;
    @FXML private double velocityY;
    private int MAX_HEALTH = 100;
    private int current_Health = MAX_HEALTH;

    public Ball() {
    }

    public void step() {
        this.setCenterX(this.getCenterX() + this.velocityX);
        this.setCenterY(this.getCenterY() + this.velocityY);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public int getHealth() {
        return current_Health;
    }


    public void damage() {
        current_Health = current_Health - 10;
    }
}
