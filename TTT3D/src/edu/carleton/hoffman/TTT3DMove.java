package edu.carleton.hoffman;

/**
 * This class represents a single move on a 4x4x4 3D tic-tac-toe board.
 *
 * FOR DISCUSSION: What do you think about using public instance variables
 * here instead of getters like "public int getRow()"?
 *
 * @author Jeff Ondich
 * @version 30 March 2017
 */
public class TTT3DMove {
    public int level;
    public int row;
    public int column;
    public Character player;

    TTT3DMove(int level, int row, int column, Character player) {
        this.level = level;
        this.row = row;
        this.column = column;
        this.player = player;
    }
}