import java.util.*;

public class Bishop extends Piece {
    // Implemented color() method in Piece, rather than in subclasses
    public Bishop(Color color) {
        super(color);
    }
    // implement appropriate methods

    // Each piece's toString method = return piece name in form it appears in layout file
    public String toString() {
        // The name is the color followed by the character representing its piece
        // Character representing Pawn is p
        return (this.color() == Color.WHITE ? "w": "b") + "b";
    }

    public List<String> moves(Board b, String loc) {
        // Keep track of all the valid moves the Bishop piece can make
        List<String> validMoves = new ArrayList<>();

        // Get the current col + row of the Bishop piece out
        char fromCol = loc.charAt(0);
        int fromRow  = loc.charAt(1) - '0';

        // Bishop moves like Queen that can only move diagonally so has 4 different possible diagonal moves

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
