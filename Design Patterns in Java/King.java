import java.util.*;

public class King extends Piece {
    // Implemented color() method in Piece, rather than in subclasses
    public King(Color color) {
        super(color);
    }
    // implement appropriate methods

    // Each piece's toString method = return piece name in form it appears in layout file
    public String toString() {
        // The name is the color followed by the character representing its piece
        // Character representing Pawn is p
        return (this.color() == Color.WHITE ? "w": "b") + "k";
    }

    public List<String> moves(Board b, String loc) {
        // Keep track of all the valid moves the King piece can make
        List<String> validMoves = new ArrayList<>();

        // Get the current col + row of the King piece out
        char fromCol = loc.charAt(0);
        int fromRow  = loc.charAt(1) - '0';

        // King can make 8 different possible moves making one of these moves at a time:
        // move up and down + L and R: (1) vertical up, (2) vertical down, (3) horizontal L, (4) horizontal R
        // diagonal moves: (5) top L, (6) top R, (7) bottom L, (8) bottom R

        // Movement case 1: move up
        int movingRowUp = fromRow + 1;

        // Check if the new row is even a valid row on the board (is it between rows 1-8)
        // Moving up so <= 8
        if (movingRowUp <= 8) {
            String movingUpRowPos = "" + fromCol + movingRowUp;  // Update position w/ new row value, col stays same

            if (isValidMove(b, movingUpRowPos)) {
                validMoves.add(movingUpRowPos);
            }
        }

        // Movement case 2: move down
        int movingRowDown = fromRow - 1;

        // Check if the new row is even a valid row on the board (is it between rows 1-8)
        // Moving down so >= 1
        if (movingRowDown >= 1) {
            String movingDownRowPos = "" + fromCol + movingRowDown;  // Update position w/ new row value, col stays same

            if (isValidMove(b, movingDownRowPos)) {
                validMoves.add(movingDownRowPos);
            }
        }

        // Movement case 3: move L
        char movingColLeft = (char) (fromCol - 1);

        // Check if new col is even a valid col on the board (is it between cols a-h)
        // Moving down so >= a
        if (movingColLeft >= 'a') {
            String movingColLeftPos = "" + movingColLeft + fromRow;  // Update position w/ new col value, row stays same

            if (isValidMove(b, movingColLeftPos)) {
                validMoves.add(movingColLeftPos);
            }
        }

        // Movement case 4: move R
        char movingColRight = (char) (fromCol + 1);

        // Check if new col is even a valid col on the board (is it between cols a-h)
        // Moving up so <= h
        if (movingColRight <= 'h') {
            String movingColRightPos = "" + movingColRight + fromRow;  // Update position w/ new col value, row stays same

            if (isValidMove(b, movingColRightPos)) {
                validMoves.add(movingColRightPos);
            }
        }

        // Movement case 5: move diagonally up L - both row + col will change
        // col = moves L --> movingColLeft
        // row = moves up --> movingRowUp

        // Check if position are moving to is even a valid position
        if (movingColLeft >= 'a' && movingRowUp <= 8) {
            String movingDiagonallyUpL = "" + movingColLeft + movingRowUp; // Update pos: both col + row change

            if (isValidMove(b, movingDiagonallyUpL)) {
                validMoves.add(movingDiagonallyUpL);
            }
        }

        // Movement case 6: move diagonally up R
        // col = moves R --> movingColRight
        // row = moves up --> movingRowUp

        // Check if position are moving to is even a valid position
        if (movingColRight <= 'h' && movingRowUp <= 8) {
            String movingDiagonallyUpR = "" + movingColRight + movingRowUp; // Update pos: both col + row change

            if (isValidMove(b, movingDiagonallyUpR)) {
                validMoves.add(movingDiagonallyUpR);
            }
        }

        // Movement case 7: move diagonally down L
        // col = moves L --> movingColLeft
        // row = moves down --> movingRowDown

        // Check if position are moving to is even a valid position
        if (movingColLeft >= 'a' && movingRowDown >= 1) {
            String movingDiagonallyDownL = "" + movingColLeft + movingRowDown; // Update pos: both col + row change

            if (isValidMove(b, movingDiagonallyDownL)) {
                validMoves.add(movingDiagonallyDownL);
            }
        }

        // Movement case 8: move diagonally down R
        // col = moves R --> movingColRight
        // row = moves down --> movingRowDown

        // Check if position are moving to is even a valid position
        if (movingColRight <= 'h' && movingRowDown >= 1) {
            String movingDiagonallyDownR = "" + movingColRight + movingRowDown; // Update pos: both col + row change

            if (isValidMove(b, movingDiagonallyDownR)) {
                validMoves.add(movingDiagonallyDownR);
            }
        }

        return validMoves;
    }
}