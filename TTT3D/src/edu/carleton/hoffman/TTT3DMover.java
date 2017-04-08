package edu.carleton.hoffman;

import sun.applet.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public List<TTT3DMove> forcingMoves(TTT3DBoard board) { return new ArrayList<TTT3DMove>(); }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return the move that this object determines would be the best choice for the
     * board's current player.
     */
    public TTT3DMove bestMove(TTT3DBoard board) { return new TTT3DMove(0, 0, 0, board.getWhoseTurn()); }

    TTT3DBoard readBoardFromFile(String filePath) {
        // Attempt to open file located at filePath
        File inputFile = new File(filePath);
        Scanner scanner = null;

        try {
            scanner = new Scanner(inputFile);
        } catch(FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }

        // Create string of X's O's and _'s
        String chrs = "";
        Character whoseTurn = new Character(' ');
        int count64 = 0;
        while(scanner.hasNext()) {
            String chr = scanner.next();
            if (count64 == 63) {
                whoseTurn = chr.charAt(0);
            } else if (chr.matches("[XO-]")) {
                chrs += chr;
                count64++;
            }
        }

        // Convert chrs to move objects
        List<TTT3DMove> moves = new ArrayList<TTT3DMove>();
        for (int i = 0; i < chrs.length(); i++) {
            char chr = chrs.charAt(i);
            if (chr != '-') {
                moves.add(new TTT3DMove(0+i/16, (i%16)/4, i%4, chr));
            }
        }

        // Separate moves for player 'X'
        List<TTT3DMove> xMoves = new ArrayList<TTT3DMove>();
        for (TTT3DMove move : moves) {
            if (move != null && move.player == 'X') {
                xMoves.add(move);
            }
        }

        // Separate moves for player 'O'
        List<TTT3DMove> oMoves = new ArrayList<TTT3DMove>();
        for (TTT3DMove move : moves) {
            if (move != null && move.player == 'O') {
                oMoves.add(move);
            }
        }

        TTT3DBoard board = new TTT3DBoard();
        if ((whoseTurn == 'X' && xMoves.size() == oMoves.size()) || oMoves.size() > xMoves.size()) {
            board = new TTT3DBoard();
            for (int i = 0; i < xMoves.size(); i++) {
                board.makeMove(xMoves.get(i));
                if (i < oMoves.size()) {
                    board.makeMove(oMoves.get(i));
                }
            }
        } else if ((whoseTurn == 'O' && xMoves.size() == oMoves.size()) || xMoves.size() > oMoves.size()) {
            board = new TTT3DBoard('O');
            for (int i = 0; i < oMoves.size(); i++) {
                board.makeMove(oMoves.get(i));
                if (i < xMoves.size()) {
                    board.makeMove(xMoves.get(i));
                }
            }
        } else {
            System.err.println("Illegal board!");
        }

        return board;
    }

    public static void main(String[] args) {
        String function = args[0];
        String filePath = args[1];

        TTT3DMover mover = new TTT3DMover();
        TTT3DBoard board = mover.readBoardFromFile(filePath);
    }
}

/*      if (xMoves.size() == oMoves.size()) {
            if (whoseTurn == 'X') {
                TTT3DBoard board = new TTT3DBoard();
                for (int i = 0; i < xMoves.size(); i++) {
                    board.makeMove(xMoves.get(i));
                    if (i < oMoves.size()) {
                        board.makeMove(oMoves.get(i));
                    }
                }
            } else if (whoseTurn == 'O') {
                TTT3DBoard board = new TTT3DBoard('O');
                for (int i = 0; i < oMoves.size(); i++) {
                    board.makeMove(oMoves.get(i));
                    if (i < xMoves.size()) {
                        board.makeMove(xMoves.get(i));
                    }
                }
            }
        } else if (xMoves.size() > oMoves.size()) {
            TTT3DBoard board = new TTT3DBoard();
            for (int i = 0; i < xMoves.size(); i++) {
                board.makeMove(xMoves.get(i));
                if (i < oMoves.size()) {
                    board.makeMove(oMoves.get(i));
                }
            }
        } else {
            TTT3DBoard board = new TTT3DBoard('O');
            for (int i = 0; i < oMoves.size(); i++) {
                board.makeMove(oMoves.get(i));
                if (i < xMoves.size()) {
                    board.makeMove(xMoves.get(i));
                }
            }
        }*/