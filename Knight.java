/*
CPSC-231 Final Project
Chess Board
By: Jake Puebla, Joshua Dowd, Nate Smith, Jerry Salas

Date Completed: December 14, 2025
*/



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
     * @param row - the piece's row position.
     * @param col - the piece's column position.
     * @param isWhite - the piece's color (true for white, false for black).
     */
    public Knight(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    /**
     * Creates a deep copy of this Knight piece.
     *
     * @return a new Knight object with the same attributes as this knight
     */
    @Override
    public Piece copy() {
        return new Knight(this.row, this.col, this.isWhite);
    }

    /**
     * Overriden function. Determines whether a move is valid.
     * 
     * The Knight moves in an L-shape: either 2 squares in one direction and
     * 1 square perpendicular, or 1 square in one direction and 2 squares
     * perpendicular. The Knight can jump over other pieces. The move must
     * not result in the player's king being in check.
     *
     * @param gameBoard - the Board object where the game is taking place.
     * @param endRow - the row of the move location that is being checked.
     * @param endCol - the column of the move location that is being checked.
     * @return true if the move is legal, false otherwise
     */
    @Override
    public boolean canMove(Board gameBoard, int endRow, int endCol) {

        if ((endRow == row) && (endCol == col)) {
            return false;
        } else if (endCol < 0 || endRow < 0 || endCol > 7 || endRow > 7) {
            return false;
        }

        switch (Math.abs(endRow - row)) {
            case 1 -> {
                if (Math.abs(endCol - col) == 2) {
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
            case 2 -> {
                if (Math.abs(endCol - col) == 1) {
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
            default -> {
                return false;
            }
        }

    }

    /**
     * Gets the current row position of this knight.
     *
     * @return the row position (0-7)
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Gets the current column position of this knight.
     *
     * @return the column position (0-7)
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * Determines the color of this knight.
     *
     * @return true if this knight is white, false if black
     */
    @Override
    public boolean isWhite() {
        return isWhite;
    }

}