
abstract class Piece {

    protected int row;
    protected int col;
    protected boolean isWhite;

    public abstract boolean canMove(Board gameBoard, int endRow, int endCol);

    public abstract Piece copy();

    public abstract int getRow();

    public abstract int getCol();

    public abstract boolean isWhite();
}
