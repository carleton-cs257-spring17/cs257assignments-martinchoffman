/**
 * Enemy.java
 * Chris Tordi & Martin Hoffman, 25 May 2017
 *
 * An enemy class taht shoots
 */

package sample;

public abstract class Firing_Enemy implements Enemy_Interface, Targeting {
	private int hp;
	private int rof;
	private int spd;

	public Firing_Enemy(int health, int fireRate, int speed) {
		this.hp = health;
		this.rof = fireRate;
		this.spd = speed;
	}
}
