
/**
 * This class is used to represent a Rook piece in a game of chess.
 *
 * @author Jake Puebla
 * @version 1.0
 * @see Piece.java
 * @see Board.java
 */
class Rook extends Piece {

    /**
     * Constructor for the Rook object.
     *
     * @param r - the piece's row position.
     * @param c - the piece's column position.
     * @param color - the piece's color (black or white).
     */
    public Rook(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    @Override
    public Piece copy() {
        return new Rook(this.row, this.col, this.isWhite);
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

        if (endCol == col || endRow == row) {
            boolean isVertical = true;
            if (endRow == row) {
                isVertical = false;
            }

            int dist;
            int direction;
            if (isVertical) {
                dist = Math.abs(endRow - row);
                if (endRow < row) {
                    direction = -1;
                } else {
                    direction = 1;
                }
            } else {
                dist = Math.abs(endCol - col);
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

            if (gameBoard.board[endRow][endCol] == null || !(gameBoard.board[endRow][endCol].isWhite == isWhite)) {
                return !(gameBoard.getKing(isWhite).isInCheck(gameBoard, endRow, endCol));
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
