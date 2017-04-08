package edu.carleton.hoffman;

import java.util.Arrays;

/**
 * TTT3DBoard represents a simple 4x4x4 3D tic-tac-toe game. Each instance
 * stores includes the contents of each of the 64 squares, plus an indicator
 * of whose turn it is ('X' or 'O').
 * <p>
 * In the 3D tic-tac-toe context, a
 * <p>
 * To keep things simple, we use the uppercase letters X and O rather than
 * something more complicated (e.g. an enumerated type) to represent players
 * and their moves. Empty squares are represented using a hyphen.
 * <p>
 * FOR DISCUSSION:
 * (1) How do you feel about my use of "this" to refer to instance variables?<br>
 * (2) Does it make sense to define BOARD_SIZE and EMPTY_SQUARE instead of
 * just using 4 and '-' throughout the code? If so, then why not also define
 * constants for 'X' and 'O'?<br>
 * (3) What's missing?
 *        Initialization and/or setter for whoseTurn.
 *        Has somebody won yet?
 *        What should makeMove do if the move is illegal?<br>
 * (4) What do you think about my use of the "ternary operator" (?:) in
 * the second constructor and at the bottom of makeMove?
 *
 * @author Jeff Ondich
 * @version 30 March 2017
 */
public class TTT3DBoard {
    public final static Character EMPTY_SQUARE = '-';
    public final static int BOARD_SIZE = 4;

    private Character squareValues[];
    private Character whoseTurn;

    /**
     * Default constructor. Initialize an empty game board, and set
     * 'X' to be the player moving first.
     */
    public TTT3DBoard() {
        this('X');
    }

    /**
     * Initialize an empty game board. If startingPlayer is 'X', then 'X' will
     * have the first turn. Otherwise, 'O' will move first.
     */
    public TTT3DBoard(Character startingPlayer) {
        int squareArrayLength = BOARD_SIZE * BOARD_SIZE * BOARD_SIZE;
        this.squareValues = new Character[squareArrayLength];
        for (int k = 0; k < squareArrayLength; k++) {
            this.squareValues[k] = EMPTY_SQUARE;
        }
        this.whoseTurn = (startingPlayer == 'X' ? 'X' : 'O');
    }

    /**
     * Copy constructor.
     * @param otherBoard the board to be copied
     */
    public TTT3DBoard(TTT3DBoard otherBoard) {
        int squareArrayLength = BOARD_SIZE * BOARD_SIZE * BOARD_SIZE;
        this.squareValues = new Character[squareArrayLength];
        System.arraycopy(otherBoard.squareValues, 0, this.squareValues, 0, squareArrayLength);
    }

    /**
     * Initializes this board from the contents of the specified file.
     *
     * The file format for a board consists of five lines of text. The first four lines
     * represent the four rows of the 3D tic-tac-toe board, with X's, O's, hyphens (to
     * represent empty squares), and space characters as needed for human readability.
     * The fifth line should contain only an X or an O, to indicate whose turn it is.
     * For example:<br><br>
     *     <pre>
     *     XO-- X--- X--- ----
     *     X--- ---- ---- ----
     *     ---- -O-- ---- -O--
     *     XO-- ---- ---- ----
     *     O
     *     </pre>
     *
     *     represents a board where X has taken 4 turns, O has taken three turns, and
     *     it's O's turn now. You may use space characters in any way you wish. Any
     *     characters other than X, O, -, space, or newline should cause loadFromFile
     *     to throw an exception.
     *     @param fileName the file containing the board to be loaded.
     */
    public void loadFromFile(String fileName) {
    }

    /**
     * @return 'X' or 'O', depending on whose turn it is
     */
    public Character getWhoseTurn() {
        return this.whoseTurn;
    }

    /**
     * @param level the level of the board position
     * @param row the row of the board position
     * @param column the column of the board position
     * @return the value ('X', 'O', or EMPTY_SQUARE) located at the specified
     * position on the game board
     */
    public Character valueInSquare(int level, int row, int column) {
        if (level < 0 || level >= BOARD_SIZE) {
            throw new IllegalArgumentException("Illegal level number " + level);
        }
        if (row < 0 || row >= BOARD_SIZE) {
            throw new IllegalArgumentException("Illegal row number " + row);
        }
        if (column < 0 || column >= BOARD_SIZE) {
            throw new IllegalArgumentException("Illegal column number " + column);
        }
        return this.squareValues[indexForPosition(level, row, column)];
    }

    /**
     * Apply the specified move to this game board. If the move is legal,
     * this game board reflects the change and the whoseTurn changes to the
     * other player.
     * @param move the move to be made
     * @throws IndexOutOfBoundsException if the move position is out of bounds, in
     * which case this game board is not changed
     * @throws IllegalArgumentException if it's not currently the move's player's
     * turn, in which case this game board is not changed
     */
    public void makeMove(TTT3DMove move) {
        if (move.row < 0 || move.row >= BOARD_SIZE) {
            throw new IndexOutOfBoundsException("Illegal row number " + move.row);
        }
        if (move.column < 0 || move.column >= BOARD_SIZE) {
            throw new IndexOutOfBoundsException("Illegal column number " + move.column);
        }
        if (move.level < 0 || move.level >= BOARD_SIZE) {
            throw new IndexOutOfBoundsException("Illegal level number " + move.level);
        }
        if (move.player != this.whoseTurn) {
            throw new IllegalArgumentException("It's not " + move.player + "'s turn");
        }

        this.squareValues[indexForPosition(move.level, move.row, move.column)] = this.whoseTurn;
        this.whoseTurn = (this.whoseTurn == 'X' ? 'O' : 'X');
    }

    /**
     * @param level the level of the board position
     * @param row the row of the board position
     * @param column the column of the board position
     * @return the index to be used in the linear array squareValues to represent the
     * 3D position (level, row, column). We're storing the squares in level-major and
     * then row-major order.
     */
    private int indexForPosition(int level, int row, int column) {
        return BOARD_SIZE * BOARD_SIZE * level + BOARD_SIZE * row + column;
    }
}