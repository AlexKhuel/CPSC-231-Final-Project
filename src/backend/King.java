package backend;

/**
 * This class is used to represent a King piece in a game of chess.
 * @author Jake Puebla
 * @version 1.0
 * @see Piece.java
 * @see Board.java
 */
class King extends Piece {

    static final int type = 6;
    public boolean hasMoved;

    /**
     * Constructor for the King object.
     * @param color - the piece's color (black or white).
     * @param r - the piece's row position.
     * @param c - the piece's column position.
     */
    public King(boolean color, int r, int c) {
        row = r;
        col = c;
        isWhite = color;
        hasMoved = false;
    }

    /**
     * Overriden function. Determines whether a move is valid.
     * @param gameBoard - the Board object where the game is taking place.
     * @param endRow - the row of the move location that is being checked.
     * @param endCol - the column of the move location that is being checked.
     */
    @Override
    public boolean canMove(Board gameBoard, int endRow, int endCol) {
        
        if (endRow == row && endCol == col) {
            return false;
        } else if (endCol < 0 || endRow < 0 || endCol > 7 || endRow > 7) {
            return false;
        }

        // King can only move one square in any direction
        if (Math.abs(endRow - row) <= 1 && Math.abs(endCol - col) <= 1) {
            // Check if destination square has a piece of the same color
            if (gameBoard.board[endRow][endCol] != null && 
                gameBoard.board[endRow][endCol].isWhite == isWhite) {
                return false;
            }

            // Check if the move would put the king in check
            return !isInCheck(gameBoard, row, col, endRow, endCol);
        } else {
            return false;
        }
    }

    /**
     * Checks if the king would be in check after a move.
     * @param gameBoard - the Board object where the game is taking place.
     * @param startRow - the starting row position.
     * @param startCol - the starting column position.
     * @param endRow - the ending row position.
     * @param endCol - the ending column position.
     * @return true if the king would be in check, false otherwise.
     */
    public boolean isInCheck(Board gameBoard, int startRow, int startCol, int endRow, int endCol) {
        // Temporarily make the move
        Piece capturedPiece = gameBoard.board[endRow][endCol];
        gameBoard.board[endRow][endCol] = this;
        gameBoard.board[startRow][startCol] = null;

        // Check if any opponent piece can attack the king's new position
        boolean inCheck = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = gameBoard.board[i][j];
                if (piece != null && piece.isWhite != isWhite) {
                    if (piece.canMove(gameBoard, endRow, endCol)) {
                        inCheck = true;
                        break;
                    }
                }
            }
            if (inCheck) break;
        }

        // Undo the temporary move
        gameBoard.board[startRow][startCol] = this;
        gameBoard.board[endRow][endCol] = capturedPiece;

        return inCheck;
    }

    /**
     * Checks if the king can castle to the specified side.
     * @param gameBoard - the Board object where the game is taking place.
     * @param isKingSide - true for kingside castle, false for queenside castle.
     * @return true if castling is legal, false otherwise.
     */
    public boolean canCastle(Board gameBoard, boolean isKingSide) {
        if (hasMoved) {
            return false;
        }

        int rookCol = isKingSide ? 7 : 0;
        Piece rook = gameBoard.board[row][rookCol];

        // Check if rook exists and hasn't moved
        if (rook == null || rook.type != 4) {
            return false;
        }
        
        // Cast to Rook to check hasMoved
        if (rook instanceof Rook && ((Rook) rook).hasMoved) {
            return false;
        }

        // Check if squares between king and rook are empty
        int start = Math.min(col, rookCol);
        int end = Math.max(col, rookCol);
        for (int i = start + 1; i < end; i++) {
            if (gameBoard.board[row][i] != null) {
                return false;
            }
        }

        // Check if king is currently in check
        if (isInCheck(gameBoard, row, col, row, col)) {
            return false;
        }

        // Check if king passes through or ends in check
        int direction = isKingSide ? 1 : -1;
        for (int i = 1; i <= 2; i++) {
            if (isInCheck(gameBoard, row, col, row, col + (direction * i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the symbol representing this piece.
     * @return 'K' for white king, 'k' for black king.
     */
    public char getSymbol() {
        return isWhite ? 'K' : 'k';
    }
}