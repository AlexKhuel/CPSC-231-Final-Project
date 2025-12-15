
/**
 * This class is used to represent a Knight piece in a game of chess.
 *
 * @author Jake Puebla
 * @version 1.0
 * @see Piece.java
 * @see Board.java
 */
class Knight extends Piece {

    /**
     * Constructor for the Knight object.
     *
     * @param r - the piece's row position.
     * @param c - the piece's column position.
     * @param color - the piece's color (black or white).
     */
    public Knight(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    @Override
    public Piece copy() {
        return new Knight(this.row, this.col, this.isWhite);
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

        switch (Math.abs(endRow - row)) {
            case 1 -> {
                if (Math.abs(endCol - col) == 2) {
                    if (gameBoard.board[endRow][endCol] == null || gameBoard.board[endRow][endCol].isWhite != isWhite) {
                        return !(gameBoard.getKing(isWhite).isInCheck(gameBoard, endRow, endCol));
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            case 2 -> {
                if (Math.abs(endCol - col) == 1) {
                    if (gameBoard.board[endRow][endCol] == null || gameBoard.board[endRow][endCol].isWhite != isWhite) {
                        return !(gameBoard.getKing(isWhite).isInCheck(gameBoard, endRow, endCol));
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }
            }
            default -> {
                return false;
            }
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

    @Override
    public boolean isWhite() {
        return isWhite;
    }

}
