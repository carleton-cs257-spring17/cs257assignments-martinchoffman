package edu.carleton.hoffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Martin on 3/30/2017.
 */
class TTT3DMoverTest {
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.out.println("Setting up...");
        TTT3DBoard testBoard = new TTT3DBoard();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.out.println("Tearing down...");
    }

    @org.junit.jupiter.api.Test
    void winningMoves() {
        // empty board, board with winning moves for other player,
        // board with one winning move for current player on one level
        // board with one winning move
        readBoardFromFile("test boards/test.txt");
    }

    @org.junit.jupiter.api.Test
    void blockingMoves() {
    }

    @org.junit.jupiter.api.Test
    void forcingMoves() {
    }

    @org.junit.jupiter.api.Test
    void bestMove() {
    }

    TTT3DBoard readBoardFromFile(String filePath) {
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
        while(scanner.hasNext()) {
            String chr = scanner.next();
            if (chr.matches("[XO-]")) {
                chrs += chr;
            }
        }

        TTT3DMove[] moves = new TTT3DMove[64];
        for (int i = 0; i < chrs.length(); i++) {
            char chr = chrs.charAt(i);
            // Some indices in chrs will be null!
            if (chr != '-') {
                moves[i] = new TTT3DMove(0+i/16, (i%16)/4, i%4, chr);
            }
        }

        TTT3DMove[] oMoves = new TTT3DMove[64];
        int countOs = 0;
        for (TTT3DMove move : moves) {
            if (move != null && move.player == 'O') {
                oMoves[countOs] = move;
                countOs++;
            }
        }

        TTT3DMove[] xMoves = new TTT3DMove[64];
        int countXs = 0;
        for (TTT3DMove move : moves) {
            if (move != null && move.player == 'X') {
                xMoves[countXs] = move;
                countXs++;
            }
        }

        TTT3DBoard board = new TTT3DBoard();
        int countMoves = 0;
        while (xMoves[countMoves] != null) {
            System.out.println(xMoves[countMoves].level);
            System.out.println(xMoves[countMoves].row);
            System.out.println(xMoves[countMoves].column);
            System.out.println(xMoves[countMoves].player);
            board.makeMove(xMoves[countMoves]);
            System.out.println(board.valueInSquare(xMoves[countMoves].level, xMoves[countMoves].row, xMoves[countMoves].column));
            if (oMoves[countMoves] != null) {
                System.out.println(oMoves[countMoves].level);
                System.out.println(oMoves[countMoves].row);
                System.out.println(oMoves[countMoves].column);
                System.out.println(oMoves[countMoves].player);
                board.makeMove(oMoves[countMoves]);
                System.out.println(board.valueInSquare(oMoves[countMoves].level, oMoves[countMoves].row, oMoves[countMoves].column));
            }
            countMoves++;
        }

        /*TTT3DBoard board = new TTT3DBoard();
        for (TTT3DMove move : moves) {
            if (move != null) {
                System.out.println(move.level);
                System.out.println(move.row);
                System.out.println(move.column);
                System.out.println(move.player);
                board.makeMove(move);
                System.out.println(board.valueInSquare(move.level, move.row, move.column));
                System.out.println();
            }
        }*/

        return board;
    }
}