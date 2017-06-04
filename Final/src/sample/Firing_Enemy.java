/**
 * Enemy.java
 * Chris Tordi & Martin Hoffman, 25 May 2017
 *
 * An enemy class taht shoots
 */

package sample;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Firing_Enemy implements Enemy_Interface {
	private int hp;
	private int rof;
	private int spd;

	// A list of valid targets (might need to be put in an abstract class?)
	private List<Object> targets = new ArrayList<>();

	public Firing_Enemy(int health, int fireRate, int speed) {
		this.hp = health;
		this.rof = fireRate;
		this.spd = speed;
	}

	// Finds all valid targets by checking the position of all potential targets and adding those
	// within range to the targets list (might need to be put in an abstract class?)
	abstract List<Tower> findTargets(List<Tower> allTargets);

	// Fires at the target
	abstract void fire(Object target);

	// Returns the position of the implementor
	abstract Point2D getPosition();
}
