/**
 * Enemy_Interface.java
 * Chris Tordi & Martin Hoffman, 27 May 2017
 *
 * An enemy interface
 */

package sample;

import java.util.ArrayDeque;
import java.util.Deque;

public interface Enemy_Interface {
	// A queue of waypoints to help enemies move (might need to be in teh abstract class?)
	Deque<Waypoint> waypoints = new ArrayDeque<Waypoint>(10);

	// Retrieves the next waypoint along the path
	Waypoint getNextPoint();

	// Moves enemies toward the next waypoint
	void move(Waypoint waypoint);

	// Returns the health of the enemy
	int getHealth();

	// Returns the position of the enemy as a Point2D
	void getEnemyPosition();

	// Moves the enemy
	void step();
}
