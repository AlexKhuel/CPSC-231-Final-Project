/*
CPSC-231 Final Project
Chess Board
By: Jake Puebla, Joshua Dowd, Nate Smith, Jerry Salas

Date Completed: December 14, 2025
*/


/**
 * Abstract base class representing a chess piece.
 * 
 * This class defines the common attributes and methods that all chess pieces
 * must implement, including position tracking, movement validation, and color
 * identification.
 */
abstract class Piece {

    /**
     * The current row position of this piece on the board (0-7)
     */
    protected int row;
    
    /**
     * The current column position of this piece on the board (0-7)
     */
    protected int col;
    
    /**
     * The color of this piece (true for white, false for black)
     */
    protected boolean isWhite;

    /**
     * Determines if this piece can legally move to the specified position.
     * 
     * @param gameBoard the current game board
     * @param endRow the target row position (0-7)
     * @param endCol the target column position (0-7)
     * @return true if the move is legal according to this piece's movement rules
     *         and does not leave the player's king in check, false otherwise
     */
    public abstract boolean canMove(Board gameBoard, int endRow, int endCol);

    /**
     * Creates a deep copy of this piece.
     * 
     * @return a new Piece object with the same attributes as this piece
     */
    public abstract Piece copy();

    /**
     * Gets the current row position of this piece.
     * 
     * @return the row position (0-7)
     */
    public abstract int getRow();

    /**
     * Gets the current column position of this piece.
     * 
     * @return the column position (0-7)
     */
    public abstract int getCol();

    /**
     * Determines the color of this piece.
     * 
     * @return true if this piece is white, false if black
     */
    public abstract boolean isWhite();
}