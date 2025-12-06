package backend;

abstract class Piece {
    public abstract boolean canMove(Board board, int endRow, int endCol);
    public abstract char getSymbol();
}