/**
 * Tower.java
 * Chris Tordi & Martin Hoffman, 28 May 2017
 *
 * A bullet class
 */

package sample;

import javafx.scene.image.Image;

public class Shot {
	private int dmg;
	private int spd;
	private Image img;

	public Shot(int damage, int speed, Image img) {
		this.dmg = damage;
		this.spd = speed;
		this.img = img;
	}
}
