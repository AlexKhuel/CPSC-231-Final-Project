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
     * @param row - the piece's row position.
     * @param col - the piece's column position.
     * @param isWhite - the piece's color (true for white, false for black).
     */
    public Queen(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    /**
     * Creates a deep copy of this Queen piece.
     *
     * @return a new Queen object with the same attributes as this queen
     */
    @Override
    public Piece copy() {
        return new Queen(this.row, this.col, this.isWhite);
    }

    /**
     * Overriden function. Determines whether a move is valid.
     * 
     * The Queen can move any number of squares horizontally, vertically, or
     * diagonally (combining the movement patterns of a Rook and Bishop).
     * The move must not result in the player's king being in check.
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
        } else if (endCol < 0 || endRow < 0 || endCol > 7 || endRow > 7) {
            return false;
        }

        Bishop tempBishop = new Bishop(this.row, this.col, this.isWhite);

        Rook tempRook = new Rook(this.row, this.col, this.isWhite);

        return tempBishop.canMove(gameBoard, endRow, endCol) || tempRook.canMove(gameBoard, endRow, endCol);

    }

    /**
     * Gets the current row position of this queen.
     *
     * @return the row position (0-7)
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Gets the current column position of this queen.
     *
     * @return the column position (0-7)
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * Determines the color of this queen.
     *
     * @return true if this queen is white, false if black
     */
    @Override
    public boolean isWhite() {
        return isWhite;
    }

}