import java.util.*;

public class Knight extends Piece {
    // Implemented color() method in Piece, rather than in subclasses
    public Knight(Color color) {
        super(color);
    }
    // implement appropriate methods

    // Each piece's toString method = return piece name in form it appears in layout file
    public String toString() {
        // The name is the color followed by the character representing its piece
        // Character representing Pawn is p
        return (this.color() == Color.WHITE ? "w": "b") + "n";
    }

    public List<String> moves(Board b, String loc) {
        // Keep track of all the valid moves the Knight piece can make
        List<String> validMoves = new ArrayList<>();

        // Get the current col + row of the Knight piece out
        char fromCol = loc.charAt(0);
        int fromRow  = loc.charAt(1) - '0';

        // Knight has 8 different possible moves:
        // (1) 2 R, 1 down (2) 2 R, 1 up
        // (3) 2 L, 1 down (4) 2 L, 1 up
        // (5) 2 up, 1 R (6) 2 up, 1 L
        // (7) 2 down, 1 L (8) 2 down, 1 R

        // 2 R, 1 down case so move 2 to right in col and move down 1 in row
        // Need make sure col + row are still valid when make the move
        // Moving R so have make sure col doesn't go past most R col h
        // Moving down so need make sure row doesn't go past row 1 (very most bottom row)
        if (fromCol + 2 <= 'h' && fromRow - 1 >= 1) {
            String rightDownPos = "" + (char) (fromCol + 2) + (fromRow - 1);

            if (isValidMove(b, rightDownPos)) {
                validMoves.add(rightDownPos);
            }
        }

        // 2 R, 1 up case
        // Moving R so have make sure col doesn't go past most R col h
        // Moving up so need make sure row doesn't go past row 8 (very most top row)
        if (fromCol + 2 <= 'h' && fromRow + 1 <= 8) {
            String rightUpPos = "" + (char) (fromCol + 2) + (fromRow + 1);

            if (isValidMove(b, rightUpPos)) {
                validMoves.add(rightUpPos);
            }
        }

        // 2 L, 1 down case
        // Moving L so have make sure col doesn't go past most L col a
        // Moving down so need make sure row doesn't go past row 1 (very most bottom row)
        if (fromCol - 2 >= 'a' && fromRow - 1 >= 1) {
            String leftDownPos = "" + (char) (fromCol - 2) + (fromRow - 1);

            if (isValidMove(b, leftDownPos)) {
                validMoves.add(leftDownPos);
            }
        }

        // 2 L, 1 up case
        // Moving L so have make sure col doesn't go past most L col a
        // Moving up so need make sure row doesn't go past row 8 (very most top row)
        if (fromCol - 2 >= 'a' && fromRow + 1 <= 8) {
            String leftUpPos = "" + (char) (fromCol - 2) + (fromRow + 1);

            if (isValidMove(b, leftUpPos)) {
                validMoves.add(leftUpPos);
            }
        }

        // 2 up, 1 R case
        // Moving up so have make sure row doesn't go past row 8 (very most top row)
        // Moving R so have make sure col doesn't go past col h (very most R col)
        if (fromRow + 2 <= 8 && fromCol + 1 <= 'h') {
            String upRightPos = "" + (char) (fromCol + 1) + (fromRow + 2);

            if (isValidMove(b, upRightPos)) {
                validMoves.add(upRightPos);
            }
        }

        // 2 up, 1 L case
        // Moving up so have make sure row doesn't go past row 8 (very most top row)
        // Moving L so have make sure col doesn't go past col a (very most L col)
        if (fromRow + 2 <= 8 && fromCol - 1 >= 'a') {
            String upLeftPos = "" + (char) (fromCol - 1) + (fromRow + 2);

            if (isValidMove(b, upLeftPos)) {
                validMoves.add(upLeftPos);
            }
        }

        // 2 down, 1 L case
        // Moving down so have make sure row doesn't go past row 1 (very most bottom row)
        // Moving L so have make sure col doesn't go past col a (very most L col)
        if (fromRow - 2 >= 1 && fromCol - 1 >= 'a') {
            String downLeftPos = "" + (char) (fromCol - 1) + (fromRow - 2);

            if (isValidMove(b, downLeftPos)) {
                validMoves.add(downLeftPos);
            }
        }

        // 2 down, 1 R case
        // Moving down so have make sure row doesn't go past row 1 (very most bottom row)
        // Moving R so have make sure col doesn't go past col h (very most R col)
        if (fromRow - 2 >= 1 && fromCol + 1 <= 'h') {
            String downRightPos = "" + (char) (fromCol + 1) + (fromRow - 2);

            if (isValidMove(b, downRightPos)) {
                validMoves.add(downRightPos);
            }
        }

        return validMoves;
    }
}