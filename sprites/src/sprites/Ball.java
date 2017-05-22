/**
 * Ball.java
 * Jeff Ondich, 10/29/14.
 *
 * A sample subclass of Sprite for CS257.
 */
package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Sprite {
    private Circle circle;

    public Ball() {
        this.circle = new Circle(0.0, 0.0, 0.0);
        this.circle.setFill(Color.GREEN);
        this.getChildren().add(this.circle);
    }

    /* This illustrates an override of the setSize method. Note that it
     * defers the resizing of the sprite itself to super.setSize, but that
     * this Ball.setSize takes responsibility for replacing the old Circle
     * with a new Circle sized appropriately for the new size.
     */
    @Override
    public void setSize(double width, double height) {
        super.setSize(width, height);
        this.circle.setCenterX(width / 2.0);
        this.circle.setCenterY(height / 2.0);
        this.circle.setRadius(width / 2.0);
    }

    @Override
    public void makeSound() {
    }
}
