/*
CPSC-231 Final Project
Chess Board
By: Jake Puebla, Joshua Dowd, Nate Smith, Jerry Salas

Date Completed: December 14, 2025
 */

/**
 * This class is used to represent a Queen piece in a game of chess.
 *
 * @author Jake Puebla
 * @version 1.0
 * @see Piece.java
 * @see Board.java
 */
class Queen extends Piece {

    /**
     * Constructor for the Queen object.
     *
     * @param r - the piece's row position.
     * @param c - the piece's column position.
     * @param color - the piece's color (black or white).
     */
    public Queen(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    @Override
    public Piece copy() {
        return new Queen(this.row, this.col, this.isWhite);
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

        Bishop tempBishop = new Bishop(this.row, this.col, this.isWhite);

        Rook tempRook = new Rook(this.row, this.col, this.isWhite);

        return tempBishop.canMove(gameBoard, endRow, endCol) || tempRook.canMove(gameBoard, endRow, endCol);

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
