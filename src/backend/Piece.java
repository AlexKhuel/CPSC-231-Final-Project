package backend;

abstract class Piece {
    public boolean isWhite;
    public abstract boolean canMove(Board board, int endRow, int endCol);
}