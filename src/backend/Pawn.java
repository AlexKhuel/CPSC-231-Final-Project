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
    static const int type = 1;

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
        this.col = col;2
        hasMoved = false;
    }

    /**
     * Determines whether this pawn can move to the specified position.
     * 
     * Validates the following move types:
     * <ul>
     *   <li>Two squares forward on the first move (if path is clear)</li>
     *   <li>One square forward (if destination is empty)</li>
     *   <li>Diagonal capture (if opponent piece occupies the diagonal square)</li>
     *   <li>En passant capture (if conditions are met)</li>
     * </ul>
     * 
     * After a valid move, marks this pawn as having moved. Also verifies that the
     * move does not leave the king in check.
     * 
     * @param gameBoard the current state of the chess board
     * @param endRow the target row for the move (0-7)
     * @param endCol the target column for the move (0-7)
     * @return true if the move is legal and does not result in check, false otherwise
     */
    public boolean canMove(Board gameBoard, int endRow, int endCol){
        if (this.isWhite){
            if (!hasMoved && ((endRow == row+2) && (col == endCol)) && (gameBoard.board[row+1][endCol] == null)){
                if (!(isInCheck(gameBoard.board, row, col, endRow, endCol))){
                    hasMoved = true;
                }
                return !(isInCheck(gameBoard.board, row, col, endRow, endCol));
            } else if (((endRow == row+1) && (col == endCol)) && (gameBoard.board[endRow][endCol] == null)) {
                
                if (!(isInCheck(gameBoard.board, row, col, endRow, endCol))){
                    hasMoved = true;
                }
                return !(isInCheck(gameBoard.board, row, col, endRow, endCol));
            } else if (((endRow == row+1) && ((endCol == col-1) || (endCol == col+1)) && (gameBoard.board[endRow][endCol].isWhite != this.isWhite))){
                if (!(isInCheck(gameBoard.board, row, col, endRow, endCol))){
                    hasMoved = true;
                }
                return !(isInCheck(gameBoard.board, row, col, endRow, endCol));
            } else if ((gameBoard.enPassantCol == endCol) && (gameBoard.enPassantRow == endRow) && ()){
                if (!(isInCheck(gameBoard.board, row, col, endRow, endCol))){
                    hasMoved = true;
                }
                return !(isInCheck(gameBoard.board, row, col, endRow, endCol));
            }
            return false;
        } else {
            if (!hasMoved && ((endRow == row-2) && (col == endCol)) && (gameBoard.board[row-1][endCol] == null)){
                if (!(isInCheck(gameBoard.board, row, col, endRow, endCol))){
                    hasMoved = true;
                }
                return !(isInCheck(gameBoard.board, row, col, endRow, endCol));
            } else if (((endRow == row-1) && (col == endCol)) && (gameBoard.board[endRow][endCol] == null)) {
                
                if (!(isInCheck(gameBoard.board, row, col, endRow, endCol))){
                    hasMoved = true;
                }
                return !(isInCheck(gameBoard.board, row, col, endRow, endCol));
            } else if (((endRow == row-1) && ((endCol == col-1) || (endCol == col+1)) && (gameBoard.board[endRow][endCol].isWhite != this.isWhite))){
                if (!(isInCheck(gameBoard.board, row, col, endRow, endCol))){
                    hasMoved = true;
                }
                return !(isInCheck(gameBoard.board, row, col, endRow, endCol));
            } else if (((gameBoard.enPassantCol == endCol) && ((gameBoard.enPassantRow == endRow) || (endRow == row+1))) && ((gameBoard.board[endRow+1][endCol].isWhite != this.isWhite) || (gameBoard.board[endRow-1][endCol].isWhite != this.isWhite))){
                if (!(isInCheck(gameBoard.board, row, col, endRow, endCol))){
                    hasMoved = true;
                }
                return !(isInCheck(gameBoard.board, row, col, endRow, endCol));
            }
            return false;
        }
    }
}