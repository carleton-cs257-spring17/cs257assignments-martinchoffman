/**
 * Tower.java
 * Chris Tordi & Martin Hoffman, 28 May 2017
 *
 * A bullet class
 */

package sample;

import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class Shot extends Circle{
	private int dmg;
	private int direction;
	private Image img;

	private int velocityX;
	private int velocityY;


	public Shot(int damage, int direction, Image img) {
		this.dmg = damage;
		this.direction = direction ;
		this.img = img;
	}

	public void step() {
	    if (direction == 1) {
	        this.velocityX = 0;
	        this.velocityY = -10;
        } else if (direction == 2) {
            this.velocityX = 0;
            this.velocityY = 10;
        } else if (direction == 3) {
            this.velocityX = -10;
            this.velocityY = 0;
        } else if (direction == 4) {
            this.velocityX = 10;
            this.velocityY = 0;
        }
		this.setCenterX(this.getCenterX() + this.velocityX);
		this.setCenterY(this.getCenterY() + this.velocityY);
	}
}
