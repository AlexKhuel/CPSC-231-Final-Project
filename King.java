
/**
 * This class is used to represent a King piece in a game of chess.
 *
 * @author Jerry Salas
 * @version 1.8
 * @see Piece.java
 * @see Board.java
 */
class King extends Piece {

    /**
     * Constructor for the King object.
     *
     * @param color - the piece's color (black or white).
     * @param r - the piece's row position.
     * @param c - the piece's column position.
     */
    public King(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    @Override
    public Piece copy() {
        return new King(this.row, this.col, this.isWhite);
    }

    /**
     * Overriden function. Determines whether a move is valid.
     *
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
            if (gameBoard.board[endRow][endCol] != null
                    && gameBoard.board[endRow][endCol].isWhite == isWhite) {
                return false;
            }

            // Simulate the move to check if it puts king in check
            Piece capturedPiece = gameBoard.board[endRow][endCol];
            gameBoard.board[endRow][endCol] = this;
            gameBoard.board[row][col] = null;

            // Update king's position temporarily
            int oldRow = row;
            int oldCol = col;
            row = endRow;
            col = endCol;

            // Check if king would be in check at new position
            boolean wouldBeInCheck = isInCheck(gameBoard);

            // Restore king's position
            row = oldRow;
            col = oldCol;
            gameBoard.board[oldRow][oldCol] = this;
            gameBoard.board[endRow][endCol] = capturedPiece;

            return !wouldBeInCheck;
        } else {
            return false;
        }
    }

    /**
     * Checks if the king is in check at the specified position. This checks if
     * any opponent piece can attack the king.
     *
     * @param gameBoard - the Board object where the game is taking place.
     * @param startRow - not used, kept for interface compatibility.
     * @param startCol - not used, kept for interface compatibility.
     * @param kingRow - the row where the king is located.
     * @param kingCol - the column where the king is located.
     * @return true if the king is in check, false otherwise.
     */
    public boolean isInCheck(Board gameBoard) {
        // Check if any opponent piece can attack the king at (kingRow, kingCol)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = gameBoard.board[i][j];
                if (piece != null && piece.isWhite != isWhite) {
                    // Check if this opponent piece can attack the king's position
                    // We need to check basic movement without recursively checking for check
                    if (canPieceAttack(piece, gameBoard, i, j, row, col)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Helper method to check if a piece can attack a target square. This checks
     * basic movement rules without checking for check (prevents infinite
     * recursion).
     */
    private boolean canPieceAttack(Piece piece, Board gameBoard, int fromRow, int fromCol, int toRow, int toCol) {
        // Basic bounds checking
        if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
            return false;
        }

        // Can't attack own piece
        if (gameBoard.board[toRow][toCol] != null
                && gameBoard.board[toRow][toCol].isWhite == piece.isWhite) {
            return false;
        }

        // Check based on piece TYPE
        if (piece instanceof Pawn) {
            // Pawns attack diagonally one square
            int direction = piece.isWhite ? 1 : -1;
            return (toRow == fromRow + direction && Math.abs(toCol - fromCol) == 1);
        } else if (piece instanceof Knight) {
            // Knight moves in L-shape
            int rowDiff = Math.abs(toRow - fromRow);
            int colDiff = Math.abs(toCol - fromCol);
            return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        } else if (piece instanceof Bishop) {
            // Bishop moves diagonally
            if (Math.abs(toRow - fromRow) != Math.abs(toCol - fromCol)) {
                return false;
            }
            return isPathClear(gameBoard, fromRow, fromCol, toRow, toCol);
        } else if (piece instanceof Rook) {
            // Rook moves horizontally or vertically
            if (fromRow != toRow && fromCol != toCol) {
                return false;
            }
            return isPathClear(gameBoard, fromRow, fromCol, toRow, toCol);
        } else if (piece instanceof Queen) {
            // Queen moves like bishop or rook
            boolean isDiagonal = Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol);
            boolean isStraight = (fromRow == toRow || fromCol == toCol);
            if (!isDiagonal && !isStraight) {
                return false;
            }
            return isPathClear(gameBoard, fromRow, fromCol, toRow, toCol);
        } else if (piece instanceof King) {
            // King attacks one square in any direction
            return Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1;
        }

        return false;
    }

    /**
     * Helper method to check if the path between two squares is clear.
     */
    private boolean isPathClear(Board gameBoard, int fromRow, int fromCol, int toRow, int toCol) {
        int rowDir = (toRow - fromRow) == 0 ? 0 : (toRow - fromRow) / Math.abs(toRow - fromRow);
        int colDir = (toCol - fromCol) == 0 ? 0 : (toCol - fromCol) / Math.abs(toCol - fromCol);

        int currentRow = fromRow + rowDir;
        int currentCol = fromCol + colDir;

        while (currentRow != toRow || currentCol != toCol) {
            if (gameBoard.board[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowDir;
            currentCol += colDir;
        }

        return true;
    }


    /**
     * Returns the symbol representing this piece.
     *
     * @return 'K' for white king, 'k' for black king.
     */
    public char getSymbol() {
        return isWhite ? 'K' : 'k';
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }
}
