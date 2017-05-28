/**
 * Enemy.java
 * Chris Tordi & Martin Hoffman, 25 May 2017
 *
 * An enemy class
 */

package sample;

public abstract class Enemy implements Enemy_Interface{
    private int hp;
    private int rof;
    private int spd;

    public Enemy(int health, int fireRate, int speed) {
        this.hp = health;
        this.rof = fireRate;
        this.spd = speed;
    }
}
