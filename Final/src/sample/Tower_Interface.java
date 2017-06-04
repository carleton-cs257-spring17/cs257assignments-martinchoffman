/**
 * Tower_Interface.java
 * Chris Tordi & Martin Hoffman, 27 May 2017
 *
 * A tower interface
 */

package sample;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public interface Tower_Interface {
	// Returns the current health of the tower
	int getHealth();

	// Returns the rate of fire of teh tower
	double getROF();

	// Finds all valid targets by checking the position of all potential targets and adding those
	// within range to the targets list (might need to be put in an abstract class?)
	List<Enemy> findTargets(List<Enemy> allTargets);

	// Returns the best target for the enemy
	Enemy getTarget(List<Enemy> validTargets);

	// Fires at the target
	Shot fire(Enemy target);

	// Returns the position of the implementor
	Point2D getPosition();
}
