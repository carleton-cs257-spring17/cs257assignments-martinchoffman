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
            EXIT_WITH_ERR();
        }

        //tests 'X' wins on one level
        TTT3DBoard xWinOneLevel = readBoardFromFile("test boards/xWinOneLevel.txt");
        winningMoves =  mover.winningMoves(xWinOneLevel);
        if (winningMoves.size() == 0 || winningMoves.size() > 1) {
            System.out.printf("xWinOneLevel failed. List has %d winning moves.%n", winningMoves.size());
        } else {
            TTT3DMove winningMove = winningMoves.get(0);
            if (winningMove.level != 0 || winningMove.row != 0 || winningMove.column != 3 || winningMove.player != 'X') {
                System.out.println("xWinOneLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinOneLevel passed!");
            }
        }

        //tests 'X' win on one level vertical
        TTT3DBoard xWinVerticalOneLevel = readBoardFromFile("test boards/xWinVerticalOneLevel.txt");
        winningMoves =  mover.winningMoves(xWinVerticalOneLevel);
        if (winningMoves.size() == 0 || winningMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has" +winningMoves.size()+ "winning moves.");
        } else {
            TTT3DMove winningMove = winningMoves.get(0);
            if (winningMove.level != 0 || winningMove.row != 3 || winningMove.column != 0 || winningMove.player != 'X') {
                System.out.println("xWinVerticalOneLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinVerticalOneLevel passed!");
            }
        }

        //tests 'X' win on one level diagonal
        TTT3DBoard xWinDiagonalOneLevel = readBoardFromFile("test boards/xWinDiagonalOneLevel.txt");
        winningMoves =  mover.winningMoves(xWinDiagonalOneLevel);
        if (winningMoves.size() == 0 || winningMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has" +winningMoves.size()+ "winning moves.");
        } else {
            TTT3DMove winningMove = winningMoves.get(0);
            if (winningMove.level != 0 || winningMove.row != 3 || winningMove.column != 3 || winningMove.player != 'X') {
                System.out.println("xWinDiagonalOneLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinDiagonalOneLevel passed!");
            }
        }


        // tests 'X' win on multiple levels horizontal
        TTT3DBoard xWinHorizontalMultLevel = readBoardFromFile("test boards/xWinHorizontalMultLevel.txt");
        winningMoves =  mover.winningMoves(xWinHorizontalMultLevel);
        if (winningMoves.size() == 0 || winningMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has" +winningMoves.size()+ "winning moves.");
        } else {
            TTT3DMove winningMove = winningMoves.get(0);
            if (winningMove.level != 3 || winningMove.row != 0 || winningMove.column != 3 || winningMove.player != 'X') {
                System.out.println("xWinHorizontalMult failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinHorizontalMult passed!");
            }
        }

        //tests 'X' win on multiple levels vertical
        TTT3DBoard xWinVerticalMultLevel = readBoardFromFile("test boards/xWinVerticalMultLevel.txt");
        winningMoves =  mover.winningMoves(xWinVerticalMultLevel);
        if (winningMoves.size() == 0 || winningMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has" +winningMoves.size()+ "winning moves.");
        } else {
            TTT3DMove winningMove = winningMoves.get(0);
            if (winningMove.level != 0 || winningMove.row != 3 || winningMove.column != 0 || winningMove.player != 'X') {
                System.out.println("xWinVerticalMultLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinVerticalMultLevel passed!");
            }
        }

        //tests 'X' win on multiple levels diagonal
        TTT3DBoard xWinMultLevel = readBoardFromFile("test boards/xWinMultLevel.txt");
        winningMoves =  mover.winningMoves(xWinMultLevel);
        if (winningMoves.size() == 0 || winningMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has" +winningMoves.size()+ "winning moves.");
        } else {
            TTT3DMove winningMove = winningMoves.get(0);
            if (winningMove.level != 0 || winningMove.row != 3 || winningMove.column != 0 || winningMove.player != 'X') {
                System.out.println("xWinMultLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinMultLevel passed!");
            }
        }

        //tests multiple 'X' wins on multiple and single levels
        TTT3DBoard x3WinningMoves = readBoardFromFile("test boards/x3WinningMoves.txt");
        winningMoves =  mover.winningMoves(x3WinningMoves);
        if (winningMoves.size() == 0 || winningMoves.size() >3) {
            System.out.printf("xWinMultLevel failed. List has %d winning moves.", winningMoves.size());
        } else {
            for (TTT3DMove move : winningMoves) {
                if (move.level == 2) {
                    if (move.row != 2 || move.column != 2 || move.player != 'X') {
                        System.out.println("x3WinningMoves failed. Winning move is incorrect");
                    } else {
                        System.out.println("x3 Test 1/3 passed");
                    }

            }
                else if (move.column == 3){
                    if (move.row != 0 || move.level != 3 || move.player != 'X') {
                        System.out.println("x3WinningMoves failed. Winning move is incorrect");
                    } else {
                        System.out.println("x3 Test  part 2/3 passed");
                    }

                }
                else {
                    if (move.level != 3 || move.row != 3 || move.column != 0 || move.player != 'X') {
                        System.out.println("x3WinningMoves failed. Winning move is incorrect");
                    } else {
                        System.out.println("x3 Test 3/3 passed");
                    }
                }
            }
        }
    }

    @org.junit.jupiter.api.Test
    void blockingMoves() {
        // empty board test.
        TTT3DMover mover = new TTT3DMover();
        TTT3DBoard empty = readBoardFromFile("test boards/empty.txt");
        List<TTT3DMove> blockingMoves =  mover.blockingMoves(empty);
        if (blockingMoves.size() > 0) {
            System.out.println("empty test failed");
        } else {
            System.out.println("empty test passed");
        }

        //tests 'X' with no wins
        TTT3DBoard xNoWin = readBoardFromFile("test boards/xNoWin.txt");
        blockingMoves =  mover.blockingMoves(xNoWin);
        if (blockingMoves.size() == 0) {
            System.out.printf("xNoWin passed. List has %d winning moves.%n", blockingMoves.size());
        } else {
            System.out.println("xNoWin passed!");
        }

        //tests 'X' wins on one level
        TTT3DBoard xWinOneLevel = readBoardFromFile("test boards/xWinOneLevel.txt");
        blockingMoves =  mover.blockingMoves(xWinOneLevel);
        if (blockingMoves.size() == 0 || blockingMoves.size() > 1) {
            System.out.printf("xWinOneLevel failed. List has %d winning moves.%n", blockingMoves.size());
        } else {
            TTT3DMove blockingMove = blockingMoves.get(0);
            if (blockingMove.level != 0 || blockingMove.row != 0 || blockingMove.column != 3 || blockingMove.player != 'O') {
                System.out.println("xWinOneLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinOneLevel passed!");
            }
        }

        //tests 'O' win on one level vertical
        TTT3DBoard xWinVerticalOneLevel = readBoardFromFile("test boards/xWinVerticalOneLevel.txt");
        blockingMoves =  mover.blockingMoves(xWinVerticalOneLevel);
        if (blockingMoves.size() == 0 || blockingMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has" +blockingMoves.size()+ "winning moves.");
        } else {
            TTT3DMove blockingMove = blockingMoves.get(0);
            if (blockingMove.level != 0 || blockingMove.row != 3 || blockingMove.column != 0 || blockingMove.player != 'O') {
                System.out.println("xWinVerticalOneLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinVerticalOneLevel passed!");
            }
        }

        //tests 'O' win on one level diagonal
        TTT3DBoard xWinDiagonalOneLevel = readBoardFromFile("test boards/xWinDiagonalOneLevel.txt");
        blockingMoves =  mover.blockingMoves(xWinDiagonalOneLevel);
        if (blockingMoves.size() == 0 || blockingMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has" +blockingMoves.size()+ "winning moves.");
        } else {
            TTT3DMove blockingMove = blockingMoves.get(0);
            if (blockingMove.level != 0 || blockingMove.row != 3 || blockingMove.column != 3 || blockingMove.player != 'O') {
                System.out.println("xWinDiagonalOneLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinDiagonalOneLevel passed!");
            }
        }


        // tests 'O' win on multiple levels horizontal
        TTT3DBoard xWinHorizontalMultLevel = readBoardFromFile("test boards/xWinHorizontalMultLevel.txt");
        blockingMoves =  mover.blockingMoves(xWinHorizontalMultLevel);
        if (blockingMoves.size() == 0 || blockingMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has" +blockingMoves.size()+ "winning moves.");
        } else {
            TTT3DMove blockingMove = blockingMoves.get(0);
            if (blockingMove.level != 3 || blockingMove.row != 0 || blockingMove.column != 3 || blockingMove.player != 'O') {
                System.out.println("xWinHorizontalMult failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinHorizontalMult passed!");
            }
        }

        //tests 'O' win on multiple levels vertical
        TTT3DBoard xWinVerticalMultLevel = readBoardFromFile("test boards/xWinVerticalMultLevel.txt");
        blockingMoves =  mover.blockingMoves(xWinVerticalMultLevel);
        if (blockingMoves.size() == 0 || blockingMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has" +blockingMoves.size()+ "winning moves.");
        } else {
            TTT3DMove blockingMove = blockingMoves.get(0);
            if (blockingMove.level != 0 || blockingMove.row != 3 || blockingMove.column != 0 || blockingMove.player != 'O') {
                System.out.println("xWinVerticalMultLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinVerticalMultLevel passed!");
            }
        }

        //tests 'O' win on multiple levels diagonal
        TTT3DBoard xWinMultLevel = readBoardFromFile("test boards/xWinMultLevel.txt");
        blockingMoves =  mover.blockingMoves(xWinMultLevel);
        if (blockingMoves.size() == 0 || blockingMoves.size() > 1) {
            System.out.println("xWinOneLevel failed. List has" +blockingMoves.size()+ "winning moves.");
        } else {
            TTT3DMove blockingMove = blockingMoves.get(0);
            if (blockingMove.level != 0 || blockingMove.row != 3 || blockingMove.column != 0 || blockingMove.player != 'O') {
                System.out.println("xWinMultLevel failed. Winning move is in incorrect spot");
            } else {
                System.out.println("xWinMultLevel passed!");
            }
        }

        //tests multiple 'O' wins on multiple and single levels
        TTT3DBoard x3WinningMoves = readBoardFromFile("test boards/x3WinningMoves.txt");
        blockingMoves =  mover.blockingMoves(x3WinningMoves);
        if (blockingMoves.size() == 0 || blockingMoves.size() >3) {
            System.out.printf("xWinMultLevel failed. List has %d winning moves.", blockingMoves.size());
        } else {
            for (TTT3DMove move : blockingMoves) {
                if (move.level == 2) {
                    if (move.row != 2 || move.column != 2 || move.player != 'O') {
                        System.out.println("x3WinningMoves failed. Winning move is incorrect");
                    } else {
                        System.out.println("x3 Test 1/3 passed");
                    }

                }
                else if (move.column == 3){
                    if (move.row != 0 || move.level != 3 || move.player != 'O') {
                        System.out.println("x3WinningMoves failed. Winning move is incorrect");
                    } else {
                        System.out.println("x3 Test  part 2/3 passed");
                    }

                }
                else {
                    if (move.level != 3 || move.row != 3 || move.column != 0 || move.player != 'O') {
                        System.out.println("x3WinningMoves failed. Winning move is incorrect");
                    } else {
                        System.out.println("x3 Test 3/3 passed");
                    }
                }
            }
        }
    }

    @org.junit.jupiter.api.Test
    void forcingMoves() {
        TTT3DMover mover = new TTT3DMover();

        TTT3DBoard empty = readBoardFromFile("test boards/empty.txt");
        TTT3DBoard xForceOneLevel = readBoardFromFile("test boards/xForceOneLevel.txt");
        TTT3DBoard xForceMultiLevel = readBoardFromFile("test boards/xForceMultiLevel.txt");
        TTT3DBoard xNoForce = readBoardFromFile("test boards/xNoForce.txt");

        List<TTT3DMove> forcingMoves =  mover.forcingMoves(empty);
        if (forcingMoves.size() > 0) {
            System.out.println("empty test failed");
        } else {
            System.out.println("empty test passed");
        }

        forcingMoves =  mover.forcingMoves(xForceOneLevel);
        for (TTT3DMove move : forcingMoves) {
            TTT3DBoard xForceOneLevelCopy = new TTT3DBoard(xForceOneLevel);
            xForceOneLevelCopy.makeMove(move);
            List<TTT3DMove> winningMoves = mover.winningMoves(xForceOneLevelCopy);
            if (winningMoves.size() == 2) {
                if (move.level == 0 && move.row == 0 && move.column == 0 && move.player == 'X') {
                    System.out.println("Test passed");
                } else {
                    System.out.println("Test failed, wrong forcing move");
                }
            } else {
                System.out.println("Test failed");
            }
        }

        forcingMoves =  mover.forcingMoves(xForceMultiLevel);
        for (TTT3DMove move : forcingMoves) {
            TTT3DBoard xForceMultiLevelCopy = new TTT3DBoard(xForceMultiLevel);
            xForceMultiLevelCopy.makeMove(move);
            List<TTT3DMove> winningMoves = mover.winningMoves(xForceMultiLevelCopy);
            if (winningMoves.size() == 2) {
                if (move.level == 0 && move.row == 0 && move.column == 0 && move.player == 'X') {
                    System.out.println("Test passed");
                } else {
                    System.out.println("Test failed, wrong forcing move");
                }
            } else {
                System.out.println("Test failed");
            }
        }

        forcingMoves =  mover.forcingMoves(xNoForce);
        for (TTT3DMove move : forcingMoves) {
            TTT3DBoard xNoForceCopy = new TTT3DBoard(xNoForce);
            xNoForceCopy.makeMove(move);
            List<TTT3DMove> winningMoves = mover.winningMoves(xNoForceCopy);
            if (winningMoves.size() == 0) {
                System.out.println("Test passed");
            } else {
                System.out.println("Test failed");
            }
        }
    }

    @org.junit.jupiter.api.Test
    void bestMove() {
        TTT3DMover mover = new TTT3DMover();

        TTT3DBoard empty = readBoardFromFile("test boards/empty.txt");
        if (mover.bestMove(empty) == null) {
            System.out.println("empty test failed");
        } else {
            System.out.println("empty test failed");
        }

            // If no winning, blocking, or forcing move, best move should be in a
        // row/col with another X that does not have an O

        TTT3DBoard xBestWinning = readBoardFromFile("test boards/xBestWinning.txt");
        TTT3DMove bestMove = mover.bestMove(xBestWinning);
        if (bestMove.level == 1 && bestMove.row == 0 && bestMove.column == 3 && bestMove.player == 'X') {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }

        TTT3DBoard xBestBlocking = readBoardFromFile("test boards/xBestBlocking.txt");
        bestMove = mover.bestMove(xBestBlocking);
        if (bestMove.level == 2 && bestMove.row == 3 && bestMove.column == 3 && bestMove.player == 'X') {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }

        TTT3DBoard xBestForcing = readBoardFromFile("test boards/xBestForcing.txt");
        bestMove = mover.bestMove(xBestForcing);
        if (bestMove.level == 0 && bestMove.row == 0 && bestMove.column == 0 && bestMove.player == 'X') {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
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