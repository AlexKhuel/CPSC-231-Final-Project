/*
CPSC-231 Final Project
Chess Board
By: Jake Puebla, Joshua Dowd, Nate Smith, Jerry Salas

Date Completed: December 14, 2025
 */

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
     * @param row - the piece's row position.
     * @param col - the piece's column position.
     * @param isWhite - the piece's color (true for white, false for black).
     */
    public Bishop(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    /**
     * Creates a deep copy of this Bishop piece.
     *
     * @return a new Bishop object with the same attributes as this bishop
     */
    @Override
    public Piece copy() {
        return new Bishop(this.row, this.col, this.isWhite);
    }

    /**
     * Overriden function. Determines whether a move is valid.
     *
     * The Bishop can move any number of squares diagonally, but cannot jump
     * over other pieces. The path must be clear and the move must not result in
     * the player's king being in check.
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

            if (gameBoard.board[endRow][endCol] == null || gameBoard.board[endRow][endCol].isWhite != isWhite) {
                King currKing = gameBoard.getKing(this.isWhite);
                Board fakeBoard = new Board(gameBoard);
                fakeBoard.uncheckedMove(this, row, col, endRow, endCol);
                return !(currKing.isInCheck(fakeBoard));
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * Gets the current row position of this bishop.
     *
     * @return the row position (0-7)
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Gets the current column position of this bishop.
     *
     * @return the column position (0-7)
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * Determines the color of this bishop.
     *
     * @return true if this bishop is white, false if black
     */
    @Override
    public boolean isWhite() {
        return isWhite;
    }

}
