/*
CPSC-231 Final Project
Chess Board
By: Jake Puebla, Joshua Dowd, Nate Smith, Jerry Salas

Date Completed: December 14, 2025
 */

/**
 * Represents a Pawn piece in a chess game.
 *
 * A Pawn can move forward one square, or two squares on its first move. It
 * captures diagonally and supports en passant moves.
 */
class Pawn extends Piece {

    /**
     * Tracks whether this pawn has moved from its starting position
     */
    public boolean hasMoved;

    /**
     * Constructs a new Pawn with the specified color and position.
     *
     * @param isWhite true if this pawn belongs to white, false if black
     * @param row the starting row position on the board (0-7)
     * @param col the starting column position on the board (0-7)
     * @param hasMoved true if the pawn has already moved, false otherwise
     */
    public Pawn(int row, int col, boolean isWhite, boolean hasMoved) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.hasMoved = hasMoved;
    }

    /**
     * Creates a deep copy of this Pawn piece.
     *
     * @return a new Pawn object with the same attributes as this pawn
     */
    @Override
    public Piece copy() {
        return new Pawn(this.row, this.col, this.isWhite, this.hasMoved);
    }

    /**
     * Determines if the pawn can legally move to the specified position.
     * Validates the move according to pawn movement rules (forward movement,
     * diagonal capture, en passant) and ensures the move does not leave the
     * player's king in check.
     *
     * @param gameBoard the current game board
     * @param endRow the target row position (0-7)
     * @param endCol the target column position (0-7)
     * @return true if the move is legal, false otherwise
     */
    @Override
    public boolean canMove(Board gameBoard, int endRow, int endCol) {
        int direction = isWhite ? -1 : 1;

        if (!isValid(gameBoard, endCol, endRow, direction)) {
            return false;
        }
        King currKing = gameBoard.getKing(this.isWhite);
        Board fakeBoard = new Board(gameBoard);
        fakeBoard.uncheckedMove(this, row, col, endRow, endCol);
        if (currKing.isInCheck(fakeBoard)) {
            return false;
        }

        hasMoved = true;
        return true;
    }

    /**
     * Validates whether the target position is a legal move for this pawn
     * according to chess rules. Checks for standard forward movement, initial
     * two-square move, diagonal capture, and en passant.
     *
     * @param gameBoard the current game board
     * @param endCol the target column position
     * @param endRow the target row position
     * @param direction the direction of movement (-1 for white, 1 for black)
     * @return true if the target position represents a valid pawn move, false
     * otherwise
     */
    private boolean isValid(Board gameBoard, int endCol, int endRow, int direction) {

        Piece target = getTarget(gameBoard.board, endCol, endRow);

        //move forward one square
        if (endCol == this.col && endRow == (this.row + direction) && target == null) {
            return true;
        }

        //move forward two squares 
        if (!hasMoved && endCol == this.col && endRow == (this.row + (direction * 2))) {
            Piece target2 = getTarget(gameBoard.board, endCol, row + direction);
            return target == null && target2 == null;
        }

        //En Passant
        if (gameBoard.enPassantCol == endCol && gameBoard.enPassantRow == endRow) {
            return Math.abs(endCol - col) == 1 && endRow == row + direction;
        }

        // Capture diagonally
        if (Math.abs(endCol - this.col) == 1 && endRow == this.row + direction) {
            return target != null && target.isWhite != this.isWhite;
        }

        return false;

    }

    /**
     * Retrieves the piece at the specified board position.
     *
     * @param board the 2D array representing the chess board
     * @param endCol the column position to check
     * @param endRow the row position to check
     * @return the Piece at the specified position, or null if the position is
     * out of bounds or empty
     */
    private Piece getTarget(Piece[][] board, int endCol, int endRow) {
        if (endCol >= 8 || endCol < 0 || endRow >= 8 || endRow < 0) {
            return null;
        }
        return board[endRow][endCol];
    }

    /**
     * Gets the current row position of this pawn.
     *
     * @return the row position (0-7)
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Gets the current column position of this pawn.
     *
     * @return the column position (0-7)
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * Determines the color of this pawn.
     *
     * @return true if this pawn is white, false if black
     */
    @Override
    public boolean isWhite() {
        return isWhite;
    }
}
