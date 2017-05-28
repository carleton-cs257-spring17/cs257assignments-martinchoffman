/**
 * Targeting.java
 * Chris Tordi & Martin Hoffman, 27 May 2017
 *
 * An interface for targeting both enemies and towers
 */

package sample;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public interface Targeting {
	// A list of valid targets (might need to be put in an abstract class?)
	List<Object> targets = new ArrayList<>();

	// Finds all valid targets by checking the position of all potential targets and adding those
	// within range to the targets list (might need to be put in an abstract class?)
	List<Object> findTargets(List<Object> allTargets);

	// Returns the best target for the enemy
	Object getTarget(List<Object> targets);

	// Fires at the target
	void fire(Object target);

	// Returns the position of the implementor
	Point2D getPosition();
}
