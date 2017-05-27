/**
 * Tower_Interface.java
 * Chris Tordi & Martin Hoffman, 27 May 2017
 *
 * A tower interface
 */

package sample;

import java.util.List;

public interface Tower_Interface {
	/*
	private final int MAX_HEALTH
	private final int RATE_OF_FIRE
	 */

	// Returns the current health of the tower
	int getHealth();

	// Returns the rate of fire of teh tower
	int getROF();
}
