package backend;

abstract class Piece {
    protected boolean isWhite;
    protected int row;
    protected int col;

    public abstract boolean canMove(Board board, int endRow, int endCol);
}