/**
 * Map.java
 * Chris Tordi & Martin Hoffman, 25 May 2017
 *
 * A map class
 */

package sample;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Integer> buildSquares = new ArrayList<>();		// Stores valid build squares
    private List<? extends Enemy> enemies = new ArrayList<>();	// Stores refs to enemies
    private List<? extends Tower> towers = new ArrayList<>();	// Stores refs to towers

    private Base base;	// Stores ref to base

    private int cash;
    private int level;
    private int wave;

    public Map(int cash, int level, int wave) {
    	this.cash = cash;
    	this.level = level;
    	this.wave = wave;
    }

	// Sets the base reference
    public void setBase(Base base) {
    	this.base = base;
	}

    public void addEnemy() { }

    public void addTower() { }

	public List<? extends Enemy> getEnemies() {
    	return enemies;
	}

	public List<? extends Tower> getTowers() {
    	return towers;
	}

	// Checks to see if a square can be built on
	public boolean checkBuild() { return true; }
}
