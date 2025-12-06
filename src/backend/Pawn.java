package backend;

public class Pawn {
    public boolean isWhite;
    public int row;
    public int col;
    public boolean hasMoved;

    public Pawn(boolean isWhite, int row, int col){
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
        hasMoved = false;
    }

    public boolean canMove(Board gameBoard, int endRow, int endCol){
        if (!hasMoved && ((endRow == row+2) && (col == endCol)) && (gameBoard.board[row+1][endCol] == null)){
            hasMoved = true;
            return true;
        } else if (((endRow == row+1) && (col == endCol)) && (gameBoard.board[endRow][endCol] == null)) {
            hasMoved = true;
            return true;
        } else if (((endRow == row+1) && ((endCol == col-1) || (endCol == col+1)) && (gameBoard.board[endRow][endCol].isWhite != this.isWhite))){
            hasMoved = true;
            return true;
        } else if (((gameBoard.enPassantCol == endCol) && ((endRow == row-1) || (endRow == row+1))) && ((gameBoard.board[endRow+1][endCol].isWhite != this.isWhite) || (gameBoard.board[endRow-1][endCol].isWhite != this.isWhite))){
            hasMoved = true;
            return true;
        }
        return false;
    }


    
}
