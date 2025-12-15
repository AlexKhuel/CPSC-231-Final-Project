
class Board {

    Piece[][] board = new Piece[8][8];

    int enPassantRow;
    int enPassantCol;
    boolean whiteTurn;
    boolean whiteShortCastle;
    boolean whiteLongCastle;
    boolean blackShortCastle;
    boolean blackLongCastle;

    public Board() {
        board = new Piece[8][8];

        board[0][0] = new Rook(0, 0, true);
        board[0][1] = new Knight(0, 1, true);
        board[0][2] = new Bishop(0, 2, true);
        board[0][3] = new Queen(0, 3, true);
        board[0][4] = new King(0, 4, true);
        board[0][5] = new Bishop(0, 5, true);
        board[0][6] = new Knight(0, 6, true);
        board[0][7] = new Rook(0, 7, true);

        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn(1, col, true, false);
        }

        for (int col = 0; col < 8; col++) {
            board[6][col] = new Pawn(6, col, false, false);
        }

        board[7][0] = new Rook(7, 0, false);
        board[7][1] = new Knight(7, 1, false);
        board[7][2] = new Bishop(7, 2, false);
        board[7][3] = new Queen(7, 3, false);
        board[7][4] = new King(7, 4, false);
        board[7][5] = new Bishop(7, 5, false);
        board[7][6] = new Knight(7, 6, false);
        board[7][7] = new Rook(7, 7, false);

        enPassantRow = -1;
        enPassantCol = -1;
        whiteTurn = true;
        whiteShortCastle = true;
        whiteLongCastle = true;
        blackShortCastle = true;
        blackLongCastle = true;
    }

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

    public boolean move(int startRow, int startCol, int endRow, int endCol) {

        if (startRow > 7 || startRow < 0 || startCol > 7 || startCol < 0 || endRow > 7 || endRow < 7 || endCol > 7 || endCol < 7) {
            return false;
        }

        if (board[startRow][startCol] == null) {
            return false;
        }

        if (board[startRow][startCol].isWhite() != whiteTurn) {
            return false;
        }

        if (isCheckmate()) {
            return false;
        }

        Piece currPiece = board[startRow][startCol];

        //Specific rook moving so we can update castling rights
        if (currPiece instanceof Rook && currPiece.canMove(this, endRow, endCol)) {
            if (whiteShortCastle && startRow == 0 && startCol == 7) {
                whiteShortCastle = false;
            } else if (whiteLongCastle && startRow == 0 && startCol == 0) {
                whiteLongCastle = false;
            } else if (blackShortCastle && startRow == 7 && startCol == 7) {
                blackShortCastle = false;
            } else if (blackLongCastle && startRow == 7 && startCol == 0) {
                blackLongCastle = false;
            }

            uncheckedMove(startRow, startCol, endRow, endCol);
            return true;
        }

        //If it is a king, check if the move is attempting castling
        if (currPiece instanceof King) {
            if (Math.abs(startCol - endCol) > 1) {
                return castle(endCol);
            }

            if (currPiece.canMove(this, endRow, endCol)) {
                uncheckedMove(startRow, startCol, endRow, endCol);
                return true;
            }

            return false;
        }

        if (currPiece instanceof Pawn && startCol != endCol) {
            int dir = endCol - startCol;

            if (board[startRow][startCol + dir] instanceof Pawn && board[endRow][endCol] == null) {
                movePassant(startRow, startCol, endRow, endCol);
                return true;
            }
        }

        if (currPiece.canMove(this, endRow, endCol)) {
            uncheckedMove(startRow, startCol, endRow, endCol);
            return true;
        }

        //Catches anything 
        return false;
    }

    public void uncheckedMove(int startRow, int startCol, int endRow, int endCol) {
        board[endRow][endCol] = board[startRow][startCol];
        board[startRow][startCol] = null;
    }

    private boolean movePassant(int startRow, int startCol, int endRow, int endCol) {
        return false;
    }

    private boolean castle(int endCol) {
        String castle;

        if (endCol == 2) {
            castle = "O-O-O";
        } else {
            castle = "O-O";
        }

        if (castle.equals("O-O")) {
            if (whiteTurn && whiteShortCastle) {
                if (board[0][5] != null || board[0][6] != null) {
                    return false;
                }
                King tempKingFirst = new King(0, 4, board[0][4].isWhite);
                King tempKingSecond = new King(0, 5, board[0][4].isWhite);

                if ((tempKingFirst.canMove(this, 0, 5) && tempKingSecond.canMove(this, 0, 6)) == false) {
                    return false;
                }

                board[0][6] = new King(0, 6, true);
                board[0][5] = new Rook(0, 5, true);
                board[0][4] = null;
                board[0][7] = null;
                whiteShortCastle = false;
                whiteLongCastle = false;
                return true;
            }

            if (whiteTurn && whiteLongCastle) {
                if (board[0][1] != null || board[0][2] != null || board[0][3] != null) {
                    return false;
                }
                King tempKingFirst = new King(0, 4, board[0][4].isWhite);
                King tempKingSecond = new King(0, 3, board[0][4].isWhite);

                if ((tempKingFirst.canMove(this, 0, 3) && tempKingSecond.canMove(this, 0, 2)) == false) {
                    return false;
                }

                board[0][2] = new King(0, 2, true);
                board[0][3] = new Rook(0, 3, true);
                board[0][4] = null;
                board[0][0] = null;
                whiteShortCastle = false;
                whiteLongCastle = false;
                return true;
            }

            if (!whiteTurn && blackShortCastle) {
                if (board[7][5] != null || board[7][6] != null) {
                    return false;
                }
                King tempKingFirst = new King(7, 4, board[7][4].isWhite);
                King tempKingSecond = new King(7, 5, board[7][4].isWhite);

                if ((tempKingFirst.canMove(this, 7, 5) && tempKingSecond.canMove(this, 7, 6)) == false) {
                    return false;
                }

                board[7][6] = new King(7, 6, false);
                board[7][5] = new Rook(7, 5, false);
                board[7][4] = null;
                board[7][7] = null;
                blackShortCastle = false;
                blackLongCastle = false;
                return true;
            }

            if (!whiteTurn && blackLongCastle) {
                if (board[7][1] != null || board[7][2] != null || board[7][3] != null) {
                    return false;
                }
                King tempKingFirst = new King(7, 4, board[7][4].isWhite);
                King tempKingSecond = new King(7, 3, board[7][4].isWhite);

                if ((tempKingFirst.canMove(this, 7, 3) && tempKingSecond.canMove(this, 7, 2)) == false) {
                    return false;
                }

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
        King currKing = getKing(whiteTurn);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    continue;
                }
                if (board[i][j].isWhite != whiteTurn) {
                    continue;
                }

                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (board[i][j].canMove(this, row, col)) {
                            Piece movingPiece = board[i][j];
                            Piece oldPiece = board[row][col];

                            board[row][col] = movingPiece.copy();
                            board[row][col].row = row;
                            board[row][col].col = col;
                            board[i][j] = null;

                            boolean wouldCheck = currKing.isInCheck(this, currKing.getRow(), currKing.getCol());

                            board[i][j] = movingPiece;
                            board[row][col] = oldPiece;

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
