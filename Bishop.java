
/**
 * This class is used to represent a Bishop piece in a game of chess.
 *
 * @author Jake Puebla
 * @version 1.0
 * @see Piece.java
 * @see Board.java
 */
class Bishop extends Piece {

    /**
     * Constructor for the Bishop object.
     *
     * @param r - the piece's row position.
     * @param c - the piece's column position.
     * @param color - the piece's color (black or white).
     */
    public Bishop(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    @Override
    public Piece copy() {
        return new Bishop(this.row, this.col, this.isWhite);
    }

    /**
     * Overriden function. Determines whether a move is valid.
     *
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

        if (Math.abs(endRow - row) == Math.abs(endCol - col)) {
            int dist = Math.abs(endRow - row);
            int rowDirection;
            int colDirection;
            if (endRow < row) {
                rowDirection = -1;
            } else {
                rowDirection = 1;
            }
            if (endCol < col) {
                colDirection = -1;
            } else {
                colDirection = 1;
            }

            for (int i = 1; i < dist; i++) {
                if (!(gameBoard.board[row + (rowDirection * i)][col + (colDirection * i)] == null)) {
                    return false;
                }
            }

            if (!(gameBoard.board[endRow][endCol].isWhite == isWhite)) {
                return !(gameBoard.getKing(this.isWhite).isInCheck(gameBoard, endRow, endCol));
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

}
