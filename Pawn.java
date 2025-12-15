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
     */
    public Pawn(int row, int col, boolean isWhite, boolean hasMoved) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.hasMoved = hasMoved;
    }

    @Override
    public Piece copy() {
        return new Pawn(this.row, this.col, this.isWhite, this.hasMoved);
    }

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

    private Piece getTarget(Piece[][] board, int endCol, int endRow) {
        if (endCol >= 8 || endCol < 0 || endRow >= 8 || endRow < 0) {
            return null;
        }
        return board[endRow][endCol];
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
