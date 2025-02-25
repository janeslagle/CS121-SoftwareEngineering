import java.util.*;

abstract public class Piece {
    private Color color;

    // Static map to hold symbol to factory mappings
    private static final Map<Character, PieceFactory> factoryMap = new HashMap<>();

    // Constructor
    protected Piece(Color color) {
        this.color = color;
    }

    // Register a PieceFactory with its symbol using static map have
    public static void registerPiece(PieceFactory pf) {
        factoryMap.put(pf.symbol(), pf);
    }

    // Create piece using factoryMap
    // Create instance of Piece described by its argument in same format as layout file
    // Look up symbol in map, call resulting constructor + return correct piece + color
    public static Piece createPiece(String name) {
        // Handle all necessary validation + errors
        if (name == null || name.length() != 2) {
            throw new IllegalArgumentException("Piece name given as invalid format: " + name);
        }

        // Get the color + piece character out from name
        // First character is color, second is the piece name
        char colorChar = name.charAt(0);
        char symbol = name.charAt(1);

        // Determine the color based on colorChar
        Color color;
        if (colorChar == 'w') {
            color = Color.WHITE;
        } else if (colorChar == 'b') {
            color = Color.BLACK;
        } else {
            throw new IllegalArgumentException("Piece given with invalid color: " + colorChar);
        }

        // Get the factory out from the map
        PieceFactory factory = factoryMap.get(symbol);

        if (factory == null) {
            throw new IllegalArgumentException("No factory was registered for the inputted symbol: " + symbol);
        }

        // Create the piece
        return factory.create(color);
    }

    // Method to return the color of the piece
    public Color color() {
	    // You should write code here and just inherit it in
	    // subclasses. For this to work, you should know
	    // that subclasses can access superclass fields.
        // Color passed to Piece's constructor so implement color() method here in Piece
        return color;
    }

    abstract public String toString();

    abstract public List<String> moves(Board b, String loc);

    // Helper function used by all Piece classes
    // Check if new position is a valid move can make: 2 cases when valid
    // (1) if new position is empty then can move there
    // (2) if new position has an opponent piece then can move there + capture the piece
    boolean isValidMove(Board b, String position) {
        return b.getPiece(position) == null || b.getPiece(position).color() != this.color();
    }

    // Helper function used in Queen, Bishop, Rook classes
    // Determine if keep moving in direction going or if break from loop and stop
    boolean keepMovingInDirection(Board b, List<String> validMoves, String position) {
        // Check that new position is a valid one
        if (isValidMove(b, position)) {
            validMoves.add(position);

            // If opponent piece is there: then capture it + exit loop to stop moving in direction going
            if (b.getPiece(position) != null) {
                return true;  // Use true to signal that should break from loop and stop moving in that direction
            }

            // Otherwise: means on empty space so return false to signify not to break from the loop and to keep moving in direction are going
            return false;
        }

        // returning true signifies to break from loop
        return true;  // If not valid position to move to, then need to stop moving in direction by exiting loop
    }
}