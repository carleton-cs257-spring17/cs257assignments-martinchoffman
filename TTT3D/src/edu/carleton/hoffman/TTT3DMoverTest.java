package edu.carleton.hoffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;


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

        // board with one winning move for current player on one level
        // board with one winning move on different levels

        // empty board test.
        TTT3DMover mover = new TTT3DMover();
        TTT3DBoard empty = readBoardFromFile("test boards/empty.txt");
        List<TTT3DMove> winningMoves =  mover.winningMoves(empty);
        if (winningMoves.size() > 0) {
            System.out.println("empty test failed");
        }

        //tests 'X' win on one level
        TTT3DBoard xWinOneLevel = readBoardFromFile("test boards/xWinOneLevel");
        winningMoves =  mover.winningMoves(xWinOneLevel);
        if (winningMoves.size() == 0 || winningMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has %d winning moves.", winningMoves.size());
        } else {
            TTT3DMove winningMove = winningMoves.get(0);
            if (winningMove.level != 0 && winningMove.row != 0 && winningMove.column != 3 && winningMove.player.compareTo('X') ) {
                System.out.println("xWinOneLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinOneLevel passed!");
            }

        }




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

        return board;
    }
}