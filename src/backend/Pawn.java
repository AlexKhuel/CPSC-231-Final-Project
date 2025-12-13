package backend;
/**
 * Represents a Pawn piece in a chess game.
 * 
 * A Pawn can move forward one square, or two squares on its first move.
 * It captures diagonally and supports en passant moves.
 */
public class Pawn extends Piece{
    /** Tracks whether this pawn has moved from its starting position */
    public boolean hasMoved;
    static final int type = 1;

    /**
     * Constructs a new Pawn with the specified color and position.
     * 
     * @param isWhite true if this pawn belongs to white, false if black
     * @param row the starting row position on the board (0-7)
     * @param col the starting column position on the board (0-7)
     */
    public Pawn(boolean isWhite, int row, int col){
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
        hasMoved = false;
    }
     /**
     * Checks the move that is trying to be made by the user 
     * 
     * @param gameBoard the gameBoard object that is intialized in main allowing access to the board and passant variables
     * @param endRow the ending row position on the board (0-7)
     * @param endCol the ending column position on the board (0-7)
     */
    public boolean canMove(Board gameBoard, int endRow, int endCol){
        int direction = isWhite ? 1 : -1;

        if (!isValid(gameBoard, endCol, endRow, direction)){
            return false;
        }

        if (isInCheck(gameBoard, endRow, endCol)){
            return false; 
        }

        hasMoved = true;
        return true;
    }

    private boolean isValid(Board gameBoard, int endCol, int endRow, int direction){

        Piece target = getTarget(gameBoard.board, endCol, endRow);

        //move forward one square
        if (endCol == this.col && endRow == (this.row+direction) && target == null){
            return true;
        }

        //move forward two squares 
        if (!hasMoved && endCol == this.col && endRow == (this.row+(direction*2))){
            Piece target2 = getTarget(gameBoard.board, endCol, row+direction);
            return target == null && target2 == null;
        }

        // Capture diagonally
        if (Math.abs(endCol - this.col) == 1 && endRow == this.row + direction){
            return target != null && target.isWhite != this.isWhite;
        }

        //En Passant
        if (gameBoard.enPassantCol == endCol && gameBoard.enPassantRow == endRow){
            return Math.abs(endCol - col) == 1 && endRow == row+direction;
        }

        return false;


    }

    private Piece getTarget(Piece[][] board, int endCol, int endRow){
        if (endCol >= 8 || endCol < 0 || endRow >= 8 || endRow < 0){
            return null;
        }
        return board[endRow][endCol];
    }
}