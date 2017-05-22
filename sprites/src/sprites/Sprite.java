/**
 * Sprite.java
 * Jeff Ondich, 10/29/14.
 *
 * The sprite superclass for the JavaFX sprites sample for CS257.
 */
package sprites;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Sprite extends Group {
    final private boolean SHOW_BORDER = true;
    private Rectangle border;

    // In addition to name and velocity, each Sprite also has a position
    // and size. Those attributes are a part of the Group superclass.
    private String name;
    private Point2D velocity;

    public Sprite() {
        if (SHOW_BORDER) {
            this.border = new Rectangle(0.0, 0.0, 0.0, 0.0);
            this.border.setFill(null);
            this.border.setStroke(Color.BLACK);
            this.getChildren().add(this.border);
        }
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String newName) {
        this.name = newName;
    }

    public final Point2D getPosition() {
        Point2D position = new Point2D(this.getLayoutX(), this.getLayoutY());
        return position;
    }

    public final void setPosition(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    public final Point2D getVelocity() {
        return this.velocity;
    }

    public final void setVelocity(double vx, double vy) {
        this.velocity = new Point2D(vx, vy);
    }

    public final Point2D getSize() {
        Bounds bounds = this.getLayoutBounds();
        Point2D size = new Point2D(bounds.getWidth(), bounds.getHeight());
        return size;
    }

    public void setSize(double width, double height) {
        if (SHOW_BORDER) {
            this.border.setWidth(width);
            this.border.setHeight(height);
        }
    }

    /**
     * Move the Sprite one step in the direction and magnitude
     * of its velocity.  Subclasses may override this method, which can
     * call super.step() and then perform any desired additional actions.
     */
    public void step() {
        Point2D position = this.getPosition();
        this.setPosition(position.getX() + this.velocity.getX(), position.getY() + this.velocity.getY());
    }

    /**
     * Play a sound suitable for this sprite.
     */
    abstract public void makeSound();
}
