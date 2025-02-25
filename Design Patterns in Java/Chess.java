import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Chess {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java Chess layout moves");
		}
		Piece.registerPiece(new KingFactory());
		Piece.registerPiece(new QueenFactory());
		Piece.registerPiece(new KnightFactory());
		Piece.registerPiece(new BishopFactory());
		Piece.registerPiece(new RookFactory());
		Piece.registerPiece(new PawnFactory());
		Board.theBoard().registerListener(new Logger());
		// args[0] is the layout file name
		// args[1] is the moves file name
		// Put your code to read the layout file and moves files
		// here.

		// Get out layout, moves files from input arguments
		File layout = new File(args[0]);
		File moves = new File(args[1]);

		// Read in layout file
		try {
			// Wrap in BufferedReader for efficient reading
			BufferedReader layoutReader = new BufferedReader(new FileReader(layout));
			String layoutLine;

			// Valid input form for each line in layout file is xn=cp
			Pattern validInputForm = Pattern.compile("([a-h][1-8])=([bw])[kqnbrp]");

			// Now loop through line by line and read file in, populating board as do so
			while((layoutLine = layoutReader.readLine()) != null) {
				// Want ignore empty lines and comments
				if (!layoutLine.startsWith("#") && !layoutLine.isEmpty()) {
					// Now check if line is valid input form
					Matcher isLineValid = validInputForm.matcher(layoutLine);

					// Line of invalid input = violates given rules so throw exception
					if (!isLineValid.matches()) {
						throw new IllegalArgumentException("Line is of invalid format.");
					}

					// Get wanted info out of line for initializing the board
					// At this point, know line is of valid format so know the location is the first 2 characters
					// and the position is the last 2
					String position = layoutLine.substring(0, 2);
					String pieceName = layoutLine.substring(3, 5);

					// Check if the position on board is already occupied before add piece to board
					if (Board.theBoard().getPiece(position) != null) {
						throw new IllegalStateException("Trying to add two pieces to same location on board.");
					}

					// Else, create the piece and add it to the board!
					Piece piece = Piece.createPiece(pieceName);
					Board.theBoard().addPiece(piece, position);
				}
			}
		} catch(Exception e) {
			System.out.println(e.toString());
		}

		// Read in moves file
		try {
			// Wrap in BufferedReader for efficient reading
			BufferedReader movesReader = new BufferedReader(new FileReader(moves));
			String movesLine;

			// Valid input form for each line in layout file is xn-ym
			Pattern validMovesForm = Pattern.compile("([a-h][1-8])\\-([a-h][1-8])");

			// Now loop through line by line and read file in, make each move on board that read in from file
			while((movesLine = movesReader.readLine()) != null) {
				// Want ignore empty lines and comments
				if (!movesLine.startsWith("#") && !movesLine.isEmpty()) {
					// Now check if line is valid input form
					Matcher isLineValid = validMovesForm.matcher(movesLine);

					// Line of invalid input = violates given rules so throw exception
					if (!isLineValid.matches()) {
						throw new IllegalArgumentException("Line is of invalid format.");
					}

					// Now make move on board by calling movePiece method from Board
					// movePiece method checks whether move is valid or not
					String from = movesLine.substring(0, 2);
					String to = movesLine.substring(3, 5);
					Board.theBoard().movePiece(from, to);
				}
			}
		} catch(Exception e) {
			System.out.println(e.toString());
		}

		// Leave the following code at the end of the simulation:
		System.out.println("Final board:");
		Board.theBoard().iterate(new BoardPrinter());
	}
}