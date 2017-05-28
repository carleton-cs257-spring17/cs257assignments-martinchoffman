/**
 * Tower.java
 * Chris Tordi & Martin Hoffman, 25 May 2017
 *
 * A tower class
 */

package sample;

public abstract class Tower implements Tower_Interface, Targeting {
	private int hp;
	private int rof;

    public Tower(int health, int fireRate) {
    	this.hp = health;
    	this.rof = fireRate;
    }
}
