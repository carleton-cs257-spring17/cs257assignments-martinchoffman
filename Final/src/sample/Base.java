/**
 * Base.java
 * Chris Tordi & Martin Hoffman, 25 May 2017
 *
 * A base class
 */

package sample;

import javafx.scene.shape.*;



public class Base extends Rectangle {
    private final int MAX_HEALTH = 100;
    private int RATE_OF_FIRE;
    private int current_Health = MAX_HEALTH;

    public Base() {
    }

    // Returns the current health of the tower
    public int getHealth() {
        return current_Health;
    }

    // Returns the rate of fire of the tower
    public int getROF() {
        return 0;
    }

    public void damage() {
        current_Health = current_Health - 10;
    }

}
