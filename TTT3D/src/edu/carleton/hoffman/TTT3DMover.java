package edu.carleton.hoffman;

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
 * @author Chris Tordi and Martin Hoffman
 *
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
        List<TTT3DMove> winningMoves = new ArrayList<TTT3DMove>();
        // Populate a bullshit data structure
        List<ArrayList<ArrayList<TTT3DMove>>> horizontals = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
        for (int lvl = 0; lvl < 4; lvl++) {
            horizontals.add(new ArrayList<ArrayList<TTT3DMove>>());
            for (int row = 0; row < 4; row++) {
                horizontals.get(lvl).add(new ArrayList<TTT3DMove>());
                for (int col = 0; col < 4; col++) {
                    if (board.valueInSquare(lvl, row, col) != '-') {
                        horizontals.get(lvl).get(row).add(new TTT3DMove(lvl, row, col,
                                                          board.valueInSquare(lvl, row, col)));
                    }
                }
            }
        }
        // scans moves in horizontals and increments  count if  move.player == board.getWhoseTurn
        for (ArrayList<ArrayList<TTT3DMove>> level : horizontals) {
            for (ArrayList<TTT3DMove> row : level) {
                int count = 0;
                boolean bool = true;
                for (TTT3DMove move : row) {
                    if (row.size() != 3) {
                        break;
                    } else if (move.player != board.getWhoseTurn()) {
                        bool = false;
                        break;
                    } else {
                        count++;
                    }
                }
                // Runs if there exists a 1D row with 3 moves that have move.player == board.getWhoseTurn
                // and an empty move space
                if (count == 3 && bool) {
                    int colID = 0;
                    for (TTT3DMove move : row) {
                        colID += move.column;
                    }
                    // Adds move to winning move in correct spot
                    TTT3DMove move = row.get(0);
                    int curLevel = move.level;
                    int curRow = move.row;
                    Character curPlayer = move.player;
                    winningMoves.add(new TTT3DMove(curLevel, curRow, 6 - colID, curPlayer));
                }
            }
        }
        //
        List<ArrayList<ArrayList<TTT3DMove>>> verticals = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
        for (int lvl = 0; lvl < 4; lvl++) {
            verticals.add(new ArrayList<ArrayList<TTT3DMove>>());
            for (int col = 0; col < 4; col++) {
                verticals.get(lvl).add(new ArrayList<TTT3DMove>());
                for (int row = 0; row < 4; row++) {
                    if (board.valueInSquare(lvl, row, col) != '-') {
                        verticals.get(lvl).get(col).add(new TTT3DMove(lvl, row, col,
                                                                      board.valueInSquare(lvl, row, col)));
                    }
                }
            }
        }
        // Scans moves in verticals and increments  count if  move.player == board.getWhoseTurn
        for (ArrayList<ArrayList<TTT3DMove>> level : verticals) {
            for (ArrayList<TTT3DMove> col : level) {
                int count = 0;
                boolean bool = true;
                for (TTT3DMove move : col) {
                    if (col.size() != 3) {
                        break;
                    } else if (move.player != board.getWhoseTurn()) {
                        bool = false;
                        break;
                    } else {
                        count++;
                    }
                }
                // Runs if there exists a 1D column with 3 moves that have move.player == board.getWhoseTurn
                // and an empty move space
                if (count == 3 && bool) {
                    int rowID = 0;
                    for (TTT3DMove move : col) {
                        rowID += move.row;
                    }
                    // Adds a possible winning vertical move to winningMoves
                    TTT3DMove move = col.get(0);
                    int curLevel = move.level;
                    int curCol = move.column;
                    Character curPlayer = move.player;
                    winningMoves.add(new TTT3DMove(curLevel, 6 - rowID, curCol, curPlayer));
                }
            }
        }
        //
        List<ArrayList<ArrayList<TTT3DMove>>> diagonals = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
        for (int lvl = 0; lvl < 4; lvl++) {
            diagonals.add(new ArrayList<ArrayList<TTT3DMove>>());
            diagonals.get(lvl).add(new ArrayList<TTT3DMove>());
            diagonals.get(lvl).add(new ArrayList<TTT3DMove>());
            for (int i = 0 ; i < 4; i++) {
                if (board.valueInSquare(lvl, i, i) != '-') {
                    diagonals.get(lvl).get(0).add(new TTT3DMove(lvl, i, i, board.valueInSquare(lvl, i, i)));
                }
                if (board.valueInSquare(lvl, 3-i, i) != '-') {
                    diagonals.get(lvl).get(1).add(new TTT3DMove(lvl, 3-i, i, board.valueInSquare(lvl, 3-i, i)));
                }

            }
        }
        // Runs if there exists a 1D diagonal with 3 moves that have move.player == board.getWhoseTurn
        for (ArrayList<ArrayList<TTT3DMove>> level : diagonals) {
            for (ArrayList<TTT3DMove> diagonal : level) {
                int count = 0;
                boolean bool = true;
                for (TTT3DMove move : diagonal) {
                    if (diagonal.size() != 3) {
                        break;
                    } else if (move.player != board.getWhoseTurn()) {
                        bool = false;
                        break;
                    } else {
                        count++;
                    }
                }
                // Runs if there exists a 1D column with 3 moves that have move.player == board.getWhoseTurn
                // and an empty move space
                if (count == 3 && bool) {
                    int rowID = 0;
                    int colID = 0;
                    for (TTT3DMove move : diagonal) {
                        rowID += move.row;
                        colID += move.column;
                    }
                    // Adds possible winning diagonal moves to winningMoves
                    TTT3DMove move = diagonal.get(0);
                    int curLevel = move.level;
                    Character curPlayer = move.player;
                    winningMoves.add(new TTT3DMove(curLevel, 6-rowID, 6 - colID, curPlayer));
                }
            }
        }
        //
        List<ArrayList<ArrayList<TTT3DMove>>> dVerticals = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
        for (int col = 0; col < 4; col++) {
            dVerticals.add(new ArrayList<ArrayList<TTT3DMove>>());
            for (int row = 0; row < 4; row++) {
                dVerticals.get(col).add(new ArrayList<TTT3DMove>());
                for (int lvl = 0; lvl < 4; lvl++) {
                    if (board.valueInSquare(lvl, row, col) != '-') {
                        dVerticals.get(col).get(row).add(new TTT3DMove(lvl, row, col,
                                                                       board.valueInSquare(lvl, row, col)));
                    }
                }
            }
        }
        // Scans moves in dverticals and increments  count if  move.player == board.getWhoseTurn
        for (ArrayList<ArrayList<TTT3DMove>> level : dVerticals) {
            for (ArrayList<TTT3DMove> col : level) {
                int count = 0;
                boolean bool = true;
                for (TTT3DMove move : col) {
                    if (col.size() != 3) {
                        break;
                    } else if (move.player != board.getWhoseTurn()) {
                        bool = false;
                        break;
                    } else {
                        count++;
                    }
                }
                // Runs if there exists a 3D column with 3 moves that have move.player == board.getWhoseTurn
                // and an empty move space
                if (count == 3 && bool) {
                    int lvlID = 0;
                    for (TTT3DMove move : col) {
                        lvlID += move.level;
                    }
                    // Adds possible winning 3d vertical moves to winningMoves
                    TTT3DMove move = col.get(0);
                    int curRow = move.row;
                    int curCol = move.column;
                    Character curPlayer = move.player;
                    winningMoves.add(new TTT3DMove(6 - lvlID, curRow, curCol, curPlayer));
                }
            }
        }
    //
        List<ArrayList<ArrayList<TTT3DMove>>> dHorizontals = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
        for (int row = 0; row < 4; row++) {
            dHorizontals.add(new ArrayList<ArrayList<TTT3DMove>>());
            dHorizontals.get(row).add(new ArrayList<TTT3DMove>());
            dHorizontals.get(row).add(new ArrayList<TTT3DMove>());
            for (int i = 0 ; i < 4; i++) {
                if (board.valueInSquare(i, row, i) != '-') {
                    dHorizontals.get(row).get(0).add(new TTT3DMove(i, row, i, board.valueInSquare(i, row, i)));
                }
                if (board.valueInSquare(3-i, row, i) != '-') {
                    dHorizontals.get(row).get(1).add(new TTT3DMove(3-i, row, i,
                                                                   board.valueInSquare(3-i, row, i)));
                }

            }
        }
        // Scans moves in dHorizontals and increments  count if  move.player == board.getWhoseTurn
        for (ArrayList<ArrayList<TTT3DMove>> row : dHorizontals) {
            for (ArrayList<TTT3DMove> diagonal : row) {
                int count = 0;
                boolean bool = true;
                for (TTT3DMove move : diagonal) {
                    if (diagonal.size() != 3) {
                        break;
                    } else if (move.player != board.getWhoseTurn()) {
                        bool = false;
                        break;
                    } else {
                        count++;
                    }
                }
                // Runs if there exists a 3D row with 3 moves that have move.player == board.getWhoseTurn
                // and an empty move space
                if (count == 3 && bool) {
                    int lvlID = 0;
                    int colID = 0;
                    for (TTT3DMove move : diagonal) {
                        lvlID += move.level;
                        colID += move.column;
                    }
                    // Adds possible winning 3d horizontal moves to winningMoves
                    TTT3DMove move = diagonal.get(0);
                    int curLevel = move.level;
                    int curRow = move.row;
                    Character curPlayer = move.player;
                    winningMoves.add(new TTT3DMove(6-lvlID, curRow, 6 - colID, curPlayer));
                }
            }
        }
        //
        List<ArrayList<TTT3DMove>> dDiagonals = new ArrayList<ArrayList<TTT3DMove>>();
        dDiagonals.add(new ArrayList<TTT3DMove>());
        dDiagonals.add(new ArrayList<TTT3DMove>());
        dDiagonals.add(new ArrayList<TTT3DMove>());
        dDiagonals.add(new ArrayList<TTT3DMove>());
        for (int i = 0 ; i < 4; i++) {
            if (board.valueInSquare(i, i, i) != '-') {
                dDiagonals.get(0).add(new TTT3DMove(i, i, i, board.valueInSquare(i, i, i)));
            }
            if (board.valueInSquare(i, 3-i, i) != '-') {
                dDiagonals.get(1).add(new TTT3DMove(i, 3-i, i, board.valueInSquare(i, 3-i, i)));
            }
            if (board.valueInSquare(3-i, i, i) != '-') {
                dDiagonals.get(2).add(new TTT3DMove(3-i, i, i, board.valueInSquare(3-i, i, i)));
            }
            if (board.valueInSquare(3-i, 3-i, i) != '-') {
                dDiagonals.get(3).add(new TTT3DMove(3-i, 3-i, i,
                                                    board.valueInSquare(3-i, 3-i, i)));
            }
        }
        // Scans moves in dDiagonals and increments  count if  move.player == board.getWhoseTurn
        for (ArrayList<TTT3DMove> diagonal : dDiagonals) {
            int count = 0;
            boolean bool = true;
            for (TTT3DMove move : diagonal) {
                if (diagonal.size() != 3) {
                    break;
                } else if (move.player != board.getWhoseTurn()) {
                    bool = false;
                    break;
                } else {
                    count++;
                }
            }
            // Runs if there exists a 3D diagonal with 3 moves that have move.player == board.getWhoseTurn
            // and an empty move space
            if (count == 3 && bool) {
                int lvlID = 0;
                int rowID = 0;
                int colID = 0;
                for (TTT3DMove move : diagonal) {
                    lvlID += move.level;
                    rowID += move.row;
                    colID += move.column;
                }
                // Adds possible winning 3d diagonal moves to winningMoves
                TTT3DMove move = diagonal.get(0);
                Character curPlayer = move.player;
                winningMoves.add(new TTT3DMove(6-lvlID, 6-rowID, 6 - colID, curPlayer));
            }
        }
        //creates list of duplicate winning moves
        List<TTT3DMove> removedWins = new ArrayList<TTT3DMove>();
        for (TTT3DMove move : winningMoves) {
            for (TTT3DMove move2 : winningMoves) {
                if (compareMoves(move, move2) && move != move2) {
                    removedWins.add(move2);
                }
            }
        }
        //removes duplicate winning moves
        for (int i = 0; i < removedWins.size() / 2; i++) {
            winningMoves.remove(removedWins.get(i));
        }
        return winningMoves;
    }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return a (possibly empty) list of moves that the non-current player could take
     * to win the game in a single turn. That is, these are positions where the current
     * player should play to avoid losing on the opponent's next turn.
     */
    public List<TTT3DMove> blockingMoves(TTT3DBoard board) {

        List<TTT3DMove> blockingMoves = new ArrayList<TTT3DMove>();
        //
        List<ArrayList<ArrayList<TTT3DMove>>> horizontals = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
        for (int lvl = 0; lvl < 4; lvl++) {
            horizontals.add(new ArrayList<ArrayList<TTT3DMove>>());
            for (int row = 0; row < 4; row++) {
                horizontals.get(lvl).add(new ArrayList<TTT3DMove>());
                for (int col = 0; col < 4; col++) {
                    if (board.valueInSquare(lvl, row, col) != '-') {
                        horizontals.get(lvl).get(row).add(new TTT3DMove(lvl, row, col,
                                                                        board.valueInSquare(lvl, row, col)));
                    }
                }
            }
        }
        //
        List<ArrayList<ArrayList<TTT3DMove>>> verticals = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
        for (int lvl = 0; lvl < 4; lvl++) {
            verticals.add(new ArrayList<ArrayList<TTT3DMove>>());
            for (int col = 0; col < 4; col++) {
                verticals.get(lvl).add(new ArrayList<TTT3DMove>());
                for (int row = 0; row < 4; row++) {
                    if (board.valueInSquare(lvl, row, col) != '-') {
                        verticals.get(lvl).get(col).add(new TTT3DMove(lvl, row, col,
                                                                      board.valueInSquare(lvl, row, col)));
                    }
                }
            }
        }
        //
        List<ArrayList<ArrayList<TTT3DMove>>> diagonals = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
        for (int lvl = 0; lvl < 4; lvl++) {
            diagonals.add(new ArrayList<ArrayList<TTT3DMove>>());
            diagonals.get(lvl).add(new ArrayList<TTT3DMove>());
            diagonals.get(lvl).add(new ArrayList<TTT3DMove>());
            for (int i = 0 ; i < 4; i++) {
                if (board.valueInSquare(lvl, i, i) != '-') {
                    diagonals.get(lvl).get(0).add(new TTT3DMove(lvl, i, i, board.valueInSquare(lvl, i, i)));
                }
                if (board.valueInSquare(lvl, 3-i, i) != '-') {
                    diagonals.get(lvl).get(1).add(new TTT3DMove(lvl, 3-i, i, board.valueInSquare(lvl, 3-i, i)));
                }

            }
        }

        // Scans moves in horizontals and increments  count if  move.player != board.getWhoseTurn
        for (ArrayList<ArrayList<TTT3DMove>> level : horizontals) {
            for (ArrayList<TTT3DMove> row : level) {
                int count = 0;
                boolean bool = true;
                for (TTT3DMove move : row) {
                    if (row.size() != 3) {
                        break;
                    } else if (move.player != board.getWhoseTurn()) {
                        count++;
                    } else {
                        bool = false;
                        break;
                    }
                }
                // Runs if there exists row with 3 moves that have move.player != board.getWhoseTurn
                // and an empty move space
                if (count == 3 && bool) {
                    int colID = 0;
                    for (TTT3DMove move : row) {
                        colID += move.column;
                    }
                    TTT3DMove move = row.get(0);
                    int curLevel = move.level;
                    int curRow = move.row;
                    blockingMoves.add(new TTT3DMove(curLevel, curRow, 6 - colID, board.getWhoseTurn()));
                }
            }
        }
        // Scans moves in verticals and increments  count if  move.player != board.getWhoseTurn
        for (ArrayList<ArrayList<TTT3DMove>> level : verticals) {
            for (ArrayList<TTT3DMove> col : level) {
                int count = 0;
                boolean bool = true;
                for (TTT3DMove move : col) {
                    if (col.size() != 3) {
                        break;
                    } else if (move.player != board.getWhoseTurn()) {
                        count++;
                    } else {
                        bool = false;
                        break;
                    }
                }
                // Runs if there exists a column with 3 moves that have move.player != board.getWhoseTurn
                // and an empty move space
                if (count == 3 && bool) {
                    int rowID = 0;
                    for (TTT3DMove move : col) {
                        rowID += move.row;
                    }
                    TTT3DMove move = col.get(0);
                    int curLevel = move.level;
                    int curCol = move.column;
                    blockingMoves.add(new TTT3DMove(curLevel, 6 - rowID, curCol, board.getWhoseTurn()));
                }
            }
        }
        // Scans moves in diagonals and increments  count if  move.player != board.getWhoseTurn
        for (ArrayList<ArrayList<TTT3DMove>> level : diagonals) {
            for (ArrayList<TTT3DMove> diagonal : level) {
                int count = 0;
                boolean bool = true;
                for (TTT3DMove move : diagonal) {
                    if (diagonal.size() != 3) {
                        break;
                    } else if (move.player != board.getWhoseTurn()) {
                        count++;
                    } else {
                        bool = false;
                        break;
                    }
                }
                // Runs if there exists a diagonal with 3 moves that have move.player != board.getWhoseTurn
                // and an empty move space
                if (count == 3 && bool) {
                    int rowID = 0;
                    int colID = 0;
                    for (TTT3DMove move : diagonal) {
                        rowID += move.row;
                        colID += move.column;
                    }
                    TTT3DMove move = diagonal.get(0);
                    int curLevel = move.level;
                    int curRow = move.row;
                    blockingMoves.add(new TTT3DMove(curLevel, 6-rowID, 6 - colID, board.getWhoseTurn()));
                }
            }
        }

        List<ArrayList<ArrayList<TTT3DMove>>> dVerticals = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
        for (int col = 0; col < 4; col++) {
            dVerticals.add(new ArrayList<ArrayList<TTT3DMove>>());
            for (int row = 0; row < 4; row++) {
                dVerticals.get(col).add(new ArrayList<TTT3DMove>());
                for (int lvl = 0; lvl < 4; lvl++) {
                    if (board.valueInSquare(lvl, row, col) != '-') {
                        dVerticals.get(col).get(row).add(new TTT3DMove(lvl, row, col,
                                                                       board.valueInSquare(lvl, row, col)));
                    }
                }
            }
        }
        // Scans moves in dverticals and increments  count if  move.player != board.getWhoseTurn
        for (ArrayList<ArrayList<TTT3DMove>> level : dVerticals) {
            for (ArrayList<TTT3DMove> col : level) {
                int count = 0;
                boolean bool = true;
                for (TTT3DMove move : col) {
                    if (col.size() != 3) {
                        break;
                    } else if (move.player != board.getWhoseTurn()) {
                        count++;
                    } else {
                        bool = false;
                        break;
                    }
                }
                // Runs if there exists a 3D column with 3 moves that have move.player != board.getWhoseTurn
                // and an empty move space
                if (count == 3 && bool) {
                    int lvlID = 0;
                    for (TTT3DMove move : col) {
                        lvlID += move.level;
                    }
                    TTT3DMove move = col.get(0);
                    int curRow = move.row;
                    int curCol = move.column;
                    blockingMoves.add(new TTT3DMove(6 - lvlID, curRow, curCol, board.getWhoseTurn()));
                }
            }
        }

        List<ArrayList<ArrayList<TTT3DMove>>> dHorizontals = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
        for (int row = 0; row < 4; row++) {
            dHorizontals.add(new ArrayList<ArrayList<TTT3DMove>>());
            dHorizontals.get(row).add(new ArrayList<TTT3DMove>());
            dHorizontals.get(row).add(new ArrayList<TTT3DMove>());
            for (int i = 0 ; i < 4; i++) {
                if (board.valueInSquare(i, row, i) != '-') {
                    dHorizontals.get(row).get(0).add(new TTT3DMove(i, row, i, board.valueInSquare(i, row, i)));
                }
                if (board.valueInSquare(3-i, row, i) != '-') {
                    dHorizontals.get(row).get(1).add(new TTT3DMove(3-i, row, i,
                                                                    board.valueInSquare(3-i, row, i)));
                }

            }
        }
        // Scans moves in dhorizontals and increments  count if  move.player != board.getWhoseTurn
        for (ArrayList<ArrayList<TTT3DMove>> row : dHorizontals) {
            for (ArrayList<TTT3DMove> diagonal : row) {
                int count = 0;
                boolean bool = true;
                for (TTT3DMove move : diagonal) {
                    if (diagonal.size() != 3) {
                        break;
                    } else if (move.player != board.getWhoseTurn()) {
                        count++;
                    } else {
                        bool = false;
                        break;
                    }
                }
                // Runs if there exists a 3D row with 3 moves that have move.player != board.getWhoseTurn
                // and an empty move space
                if (count == 3 && bool) {
                    int lvlID = 0;
                    int colID = 0;
                    for (TTT3DMove move : diagonal) {
                        lvlID += move.level;
                        colID += move.column;
                    }
                    TTT3DMove move = diagonal.get(0);
                    int curLevel = move.level;
                    int curRow = move.row;
                    blockingMoves.add(new TTT3DMove(6-lvlID, curRow, 6 - colID, board.getWhoseTurn()));
                }
            }
        }

        List<ArrayList<TTT3DMove>> dDiagonals = new ArrayList<ArrayList<TTT3DMove>>();
        dDiagonals.add(new ArrayList<TTT3DMove>());
        dDiagonals.add(new ArrayList<TTT3DMove>());
        dDiagonals.add(new ArrayList<TTT3DMove>());
        dDiagonals.add(new ArrayList<TTT3DMove>());
        for (int i = 0 ; i < 4; i++) {
            if (board.valueInSquare(i, i, i) != '-') {
                dDiagonals.get(0).add(new TTT3DMove(i, i, i, board.valueInSquare(i, i, i)));
            }
            if (board.valueInSquare(i, 3-i, i) != '-') {
                dDiagonals.get(1).add(new TTT3DMove(i, 3-i, i, board.valueInSquare(i, 3-i, i)));
            }
            if (board.valueInSquare(3-i, i, i) != '-') {
                dDiagonals.get(2).add(new TTT3DMove(3-i, i, i, board.valueInSquare(3-i, i, i)));
            }
            if (board.valueInSquare(3-i, 3-i, i) != '-') {
                dDiagonals.get(3).add(new TTT3DMove(3-i, 3-i, i, board.valueInSquare(3-i, 3-i, i)));
            }
        }
        // Scans moves in dDiagonals and increments  count if  move.player == board.getWhoseTurn
        for (ArrayList<TTT3DMove> diagonal : dDiagonals) {
            int count = 0;
            boolean bool = true;
            for (TTT3DMove move : diagonal) {
                if (diagonal.size() != 3) {
                    break;
                } else if (move.player != board.getWhoseTurn()) {
                    count++;
                } else {
                    bool = false;
                    break;
                }
            }
            // Runs if there exists a 3D diagonal with 3 moves that have move.player != board.getWhoseTurn
            // and an empty move space
            if (count == 3 && bool) {
                int lvlID = 0;
                int rowID = 0;
                int colID = 0;
                for (TTT3DMove move : diagonal) {
                    lvlID += move.level;
                    rowID += move.row;
                    colID += move.column;
                }
                TTT3DMove move = diagonal.get(0);
                blockingMoves.add(new TTT3DMove(6-lvlID, 6-rowID, 6 - colID, board.getWhoseTurn()));
            }
        }
        // Create a list of duplicate wins
        List<TTT3DMove> removedWins = new ArrayList<TTT3DMove>();
        for (TTT3DMove move : blockingMoves) {
            for (TTT3DMove move2 : blockingMoves) {
                if (compareMoves(move, move2) && move != move2) {
                    removedWins.add(move2);
                }
            }
        }
        // Remove duplicate entries in removedWins
        for (int i = 0; i < removedWins.size() / 2; i++) {
            blockingMoves.remove(removedWins.get(i));
        }
        return blockingMoves;
    }

    /**
     * Default method.
     * @param board
     * @return a representation of board as a string.
     */
    public String writeToString(TTT3DBoard board) {
        return writeToString(board, new ArrayList<TTT3DMove>());
    }

    /**
     * Overloaded method.
     * @param board
     * @param asterisks a list of moves to be added as asterisks to indicate potential moves.
     * @return a representation of board as a string and the moves in asterisks.
     */
    private String writeToString(TTT3DBoard board, List<TTT3DMove> asterisks) {
        String stringBoard = "";
        // Iterate through each square
        for (int i = 0; i < 4; i++) {
            // Add a newline at the end of each level
            if (i > 0) {
                stringBoard = stringBoard + "\n";
            }
            for (int j = 0; j < 4; j++) {
                // Add a space after each row
                if (j > 0) {
                    stringBoard = stringBoard + " ";
                }
                for (int k = 0; k < 4; k++) {
                    boolean addedAsterisk = false;
                    // Add an asterisk if necessary
                    for (TTT3DMove move : asterisks) {
                        if (move.level == i && move.row == j && move.column == k) {
                            stringBoard += '*';
                            addedAsterisk = true;
                        }
                    }
                    // If an  asterisk was not added, add a character
                    if (!addedAsterisk) {
                        stringBoard = stringBoard + board.valueInSquare(i,j,k);
                    }
                }
            }
        }

        return stringBoard;
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
        List<TTT3DMove> forcingMoves = new ArrayList<TTT3DMove>();
        int curWinningMoves = winningMoves(board).size();
        // Iterate through each square
        for (int lvl = 0; lvl < 4; lvl++) {
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    TTT3DBoard boardCopy = new TTT3DBoard(board);
                    // If a square is empty, evaluate whether making a move there would create two new wins
                    if (boardCopy.valueInSquare(lvl, row, col) == '-') {
                        TTT3DMove possibleForce;
                        if (boardCopy.getWhoseTurn() == null) {
                            possibleForce = new TTT3DMove(lvl, row, col, 'X');
                        } else {
                            possibleForce = new TTT3DMove(lvl, row, col, boardCopy.getWhoseTurn());
                        }
                        boardCopy.makeMove(possibleForce);
                        // Making a move has changed whoseTurn, check blockingMoves instead of winningMoves
                        if (blockingMoves(boardCopy).size() >= (curWinningMoves + 2)) {
                            forcingMoves.add(possibleForce);
                        }
                    }
                }
            }
        }
        return forcingMoves;
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

    /**
     * @param move1
     * @param move2
     * @return true if move1 has instance variables identical to move2, else return false.
     */
    private boolean compareMoves(TTT3DMove move1, TTT3DMove move2) {
        if (move1.level == move2.level && move1.row == move2.row &&
                move1.column == move2.column && move1.player == move2.player) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Valid text files should begin boards on level zero and contain exactly 65
     * total X's O's and -'s: the board contents and an X or O to indicate the
     * current player's turn.
     * @param filePath
     * @return the board represented by the text file at filePath.
     */
    private TTT3DBoard readBoardFromFile(String filePath) {
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
        Character whoseTurn = ' ';
        int count64 = 0;
        while(scanner.hasNext()) {
            String line = scanner.next();
            for (int i = 0; i < line.length(); i++) {
                String chr = line.substring(i, i + 1);
                if (count64 == 63) {
                    whoseTurn = chr.charAt(0);
                } else if (chr.matches("[XO-]")) {
                    chrs += chr;
                    count64++;
                }
            }
        }
        // Convert chrs to move objects
        List<TTT3DMove> moves = new ArrayList<TTT3DMove>();
        for (int i = 0; i < chrs.length(); i++) {
            char chr = chrs.charAt(i);
            if (chr != '-') {
                moves.add(new TTT3DMove(i / 16, (i%16)/4, i%4, chr));
            }
        }
        // Separate moves for player 'X'
        List<TTT3DMove> xMoves = new ArrayList<TTT3DMove>();
        for (TTT3DMove move : moves) {
            if (move.player == 'X') {
                xMoves.add(move);
            }
        }
        // Separate moves for player 'O'
        List<TTT3DMove> oMoves = new ArrayList<TTT3DMove>();
        for (TTT3DMove move : moves) {
            if (move.player == 'O') {
                oMoves.add(move);
            }
        }
        //
        TTT3DBoard board = new TTT3DBoard();
        if ((whoseTurn == 'X' && xMoves.size() == oMoves.size()) || xMoves.size() > oMoves.size()) {
            board = new TTT3DBoard();
            for (int i = 0; i < xMoves.size(); i++) {
                board.makeMove(xMoves.get(i));
                if (i < oMoves.size()) {
                    board.makeMove(oMoves.get(i));
                }
            }
        } else if ((whoseTurn == 'O' && xMoves.size() == oMoves.size()) || oMoves.size() > xMoves.size()) {
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
        String function = args[0].toLowerCase();
        String filePath = args[1];

        TTT3DMover mover = new TTT3DMover();
        TTT3DBoard board = mover.readBoardFromFile(filePath);

        switch (function) {
            case "win":
                List<TTT3DMove> winningMoves = mover.winningMoves(board);
                System.out.println(mover.writeToString(board, winningMoves));
                break;
            case "block":
                List<TTT3DMove> blockingMoves = mover.blockingMoves(board);
                System.out.println(mover.writeToString(board, blockingMoves));
                break;
            case "force":
                List<TTT3DMove> forcingMoves = mover.forcingMoves(board);
                System.out.println(mover.writeToString(board, forcingMoves));
                break;
            default:
                System.out.println("Slow down there partner. Pick 'win' 'block' or 'force'");
                break;
        }
    }
}