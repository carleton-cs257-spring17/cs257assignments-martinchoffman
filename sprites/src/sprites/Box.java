/**
 * Box.java
 * Jeff Ondich, 16 May 2017
 *
 * A sample subclass of Sprite for CS257.
 */
package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Box extends Sprite {
    private Rectangle box;

    public Box() {
        this.box = new Rectangle(0.0, 0.0, 0.0, 0.0);
        this.box.setFill(null);
        this.box.setStroke(Color.BLACK);
        this.getChildren().add(this.box);
    }

    @Override
    public void setSize(double width, double height) {
        super.setSize(width, height);
        this.box.setWidth(width);
        this.box.setHeight(height);
    }

    @Override
    public void makeSound() {
    }
}
