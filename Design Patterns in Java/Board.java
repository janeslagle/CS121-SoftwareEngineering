import java.util.*;

public class Board {

//    private Piece[][] pieces = new Piece[8][8];

    private static Board instance; // Singleton instance
    private final List<BoardListener> listeners;  // List of registered listeners

    private final Map<String, Piece> pieces;

    private Board() {
        listeners = new ArrayList<>();
        pieces = new HashMap<>();
    }

    // Get the Singleton instance
    public static Board theBoard() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    // Figure out if location is valid or not
    private boolean isValidLoc(String loc) {
        // Valid location is any character in a-h followed by a number in a valid row 1-8
        return loc != null && loc.matches("[a-h][1-8]");
    }

    // Returns piece at given loc or null if no such piece
    // exists
    public Piece getPiece(String loc) {
        // If location is invalid then raise exception
        if (!isValidLoc(loc)) {
            throw new IllegalArgumentException("Location of piece is invalid: " + loc);
        }

        return pieces.get(loc);
    }

    // Adds piece p to board at given location
    // Raise exception if location is already occupied or invalid
    // In Chess: raise exception if piece already at location before call addPiece so only have worry about
    // exception if location is invalid here
    public void addPiece(Piece p, String loc) {
        if (!isValidLoc(loc)) {
            throw new IllegalArgumentException("Location of piece adding is invalid: " + loc);
        }

        pieces.put(loc, p);
    }

    // Moves piece at location from to location to
    public void movePiece(String from, String to) {
        // Validate from, to positions
        if (!isValidLoc(from) || !isValidLoc(to)) {
            throw new IllegalArgumentException("Invalid position at: " + from + " or at: " + to);
        }

        // Get the place at the from position
        Piece movingPiece = pieces.get(from);

        // Check that there is a piece at from, if not, throw exception
        if (movingPiece == null) {
            throw new IllegalArgumentException("No piece at position: " + from);
        }

        // Check if to position is occupied
        if (pieces.containsKey(to)) {
            Piece targetPiece = pieces.get(to);

            // Ensure target is an opponent's (opposite color of it) piece
            // If not, then this is invalid move so throw exception
            if (targetPiece.color() == movingPiece.color()) {
                throw new IllegalArgumentException("Invalid move: cannot make this move since you are trying to move to a position occipied by your own piece.");
            }

            // If piece at to then means piece captured: so notify listeners of capturing move
            notifyCapture(movingPiece, targetPiece);
        }

        // Now actually perform the move by placing the moving piece at the new to position
        // Need to make the location from be vacant so remove the piece from the from position
        pieces.put(to, movingPiece);
        pieces.remove(from);

        // Need to notify the listeners of the move too (= 2nd part of making a capturing move)
        notifyMove(from, to, movingPiece);
    }

    // Notify listeners of piece capture: if piece captured need to first make call to onMove
    // and then need to call onCapture
    private void notifyCapture(Piece attacker, Piece captured) {
        for (BoardListener listener: listeners) {
            listener.onCapture(attacker, captured);
        }
    }

    // Notify listeners of move capture
    private void notifyMove(String from, String to, Piece piece) {
        for (BoardListener listener: listeners) {
            listener.onMove(from, to, piece);
        }
    }

    // Removes all pieces from board
    public void clear() {
        pieces.clear();
    }

    // Adds listener that should subsequently be called at appropriate times
    // Need to modify movePiece
    // There can be any number of listeners at any time (zero or more)
    // We will not test the case of the same listener being registered more than once
    public void registerListener(BoardListener bl) {
        if (!listeners.contains(bl)) {
            listeners.add(bl);
        }
    }

    // Removes listener so it will subsequently not be notified of events
    public void removeListener(BoardListener bl) {
        if (listeners.contains(bl)) {
            listeners.remove(bl);
        }
    }

    // Removes all listeners
    public void removeAllListeners() {
        listeners.clear();
    }

    // Takes BoardInteralIterator + for every square on the board:
    // calls visit method: pass square location currently at + piece at that location
    // or null if the location vacant
    public void iterate(BoardInternalIterator bi) {
        // Iterate through all positions + pieces on board so from position a1 to position h8
        // Need loop over all columns a through h and all rows 1 through 8
        for (char col = 'a'; col <= 'h'; col++) {
            for (char row = '1'; row <= '8'; row++) {
                String loc = String.valueOf(col) + row;
                Piece p = pieces.get(loc);
                bi.visit(loc, p);
            }
        }
    }
}
