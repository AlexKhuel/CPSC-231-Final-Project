/**
 * This class is used to represent a Knight piece in a game of chess.
 * @author Jake Puebla
 * @version 1.0
 * @see Piece.java
 * @see Board.java
 */
class Knight extends Piece {

    /**
     * Constructor for the Knight object.
     * @param r - the piece's row position.
     * @param c - the piece's column position.
     * @param color - the piece's color (black or white).
     */
    public Knight(int r, int c, boolean color) {
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

        if (Math.abs(endRow - row) == 1) {
            if (Math.abs(endCol - col) == 2) {
                if (!(gameBoard.board[endRow][endCol].isWhite == isWhite)) {
                    return !(isInCheck(gameBoard.board, endRow, endCol));
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } else if (Math.abs(endRow - row) == 2) {
            if (Math.abs(endCol - col) == 1) {
                if (!(gameBoard.board[endRow][endCol].isWhite == isWhite)) {
                    return !(isInCheck(gameBoard.board, endRow, endCol));
                } else {
                    return false;
                }

            } else {
                return false;
            }

        } else {
            return false;
        }

    }

}