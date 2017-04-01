package edu.carleton.hoffman;

import java.util.ArrayList;
import java.util.List;

/**
 * TTT3DMover's job is to analyze a TTT3DBoard and make choices about what move
 * a player should make next. A TT3DMover object could be used as a key component
 * of the "AI" player in a full-blown 3D tic-tac-toe application.
 *
 * In phase 3 of this assignment, you'll implement the methods stubbed and
 * documented below. In phase 2 (which comes before phase 3, as you might guess),
 * you'll create a JUnit TTT3DMoverTest class with a suitably rich collection of
 * unit tests for the public methods below.
 *
 * @author Jeff Ondich
 * @version 30 March 2017
 */
public class TTT3DMover {
    /**
     * Because we currently have no implementation of TTT3DMover, a default
     * constructor should suffice.
     */
    public TTT3DMover() {
    }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return a (possibly empty) list of moves that the current player can take
     * to win the game in a single turn.
     */
    public List<TTT3DMove> winningMoves(TTT3DBoard board) {

        return new ArrayList<TTT3DMove>();
    }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return a (possibly empty) list of moves that the non-current player could take
     * to win the game in a single turn. That is, these are positions where the current
     * player should play to avoid losing on the opponent's next turn.
     */
    public List<TTT3DMove> blockingMoves(TTT3DBoard board) {

        return new ArrayList<TTT3DMove>();
    }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return a (possibly empty) list of moves that the current player could take
     * to force a win. A move is "forcing" if it results in at least two different
     * ways for the current player to win on the next move. In other words, after a
     * forcing move, the opponent will be forced to make two different blocking moves
     * in a single turn to avoid losing.
     */
    public List<TTT3DMove> forcingMoves(TTT3DBoard board) {
        return new ArrayList<TTT3DMove>();
    }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return the move that this object determines would be the best choice for the
     * board's current player.
     */
    public TTT3DMove bestMove(TTT3DBoard board) {
        return new TTT3DMove(0, 0, 0, board.getWhoseTurn());
    }
}
