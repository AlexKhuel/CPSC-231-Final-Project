/**
 * This class is used to represent a Rook piece in a game of chess.
 * @author Jake Puebla
 * @version 1.0
 * @see Piece.java
 * @see Board.java
 */
class Rook extends Piece {

    static const int type = 4;

    /**
     * Constructor for the Rook object.
     * @param r - the piece's row position.
     * @param c - the piece's column position.
     * @param color - the piece's color (black or white).
     */
    public Rook(int r, int c, boolean color) {
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

        if (endCol == col || endRow == row) {
            boolean isVertical = true;
            if (endRow == row) {
                isVertical = false;
            }

            int dist;
            int direction;
            if (isVertical) {
                if (endRow < row) {
                    direction = -1;
                } else {
                    direction = 1;
                }
            } else {
                if (endCol < col) {
                    direction = -1;
                } else {
                    direction = 1;
                }
            }

            for (int i = 1; i < dist; i++) {
                if (isVertical) {
                    if (!(gameBoard.board[row + (direction * i)][col] == null)) {
                        return false;
                    }
                } else {
                    if (!(gameBoard.board[row][col + (direction * i)] == null)) {
                        return false;
                    }
                }
            }

            if (!(gameBoard.board[endRow][endCol].isWhite == isWhite)) {
                return !(isInCheck(gameBoard.board, row, col, endRow, endCol));
            } else {
                return false;
            }

        } else {
            return false;
        }


    }

}