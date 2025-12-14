
class Board {

    Piece[][] board = new Piece[8][8];

    int enPassantRow;
    int enPassantCol;
    boolean whiteTurn;
    boolean whiteShortCastle;
    boolean whiteLongCastle;
    boolean blackShortCastle;
    boolean blackLongCastle;

    public King getKing(boolean isWhite) {
        int r = isWhite ? 7 : 0;
        int c = isWhite ? 7 : 0;
        int direction = isWhite ? -1 : 1;

        for (int row = r; isWhite ? row >= 0 : row < 8; row += direction) {
            for (int col = c; isWhite ? col >= 0 : col < 8; col += direction) {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.isWhite == isWhite) {
                    return (King) piece;
                }
            }
        }

        return null; //somethings wrong
    }

    public boolean move(String userInput) {
        return false; // Temp
    }

    private boolean movePassant(int startRow, int startCol, int endRow, int endCol) {
        return false; // Temp
    }

    private boolean castle(String castle) {
        if (castle.equals("0-0") || castle.equals("O-O")) {
            if (whiteTurn && whiteShortCastle && board[0][4].canMove(this, 0, 6)) {
                board[0][6] = new King(0, 6, true);
                board[0][5] = new Rook(0, 5, true);
                board[0][4] = null;
                board[0][7] = null;
                whiteShortCastle = false;
                whiteLongCastle = false;
                return true;
            } else if (!whiteTurn && blackShortCastle && board[7][4].canMove(this, 7, 6)) {
                board[7][6] = new King(7, 6, false);
                board[7][5] = new Rook(7, 5, false);
                board[7][4] = null;
                board[7][7] = null;
                blackShortCastle = false;
                blackLongCastle = false;
                return true;
            }
        } else if (castle.equals("0-0-0") || castle.equals("O-O-O")) {
            if (whiteTurn && whiteLongCastle && board[0][4].canMove(this, 0, 2)) {
                board[0][2] = new King(0, 2, true);
                board[0][3] = new Rook(0, 3, true);
                board[0][4] = null;
                board[0][0] = null;
                whiteShortCastle = false;
                whiteLongCastle = false;
                return true;
            } else if (!whiteTurn && blackLongCastle && board[7][4].canMove(this, 7, 2)) {
                board[7][2] = new King(7, 2, false);
                board[7][3] = new Rook(7, 3, false);
                board[7][4] = null;
                board[7][0] = null;
                blackShortCastle = false;
                blackLongCastle = false;
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate() {
        King currKing = this.getKing(this.whiteTurn);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.board[i][j] == null) {
                    continue;
                }
                if (this.board[i][j].isWhite != this.whiteTurn) {
                    continue;
                }

                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (this.board[i][j].canMove(this, row, col)) {
                            Piece movingPiece = this.board[i][j];
                            Piece oldPiece = this.board[row][col];

                            this.board[row][col] = movingPiece.copy();
                            this.board[row][col].row = row;
                            this.board[row][col].col = col;
                            this.board[i][j] = null;

                            boolean wouldCheck = currKing.isInCheck(this, currKing.getRow(), currKing.getCol());

                            this.board[i][j] = movingPiece;
                            this.board[row][col] = oldPiece;

                            if (!wouldCheck) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;

    }

}
