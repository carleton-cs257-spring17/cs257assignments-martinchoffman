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
	private int spd;
	private Image img;

	private final double velocityX = 0;
	private final double velocityY = -10;

	public Shot(int damage, int speed, Image img) {
		this.dmg = damage;
		this.spd = speed;
		this.img = img;
	}

	public void step() {
		this.setCenterX(this.getCenterX() + this.velocityX);
		this.setCenterY(this.getCenterY() + this.velocityY);
	}
}
