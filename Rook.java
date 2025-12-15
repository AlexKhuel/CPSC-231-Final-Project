/*
CPSC-231 Final Project
Chess Board
By: Jake Puebla, Joshua Dowd, Nate Smith, Jerry Salas

Date Completed: December 14, 2025
 */

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
     * @param row - the piece's row position.
     * @param col - the piece's column position.
     * @param isWhite - the piece's color (true for white, false for black).
     */
    public Rook(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    /**
     * Creates a deep copy of this Rook piece.
     *
     * @return a new Rook object with the same attributes as this rook
     */
    @Override
    public Piece copy() {
        return new Rook(this.row, this.col, this.isWhite);
    }

    /**
     * Overriden function. Determines whether a move is valid.
     * 
     * The Rook can move any number of squares horizontally or vertically,
     * but cannot jump over other pieces. The path must be clear and the
     * move must not result in the player's king being in check.
     *
     * @param gameBoard - the Board object where the game is taking place.
     * @param endRow - the row of the move location that is being checked.
     * @param endCol - the column of the move location that is being checked.
     * @return true if the move is legal, false otherwise
     */
    @Override
    public boolean canMove(Board gameBoard, int endRow, int endCol) {

        if (endRow == row && endCol == col) {
            return false;
        }

        if (endCol < 0 || endRow < 0 || endCol > 7 || endRow > 7) {
            return false;
        }

        if (!(endCol == col || endRow == row)) {
            return false;
        }

        boolean isVertical = endRow != row;

        int dist;
        int direction;

        if (isVertical) {
            dist = Math.abs(endRow - row);

            direction = endRow < row ? -1 : 1;

        } else {
            dist = Math.abs(endCol - col);

            direction = endCol < col ? -1 : 1;
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

        if (gameBoard.board[endRow][endCol] == null || gameBoard.board[endRow][endCol].isWhite != isWhite) {
            King currKing = gameBoard.getKing(this.isWhite);
            Board fakeBoard = new Board(gameBoard);
            fakeBoard.uncheckedMove(this, row, col, endRow, endCol);
            return !(currKing.isInCheck(fakeBoard));
        } else {
            return false;
        }

    }

    /**
     * Gets the current row position of this rook.
     *
     * @return the row position (0-7)
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Gets the current column position of this rook.
     *
     * @return the column position (0-7)
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * Determines the color of this rook.
     *
     * @return true if this rook is white, false if black
     */
    @Override
    public boolean isWhite() {
        return isWhite;
    }

}