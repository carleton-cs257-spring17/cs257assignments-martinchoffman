/**
 * Tower.java
 * Chris Tordi & Martin Hoffman, 28 May 2017
 *
 * A bullet class
 */

package sample;

public class Shot {
	private int dmg;
	private int spd;
	private String imgUrl;

	public Shot(int damage, int speed, String imgUrl) {
		this.dmg = damage;
		this.spd = speed;
		this.imgUrl = imgUrl;
	}
}
