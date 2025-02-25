import java.util.*;
public class Pawn extends Piece {
    // Implemented color() method in Piece, rather than in subclasses
    public Pawn(Color color) {
        super(color);
    }
    // implement appropriate methods

    // Each piece's toString method = return piece name in form it appears in layout file
    public String toString() {
        // The name is the color followed by the character representing its piece
        // Character representing Pawn is p
        return (this.color() == Color.WHITE ? "w": "b") + "p";
    }

    public List<String> moves(Board b, String loc) {
        // Keep track of all the valid moves the Pawn piece can make
        List<String> validMoves = new ArrayList<>();

        // Get the current col + row of the Pawn piece out
        char fromCol = loc.charAt(0);
        int fromRow  = loc.charAt(1) - '0';

        // Figure out what direction the pawn should move since black + white move in opposite directions
        // White pawns move up the board from row 1 to row 8
        // Black pawns move down the board from row 8 to row 1
        int direction = (this.color() == Color.WHITE) ? 1 : -1;

        // Set the home row for white + black pawns
        // White pawns start in row 2
        // Black pawns start in row 7
        int homeRow = (this.color() == Color.WHITE) ? 2 : 7;

        // Movement case 1: pawn can move 1 space vertically forward, only toward opponent's side of board
        String oneStep = "" + fromCol + (fromRow + direction);

        // This move can only be made to vacant spaces so check that it's empty before add it to validMoves
        if (b.getPiece(oneStep) == null) {
            validMoves.add(oneStep);
        }

        // Movement case 2: pawn can move 2 spaces forward if it's in its home row
        if (fromRow == homeRow) {
            // Can only make 2 steps forward if the 1 step forward is vacant
            if (b.getPiece(oneStep) == null) {
                String twoSteps = "" + fromCol + (fromRow + 2 * direction);

                // And can only make 2 steps forward if the second step itself is vacant too
                if (b.getPiece(twoSteps) == null) {
                    validMoves.add(twoSteps);
                }
            }
        }

        // Movement case 3: pawn can capture an opponent's piece by moving one square diagonally toward the opponent
        // Figure out col + row with diagonal move: for both L + R diagonal moves:
        // move one row up or down depending on direction for pawn color + move L or R too
        int diagonalRow = fromRow + direction;
        char colToLeft = (char) (fromCol - 1);
        char colToRight = (char) (fromCol + 1);

        // Capture L + R diagonal moves
        // Make sure that the row and columns for diagonal direction are actually on board before attempt to make diagonal move to them
        if (diagonalRow >= 1 && diagonalRow <= 8) {
            // Capture opponent's piece diagonally to left of current position
            if (colToLeft >= 'a' && colToLeft <= 'h') {
                String diagonalLeft = colToLeft + Integer.toString(diagonalRow);

                if (b.getPiece(diagonalLeft) != null && b.getPiece(diagonalLeft).color() != this.color()) {
                    validMoves.add(diagonalLeft);
                }
            }

            // Same thing for moving diagonally right and capturing opponent's piece
            if (colToRight >= 'a' && colToRight <= 'h') {
                String diagonalRight = colToRight + Integer.toString(diagonalRow);

                if (b.getPiece(diagonalRight) != null && b.getPiece(diagonalRight).color() != this.color()) {
                    validMoves.add(diagonalRight);
                }
            }
        }

        return validMoves;
    }
}