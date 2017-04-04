package edu.carleton.hoffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * TTT3DMoverTest has a number of useful tests that cover all the methods of TTT3DMove.
 *
 * @author Martin Hoffman
 * @author Chris Tordi
 * @version 30 March 2017.
 */
class TTT3DMoverTest {
	@org.junit.jupiter.api.BeforeEach
	void setUp() { System.out.println("Setting up..."); }

	@org.junit.jupiter.api.AfterEach
	void tearDown() { System.out.println("Tearing down..."); }

	@org.junit.jupiter.api.Test
	void test() {
		Map<String, TTT3DBoard> boards = readBoardsFromFile("test boards (one file)/xWins.txt");
		System.out.println(boards.get("empty").valueInSquare(2, 0, 2));
		System.out.println(boards.get("xWin-HM-303").valueInSquare(2, 0, 2));
	}

	@org.junit.jupiter.api.Test
	void winningMoves() {
		// Import boards
		Map<String, TTT3DBoard> boards = readBoardsFromFile("test boards (one file)/xWins.txt");

		// Instantiate Mover
		TTT3DMover mover = new TTT3DMover();

		// Empty board
		List<TTT3DMove> winningMoves =  mover.winningMoves(boards.get("empty"));
		assert(winningMoves.size() > 0);

		if (winningMoves.size() > 0) {
			System.out.println("empty test failed");
		} else {
			System.out.println("empty test failed");
		}

		// Test empty
		winningMoves =  mover.winningMoves(boards.get("xWin-H-003"));
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

		// Test no available block
		winningMoves =  mover.winningMoves(boards.get("xWin-V-030"));
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

		// Test block on horizontal
		winningMoves =  mover.winningMoves(boards.get("xWin-D-033"));
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


		// Test block on vertical
		winningMoves =  mover.winningMoves(boards.get("xWin-HM-303"));
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

		// Test block on diagonal
		winningMoves =  mover.winningMoves(boards.get("xWin-VM-220"));
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

		// Test block on multi-level horizontal
		winningMoves =  mover.winningMoves(boards.get("xWin-DM-333"));
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

		// Test block on multi-level vertical
		winningMoves =  mover.winningMoves(boards.get("xWin-MM-222-330-303"));
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
		// Import boards
		Map<String, TTT3DBoard> boards = readBoardsFromFile("test boards (one file)/xWins.txt");

		// Instantiate mover
		TTT3DMover mover = new TTT3DMover();

		// Test empty
		List<TTT3DMove> blockingMoves = mover.blockingMoves(boards.get("empty"));
		if (blockingMoves.size() > 0) {
			System.out.println("empty test failed");
		} else {
			System.out.println("empty test passed");
		}

		// Test no available block
		blockingMoves = mover.blockingMoves(boards.get("xWin-None"));
		if (blockingMoves.size() == 0) {
			System.out.printf("xNoWin passed. List has %d winning moves.%n", blockingMoves.size());
		} else {
			System.out.println("xNoWin passed!");
		}

		// Test block on horizontal
		blockingMoves = mover.blockingMoves(boards.get("xWin-H-003"));
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

		// Test block on vertical
		blockingMoves = mover.blockingMoves(boards.get("xWin-V-030"));
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

		// Test block on diagonal
		blockingMoves = mover.blockingMoves(boards.get("xWin-D-033"));
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


		// Test block on multi-level horizontal
		blockingMoves = mover.blockingMoves(boards.get("xWin-HM-303"));
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

		// Test block on multi-level vertical
		blockingMoves = mover.blockingMoves(boards.get("xWin-VM-220"));
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

		// Test block on multi-level diagonal
		blockingMoves = mover.blockingMoves(boards.get("xWin-DM-333"));
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

		// Test block on multiple wins, multiple levels
		blockingMoves = mover.blockingMoves(boards.get("xWin-MM-222-330-303"));
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
		// Import boards
		Map<String, TTT3DBoard> boards = readBoardsFromFile("test boards (one file)/xForce.txt");

		// Instantiate mover
		TTT3DMover mover = new TTT3DMover();

		// Test empty
		List<TTT3DMove> forcingMoves =  mover.forcingMoves(boards.get("empty"););
		if (forcingMoves.size() > 0) {
			System.out.println("empty test failed");
		} else {
			System.out.println("empty test passed");
		}

		// Test no available force
		forcingMoves =  mover.forcingMoves(boards.get("xForceNone"););
		for (TTT3DMove move : forcingMoves) {
			TTT3DBoard xForceNoneCopy = new TTT3DBoard(boards.get("xForceNone"););
			xForceNoneCopy.makeMove(move);
			List<TTT3DMove> winningMoves = mover.winningMoves(xForceNoneCopy);
			if (winningMoves.size() == 0) {
				System.out.println("Test passed");
			} else {
				System.out.println("Test failed");
			}
		}

		// Test force on one level
		forcingMoves =  mover.forcingMoves(boards.get("xForce-S-000"););
		for (TTT3DMove move : forcingMoves) {
			TTT3DBoard xForceOneLevelCopy = new TTT3DBoard(boards.get("xForce-S-000"););
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

		// Test force on multiple levels
		forcingMoves =  mover.forcingMoves(boards.get("xForce-M-000"););
		for (TTT3DMove move : forcingMoves) {
			TTT3DBoard xForceMultiLevelCopy = new TTT3DBoard(boards.get("xForce-M-000"););
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
	}

	@org.junit.jupiter.api.Test
	void bestMove() {
		// Import boards
		Map<String, TTT3DBoard> boards = readBoardsFromFile("test boards (one file)/xWins.txt");

		// Instantiate mover
		TTT3DMover mover = new TTT3DMover();

		// Empty test
		if (mover.bestMove(boards.get("empty");) == null) {
			System.out.println("empty test failed");
		} else {
			System.out.println("empty test failed");
		}

		// The hierarchy of best moves should be win > block > force
		// Test win
		TTT3DMove bestMove = mover.bestMove(boards.get("xBestWin-103"););
		if (bestMove.level == 1 && bestMove.row == 0 && bestMove.column == 3 && bestMove.player == 'X') {
			System.out.println("Test passed");
		} else {
			System.out.println("Test failed");
		}

		// Test block
		bestMove = mover.bestMove(boards.get("xBestBlock-233"););
		if (bestMove.level == 2 && bestMove.row == 3 && bestMove.column == 3 && bestMove.player == 'X') {
			System.out.println("Test passed");
		} else {
			System.out.println("Test failed");
		}

		// Test force
		bestMove = mover.bestMove(boards.get("xBestForce-001"););
		if (bestMove.level == 0 && bestMove.row == 0 && bestMove.column == 0 && bestMove.player == 'X') {
			System.out.println("Test passed");
		} else {
			System.out.println("Test failed");
		}
	}

	private Map<String, TTT3DBoard> readBoardsFromFile(String filePath) {
		// Attempt to open file located at filePath
		File inputFile = new File(filePath);
		Scanner scanner = null;

		try {
			scanner = new Scanner(inputFile);
		} catch(FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		}

		// Create string of characters in input file
		String text = "";
		while(scanner.hasNext()) {
			text += scanner.next();
		}

		// Split text around " character
		List<String> splitText = new ArrayList<String>();
		for (String str : text.split("[\"]")) {
			if (str.length() > 1) {
				splitText.add(str);
			}
		}

		// Clean X's, O's, and -'s
		List<String> splitTextClean = new ArrayList<String>();
		for (String str : splitText) {
			if (splitText.indexOf(str) % 2 == 1) {
				String cleanStr = "";
				for (int i = 0; i < str.length(); i++) {
					if (str.substring(i, i + 1).matches("[XO-]")) {
						cleanStr += str.substring(i, i + 1);
					}
				}
				splitTextClean.add(cleanStr);
			} else {
				splitTextClean.add(str);
			}
		}

		// If there aren't an even number of strings, a board is missing a title or vice versa
		assert (splitTextClean.size() % 2 == 0);

		// Separate label text from board text
		List<String> boardLabels = new ArrayList<String>();
		List<String> boardTexts = new ArrayList<String>();
		for (int i = 0; i < splitTextClean.size(); i++) {
			// Labels should be even entries, boards should be odd entries
			if (i % 2 == 0) {
				boardLabels.add(splitTextClean.get(i));
			} else {
				assert splitTextClean.get(i).length() == 64;		// A board without 64 spaces is incomplete
				boardTexts.add(splitTextClean.get(i));
			}
		}

		// Convert board text to move objects
		List<ArrayList<TTT3DMove>> moveSets = new ArrayList<ArrayList<TTT3DMove>>();
		int moveSetIndex = 0;
		for (String boardText : boardTexts) {
			moveSets.add(new ArrayList<TTT3DMove>());
			for (int i = 0; i < boardText.length(); i++) {
				if (boardText.toCharArray()[i] != '-') {
					// Calculates level, row, and column based on position in the boardText string
					moveSets.get(moveSetIndex).add(new TTT3DMove(0 + i / 16, (i % 16) / 4, i % 4, boardText.toCharArray()[i]));
				}
			}
			moveSetIndex += 1;
		}

		// Separate moves for player 'X' and player 'O' in moveSetsXO
		List<ArrayList<ArrayList<TTT3DMove>>> moveSetsXO = new ArrayList<ArrayList<ArrayList<TTT3DMove>>>();
		for (ArrayList<TTT3DMove> moveSet : moveSets) {
			ArrayList<ArrayList<TTT3DMove>> moveSetXO = new ArrayList<ArrayList<TTT3DMove>>();
			ArrayList<TTT3DMove> xMoves = new ArrayList<TTT3DMove>();
			ArrayList<TTT3DMove> oMoves = new ArrayList<TTT3DMove>();
			for (TTT3DMove move : moveSet) {
				assert (move.player == 'X' || move.player == 'O');
				if (move.player == 'X') {
					xMoves.add(move);
				} else {
					oMoves.add(move);
				}
			}
			moveSetXO.add(xMoves);
			moveSetXO.add(oMoves);
			moveSetsXO.add(moveSetXO);
		}

		// Convert moves to board objects and link to labels in a dictionary
		Map<String, TTT3DBoard> boards = new HashMap<String, TTT3DBoard>();
		for (int i = 0; i < boardLabels.size(); i++) {		// Number of boards should equal number of labels
			TTT3DBoard board = new TTT3DBoard();
			String boardLabel = boardLabels.get(i);
			// Alternate between 'X' and 'O' moves to satisfy turn requirements of board. Assumes first move is an 'X'
			ArrayList<TTT3DMove> xMoves = moveSetsXO.get(i).get(0);
			ArrayList<TTT3DMove> oMoves = moveSetsXO.get(i).get(1);
			for (int j = 0; j < xMoves.size(); j++) {
				board.makeMove(xMoves.get(j));
				if (j < oMoves.size()) {		// oMoves may be one move shorter than xMoves
					board.makeMove(oMoves.get(j));
				}
			}
			boards.put(boardLabel, board);
		}

		return boards;
	}
}