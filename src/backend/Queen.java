/**
 * This class is used to represent a Queen piece in a game of chess.
 * @author Jake Puebla
 * @version 1.0
 * @see Piece.java
 * @see Board.java
 */
class Queen extends Piece {

    static const int type = 5;

    /**
     * Constructor for the Queen object.
     * @param r - the piece's row position.
     * @param c - the piece's column position.
     * @param color - the piece's color (black or white).
     */
    public Queen(int r, int c, boolean color) {
        row = r;
        col = c;
        isWhite = color;
    }

    /**
     * Overriden function. Determines whether a move is valid.
     * @param gameBoard - the Board object where the game is taking place.
     * @param endRow - the row of the move location that is being checked.
     * @param endCol - the column of the move location that is being checked.
     */
    @Override
    public boolean canMove(Board gameBoard, int endRow, int endCol) {
        
        if (endRow == row && endCol == col) {
            return false;
        } else if (endCol < 0 || endRow < 0 || endCol > 7 || endRow > 7) {
            return false;
        }

        return Bishop.canMove(gameBoard, endRow, endCol) || Rook.canMove(gameBoard, endRow, endCol);

    }

}