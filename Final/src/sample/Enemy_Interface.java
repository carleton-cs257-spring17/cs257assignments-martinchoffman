/**
 * Enemy_Interface.java
 * Chris Tordi & Martin Hoffman, 27 May 2017
 *
 * An enemy interface
 */

package sample;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public interface Enemy_Interface {
	// A queue of waypoints to help enemies move
	Deque<Waypoint> waypoints = new ArrayDeque<Waypoint>(10);

	// Retrieves the next waypoint along the path
	Waypoint getNextPoint();

	// Moves enemies toward the next waypoint
	void move(Waypoint waypoint);
}
