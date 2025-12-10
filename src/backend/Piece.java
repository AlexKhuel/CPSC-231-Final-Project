
abstract class Piece {
    protected boolean isWhite;
    protected int row;
    protected int col;
    protected static int type;

    public abstract boolean canMove(Board gameBoard, int endRow, int endCol);
}