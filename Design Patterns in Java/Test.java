import java.util.Arrays;
import java.util.List;

public class Test {

	// Run "java -ea Test" to run with assertions enabled (If you run
	// with assertions disabled, the default, then assert statements
	// will not execute!)

	// Test given in code skeleton
	public static void test1() {
		Board b = Board.theBoard();
		Piece.registerPiece(new PawnFactory());
		Piece p = Piece.createPiece("bp");
		b.addPiece(p, "a3");
		assert b.getPiece("a3") == p;
	}

	// Test Pawn moves
	public static void test2() {
		// Set up board
		Board b = Board.theBoard();
		b.clear();

		// Add pieces to board
		Piece.registerPiece(new PawnFactory());
		Piece wPawn = Piece.createPiece("wp");
		b.addPiece(wPawn, "b2");

		// Make sure pieces are actually on board
		assert b.getPiece("b2") == wPawn;

		// Should be same for white + black so only test for white
		// Movement case 1: Test moving 1 space vertically forward if space vacant
		List<String> wPawnMoves = wPawn.moves(b, "b2");
		assert wPawnMoves.contains("b3");
		assert !wPawnMoves.contains("b1");  // Can only move 1 space vertically toward opponent's side so shouldn't be able to move "backwards" to position b1

		// Movement case 2: Test moving 2 spaces forward if in home row (row 2 for white)
		assert wPawnMoves.contains("b4");

		// Make sure not able to move forward vertically if the space is not vacant
		// Test having the 1st step forward not be vacant
		Piece bPawn1 = Piece.createPiece("bp");
		b.addPiece(bPawn1, "b3");           // This will block 1 and 2 vertical moves forward
		assert b.getPiece("b3") == bPawn1;

		wPawnMoves = wPawn.moves(b, "b2");  // Board has changed, so get moves again
		assert !wPawnMoves.contains("b3");
		assert !wPawnMoves.contains("b4");

		// Test having the 1st step forward be vacant + 2nd step forward not being vacant
		// So add another white pawn piece to home row
		Piece wPawn2 = Piece.createPiece("wp");
		Piece bPawn4 = Piece.createPiece("bp");
		b.addPiece(wPawn2, "d2");
		b.addPiece(bPawn4, "d4");  // Fill in 2 spots away in col d but leave the 1st space forward vacant

		// Make sure pieces are actually on board
		assert b.getPiece("d2") == wPawn2;
		assert b.getPiece("d4") == bPawn4;

		// Get moves out for this piece
		List<String> wPawn2Moves = wPawn2.moves(b, "d2");
		assert wPawn2Moves.contains("d3");
		assert !wPawn2Moves.contains("d4");

		// Test 2 steps forward if not in home row: shouldn't be possible!
		Piece wPawn3 = Piece.createPiece("wp");
		b.addPiece(wPawn3, "d5");
		List<String> wPawn3Moves = wPawn3.moves(b, "d5");
		assert !wPawn3Moves.contains("d7");

		// Movement case 3: Test capturing opponent's piece by moving diagonally toward them
		// Also test both L + R diagonal captures

		// White pawn at b2 then able capture black piece at a3 or c3
		Piece bPawn2 = Piece.createPiece("bp");
		Piece bPawn3 = Piece.createPiece("bp");
		b.addPiece(bPawn2, "a3");
		b.addPiece(bPawn3, "c3");

		assert b.getPiece("a3") == bPawn2;
		assert b.getPiece("c3") == bPawn3;

		wPawnMoves = wPawn.moves(b, "b2");  // Board has changed, so get moves again
		assert wPawnMoves.contains("a3");       // Left diagonal capture
		assert wPawnMoves.contains("c3");       // Right diagonal capture

		// Make sure it doesn't capture white pieces, that only captures opponent black pieces
		Piece wPawn4 = Piece.createPiece("wp");
		Piece wPawn5 = Piece.createPiece("wp");
		Piece wPawn6 = Piece.createPiece("wp");
		b.addPiece(wPawn4, "e4");
		b.addPiece(wPawn5, "d5");
		b.addPiece(wPawn6, "f5");

		List<String> wPawn4Moves = wPawn4.moves(b, "e4");
		assert !wPawn4Moves.contains("d5");     // Left diagonal capture
		assert !wPawn4Moves.contains("f5");     // Right diagonal capture
	}

	// Test King moves
	public static void test3() {
		// Set up board
		Board b = Board.theBoard();
		b.clear();

		// Up + down tests
		// Check that can't move up if at last row (row 8 for white king)

		// Add pieces to board
		Piece.registerPiece(new KingFactory());
		Piece wKing = Piece.createPiece("wk");
		b.addPiece(wKing, "c8");

		// Make sure pieces are actually on board
		assert b.getPiece("c8") == wKing;

		List<String> wKingMoves = wKing.moves(b, "c8");
		assert !wKingMoves.contains("c9");

		// Failure case 2: check that can't move if space occupied + same color
		// Add pieces to board
		Piece wKing1 = Piece.createPiece("wk");
		b.addPiece(wKing1, "c7");

		List<String> wKing1Moves = wKing1.moves(b, "c7");
		assert !wKing1Moves.contains("c8");

		// Check can move up if space empty
		Piece wKing2 = Piece.createPiece("wk");
		b.addPiece(wKing2, "b4");
		List<String> wKing2Moves = wKing2.moves(b, "b4");

		assert wKing2Moves.contains("b5");

		// Check can move up if space is occupied by opponent (black) piece
		Piece bKing = Piece.createPiece("bk");
		b.addPiece(bKing, "b5");
		assert b.getPiece("b5") == bKing;

		wKing2Moves = wKing2.moves(b, "b4");

		assert wKing2Moves.contains("b5");

		// L + R tests
		// Check that can move L if empty, check that can move R if opponent there
		Piece wKing3 = Piece.createPiece("wk");
		Piece bKing2 = Piece.createPiece("bk");
		b.addPiece(wKing3, "e7");
		b.addPiece(bKing2, "f7");

		// Make sure pieces are actually on board
		assert b.getPiece("e7") == wKing3;
		assert b.getPiece("f7") == bKing2;

		List<String> wKing3Moves = wKing3.moves(b, "e7");
		assert wKing3Moves.contains("d7");
		assert wKing3Moves.contains("f7");

		// Diagonal tests
		Piece wKing4 = Piece.createPiece("wk");
		Piece bKing3 = Piece.createPiece("bk");
		Piece wKing5 = Piece.createPiece("wk");
		b.addPiece(wKing4, "d3");
		b.addPiece(bKing3, "c2");
		b.addPiece(wKing5, "e2");

		// Make sure pieces are actually on board
		assert b.getPiece("d3") == wKing4;
		assert b.getPiece("c2") == bKing3;
		assert b.getPiece("e2") == wKing5;

		List<String> wKing4Moves = wKing4.moves(b, "d3");
		assert wKing4Moves.contains("c4");  // Empty so should be able to move there
		assert wKing4Moves.contains("c2");  // Black opponent so should be able to move there
		assert wKing4Moves.contains("e4");  // Empty so should be able to move there
		assert !wKing4Moves.contains("e2");  // Shouldn't be able to move there bc white piece there
	}

	// Test Queen moves
	public static void test4() {
		// Test case 1: Test on an empty board to make sure that are able to make all movements
		// Also make sure that all constraints on valid pieces of board are implemented correctly
		Board b = Board.theBoard();
		b.clear();

		// Add white Queen in middle of board
		Piece.registerPiece(new QueenFactory());
		Piece wQueen = Piece.createPiece("wq");
		b.addPiece(wQueen, "d4");

		// Make list of all the other spots on board where Queen should be able to move
		// When move in line L: should be able to reach a4, b4, c4
		// R valid movements: e4, f4, g4, h4
		// up valid movements: d5, d6, d7, d8
		// down valid movements: d3, d2, d1
		// diagonally up L: c5, b6, a7
		// diagonally up R: e5, f6, g7, h8
		// diagonally down L: c3, b2, a1
		// diagonally down R: e3, f2, g1
		List<String> expectedValidMoves = Arrays.asList(
				"a4", "b4", "c4",
				"e4", "f4", "g4", "h4",
				"d5", "d6", "d7", "d8",
				"d3", "d2", "d1",
				"c5", "b6", "a7",
				"e5", "f6", "g7", "h8",
				"c3", "b2", "a1",
				"e3", "f2", "g1"
		);

		// Get out the actual moves from the Queen piece
		List<String> actualValidMoves = wQueen.moves(b, "d4");
		assert b.getPiece("d4") == wQueen;

		// Check that the actually valid moves are the exact same as the expected valid moves
		assert actualValidMoves.containsAll(expectedValidMoves);

		// AND make sure that are not able to reach row 9 or row 0!!!
		assert !actualValidMoves.contains("d9");
		assert !actualValidMoves.contains("d0");

		// Test case 2: should stop if come across position that is occupied by same color piece
		Piece wQueen2 = Piece.createPiece("wq");
		b.addPiece(wQueen2, "f4");
		assert b.getPiece("f4") == wQueen2;

		actualValidMoves = wQueen.moves(b, "d4");  // Recall moves bc have made changes to board
		assert !actualValidMoves.contains("f4");
		assert !actualValidMoves.contains("g4");
		assert !actualValidMoves.contains("h4");

		// Test case 3: if come across opponent piece then capture it
		Piece bQueen = Piece.createPiece("bq");
		b.addPiece(bQueen, "d2");
		assert b.getPiece("d2") == bQueen;

		actualValidMoves = wQueen.moves(b, "d4");
		assert actualValidMoves.contains("d2");
	}

	// Test Knight moves
	public static void test5() {
		// Test on empty board so that can make sure all 8 moves should be able to do are valid
		Board b = Board.theBoard();
		b.clear();

		// Add white Queen in middle of board
		Piece.registerPiece(new KnightFactory());
		Piece wKnight = Piece.createPiece("wn");
		b.addPiece(wKnight, "d4");

		// Make list of all the other spots on board where Knight should be able to move
		// 2 R, 1 up: f5
		// 2 R, 1 down: f3
		// 2 L, 1 up: b5
		// 2 L, 1 down: b3
		// 2 up, 1 R: e6
		// 2 up, 1 L: c6
		// 2 down, 1 L: c2
		// 2 down, 1 R: e2
		List<String> expectedValidMoves = Arrays.asList(
				"f5", "f3", "b5", "b3", "e6", "c6", "c2", "e2"
		);

		// Get out the actual moves from the Knight piece
		List<String> actualValidMoves = wKnight.moves(b, "d4");
		assert b.getPiece("d4") == wKnight;

		// Check that the actually valid moves are the exact same as the expected valid moves
		assert actualValidMoves.containsAll(expectedValidMoves);

		// Now try moving Knight from position where L shape takes off board to test if board constraints have set are valid
		Piece wKnight2 = Piece.createPiece("wn");
		b.addPiece(wKnight2, "h7");

		// Valid moves:
		// no: 2R, 1 up or 2R, 1 down or 2 up, 1 R or 2 up, 1 L or 2 down, 1 R
		// valid moves: f8, f6, g5
		List<String> expectedValidMoves2 = Arrays.asList(
				"f8", "f6", "g5"
		);

		List<String> actualValidMoves2 = wKnight2.moves(b, "h7");
		assert b.getPiece("h7") == wKnight2;

		// Check that the actually valid moves are the exact same as the expected valid moves
		assert actualValidMoves2.containsAll(expectedValidMoves2);

		// Check that don't capture if same color piece there
		Piece wKnight3 = Piece.createPiece("wn");
		b.addPiece(wKnight3, "f6");
		expectedValidMoves2 = wKnight2.moves(b, "h7");
		assert !expectedValidMoves2.contains("f6");

		// Check that capture if opponent is at position
		Piece bKnight = Piece.createPiece("bn");
		b.addPiece(bKnight, "g5");
		expectedValidMoves2 = wKnight2.moves(b, "h7");
		assert expectedValidMoves2.contains("g5");
	}

	// Test Bishop moves
	public static void test6() {
		// Test case 1: Test on an empty board to make sure that are able to make all movements
		// Also make sure that all constraints on valid pieces of board are implemented correctly
		Board b = Board.theBoard();
		b.clear();

		// Add white Queen in middle of board
		Piece.registerPiece(new BishopFactory());
		Piece wBishop = Piece.createPiece("wb");
		b.addPiece(wBishop, "d4");

		// Make list of all the other spots on board where Bishop should be able to move
		// diagonally up L: c5, b6, a7
		// diagonally up R: e5, f6, g7, h8
		// diagonally down L: c3, b2, a1
		// diagonally down R: e3, f2, g1
		List<String> expectedValidMoves = Arrays.asList(
				"c5", "b6", "a7",
				"e5", "f6", "g7", "h8",
				"c3", "b2", "a1",
				"e3", "f2", "g1"
		);

		// Get out the actual moves from the Bishop piece
		List<String> actualValidMoves = wBishop.moves(b, "d4");
		assert b.getPiece("d4") == wBishop;

		// Check that the actually valid moves are the exact same as the expected valid moves
		assert actualValidMoves.containsAll(expectedValidMoves);

		// Test case 2: should stop if come across position that is occupied by same color piece
		Piece wBishop2 = Piece.createPiece("wb");
		b.addPiece(wBishop2, "f2");
		assert b.getPiece("f2") == wBishop2;

		actualValidMoves = wBishop.moves(b, "d4");  // Recall moves bc have made changes to board
		assert !actualValidMoves.contains("f2");
		assert !actualValidMoves.contains("g1");

		// Test case 3: if come across opponent piece then capture it
		Piece bBishop = Piece.createPiece("bb");
		b.addPiece(bBishop, "g7");
		assert b.getPiece("g7") == bBishop;

		actualValidMoves = wBishop.moves(b, "d4");
		assert actualValidMoves.contains("g7");
	}

	// Test Rook moves
	public static void test7() {
		// Test case 1: Test on an empty board to make sure that are able to make all movements
		// Also make sure that all constraints on valid pieces of board are implemented correctly
		Board b = Board.theBoard();
		b.clear();

		// Add white Queen in middle of board
		Piece.registerPiece(new RookFactory());
		Piece wRook = Piece.createPiece("wr");
		b.addPiece(wRook, "d4");

		// Make list of all the other spots on board where Rook should be able to move
		// When move in line L: should be able to reach a4, b4, c4
		// R valid movements: e4, f4, g4, h4
		// up valid movements: d5, d6, d7, d8
		// down valid movements: d3, d2, d1
		List<String> expectedValidMoves = Arrays.asList(
				"a4", "b4", "c4",
				"e4", "f4", "g4", "h4",
				"d5", "d6", "d7", "d8",
				"d3", "d2", "d1"
		);

		// Get out the actual moves from the Rook piece
		List<String> actualValidMoves = wRook.moves(b, "d4");
		assert b.getPiece("d4") == wRook;

		// Check that the actually valid moves are the exact same as the expected valid moves
		assert actualValidMoves.containsAll(expectedValidMoves);

		// AND make sure that are not able to reach row 9 or row 0!!!
		assert !actualValidMoves.contains("d9");
		assert !actualValidMoves.contains("d0");

		// Test case 2: should stop if come across position that is occupied by same color piece
		Piece wRook2 = Piece.createPiece("wr");
		b.addPiece(wRook2, "f4");
		assert b.getPiece("f4") == wRook2;

		actualValidMoves = wRook.moves(b, "d4");  // Recall moves bc have made changes to board
		assert !actualValidMoves.contains("f4");
		assert !actualValidMoves.contains("g4");
		assert !actualValidMoves.contains("h4");

		// Test case 3: if come across opponent piece then capture it
		Piece bRook = Piece.createPiece("br");
		b.addPiece(bRook, "d2");
		assert b.getPiece("d2") == bRook;

		actualValidMoves = wRook.moves(b, "d4");
		assert actualValidMoves.contains("d2");
	}

	public static void main(String[] args) {
//		test1();
//		test2();
//		test3();
//		test4();
//		test5();
//		test6();
//		test7();
	}
}