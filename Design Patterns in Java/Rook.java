import java.util.*;

public class Rook extends Piece {
    // Implemented color() method in Piece, rather than in subclasses
    public Rook(Color color) {
        super(color);
    }
    // implement appropriate methods

    // Each piece's toString method = return piece name in form it appears in layout file
    public String toString() {
        // The name is the color followed by the character representing its piece
        // Character representing Pawn is p
        return (this.color() == Color.WHITE ? "w": "b") + "r";
    }

    public List<String> moves(Board b, String loc) {
        // Keep track of all the valid moves the Rook piece can make
        List<String> validMoves = new ArrayList<>();

        // Get the current col + row of the Rook piece out
        char fromCol = loc.charAt(0);
        int fromRow  = loc.charAt(1) - '0';

        // Just like Queen, but can only move horizontally or vertically
        // So has 4 different possible moves it can make

        // Implement moving L, R first
        // Moving L: change col, row stays same
        // Make sure don't go past col a = last col on L
        for (char col = (char) (fromCol - 1); col >= 'a'; col--) {
            String moveLeftPos = "" + col + fromRow;

            if (keepMovingInDirection(b, validMoves, moveLeftPos)) {
                break;
            }
        }

        // Moving R: change col, row stays same
        // Make sure don't go past col h = last col on R
        for (char col = (char) (fromCol + 1); col <= 'h'; col++) {
            String moveRightPos = "" + col + fromRow;

            if (keepMovingInDirection(b, validMoves, moveRightPos)) {
                break;
            }
        }

        // Implement moving up, down
        // Moving up: row changes, col stays same
        // Make sure don't go past top row = row 8
        for (int row = fromRow + 1; row <= 8; row++) {
            String moveUpPos = "" + fromCol + row;

            if (keepMovingInDirection(b, validMoves, moveUpPos)) {
                break;
            }
        }

        // Moving down: row changes, col stays same
        // Make sure don't go past bottom row = row 1
        for (int row = fromRow - 1; row >= 1; row--) {
            String moveDownPos = "" + fromCol + row;

            if (keepMovingInDirection(b, validMoves, moveDownPos)) {
                break;
            }
        }

        return validMoves;
    }
}