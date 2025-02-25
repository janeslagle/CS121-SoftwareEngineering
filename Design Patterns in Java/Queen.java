import java.util.*;

public class Queen extends Piece {
    // Implemented color() method in Piece, rather than in subclasses
    public Queen(Color color) {
        super(color);
    }
    // implement appropriate methods

    // Each piece's toString method = return piece name in form it appears in layout file
    public String toString() {
        // The name is the color followed by the character representing its piece
        // Character representing Pawn is p
        return (this.color() == Color.WHITE ? "w": "b") + "q";
    }

    public List<String> moves(Board b, String loc) {
        // Keep track of all the valid moves the Queen piece can make
        List<String> validMoves = new ArrayList<>();

        // Get the current col + row of the Queen piece out
        char fromCol = loc.charAt(0);
        int fromRow  = loc.charAt(1) - '0';

        // Queen can move any number vacant spots in any direction: L/R, up/down, diagonal any direction

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

        // Implement moving diagonally
        // Move up and to the left
        // Means can't go past col a (most left col) and can't go past row 8 (top row)
        for (int i = 1; fromCol - i >= 'a' && fromRow + i <= 8; i++) {
            char moveLeftCol = (char) (fromCol - i);
            int moveUpRow = fromRow + i;
            String diagonallyLeftPos = "" + moveLeftCol + moveUpRow;

            if (keepMovingInDirection(b, validMoves, diagonallyLeftPos)) {
                break;
            }
        }

        // Move up and to the right
        // Means can't go past col h (most right col) and can't go past row 8 (top row)
        for (int i = 1; fromCol + i <= 'h' && fromRow + i <= 8; i++) {
            char moveRightCol = (char) (fromCol + i);
            int moveUpRow = fromRow + i;
            String diagonallyRightPos = "" + moveRightCol + moveUpRow;

            if (keepMovingInDirection(b, validMoves, diagonallyRightPos)) {
                break;
            }
        }

        // Move down and to the right
        // Means can't go past col h (most right col) and can't go past row 1 (bottom row)
        for (int i = 1; fromCol + i <= 'h' && fromRow - i >= 1; i++) {
            char moveRightCol = (char) (fromCol + i);
            int moveDownRow = fromRow - i;

            String diagonallyDownRightPos = "" + moveRightCol + moveDownRow;

            if (keepMovingInDirection(b, validMoves, diagonallyDownRightPos)) {
                break;
            }
        }

        // Move down and to the left
        // Means can't go past col a (most left col) and can't go past row 1 (bottom row)
        for (int i = 1; fromCol - i >= 'a' && fromRow - i >= 1; i++) {
            char moveLeftCol = (char) (fromCol - i);
            int moveDownRow = fromRow - i;

            String diagonallyDownLeftPos = "" + moveLeftCol + moveDownRow;

            if (keepMovingInDirection(b, validMoves, diagonallyDownLeftPos)) {
                break;
            }
        }

        return validMoves;
    }
}