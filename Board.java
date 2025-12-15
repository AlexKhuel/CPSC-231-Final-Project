
class Board {

    Piece[][] board = new Piece[8][8];

    int enPassantRow;
    int enPassantCol;
    boolean enPassantIsWhite;
    boolean whiteTurn;
    boolean whiteShortCastle;
    boolean whiteLongCastle;
    boolean blackShortCastle;
    boolean blackLongCastle;

    public Board() {
        board = new Piece[8][8];

        board[0][0] = new Rook(0, 0, false);
        board[0][1] = new Knight(0, 1, false);
        board[0][2] = new Bishop(0, 2, false);
        board[0][3] = new Queen(0, 3, false);
        board[0][4] = new King(0, 4, false);
        board[0][5] = new Bishop(0, 5, false);
        board[0][6] = new Knight(0, 6, false);
        board[0][7] = new Rook(0, 7, false);

        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn(1, col, false, false);
        }

        for (int col = 0; col < 8; col++) {
            board[6][col] = new Pawn(6, col, true, false);
        }

        board[7][0] = new Rook(7, 0, true);
        board[7][1] = new Knight(7, 1, true);
        board[7][2] = new Bishop(7, 2, true);
        board[7][3] = new Queen(7, 3, true);
        board[7][4] = new King(7, 4, true);
        board[7][5] = new Bishop(7, 5, true);
        board[7][6] = new Knight(7, 6, true);
        board[7][7] = new Rook(7, 7, true);

        enPassantRow = -1;
        enPassantCol = -1;
        whiteTurn = true;
        whiteShortCastle = true;
        whiteLongCastle = true;
        blackShortCastle = true;
        blackLongCastle = true;
    }

    public Board(Board other) {
        this.board = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (other.board[row][col] != null) {
                    this.board[row][col] = other.board[row][col].copy();
                }
            }
        }

        this.enPassantRow = other.enPassantRow;
        this.enPassantCol = other.enPassantCol;
        this.whiteTurn = other.whiteTurn;
        this.whiteShortCastle = other.whiteShortCastle;
        this.whiteLongCastle = other.whiteLongCastle;
        this.blackShortCastle = other.blackShortCastle;
        this.blackLongCastle = other.blackLongCastle;
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
        if (startRow > 7 || startRow < 0 || startCol > 7 || startCol < 0 || endRow > 7 || endRow < 0 || endCol > 7 || endCol < 0) {
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
        if (currPiece instanceof Rook currRook && currRook.canMove(this, endRow, endCol)) {
            if (whiteShortCastle && startRow == 0 && startCol == 7) {
                whiteShortCastle = false;
            } else if (whiteLongCastle && startRow == 0 && startCol == 0) {
                whiteLongCastle = false;
            } else if (blackShortCastle && startRow == 7 && startCol == 7) {
                blackShortCastle = false;
            } else if (blackLongCastle && startRow == 7 && startCol == 0) {
                blackLongCastle = false;
            }

            board[endRow][endCol] = new Rook(endRow, endCol, board[startRow][startCol].isWhite());
            board[startRow][startCol] = null;

            return true;
        }

        //If it is a king, check if the move is attempting castling
        if (currPiece instanceof King currKing) {

            if (Math.abs(startCol - endCol) > 1) {
                return castle(endCol);
            }

            if (currKing.canMove(this, endRow, endCol)) {
                board[endRow][endCol] = new King(endRow, endCol, board[startRow][startCol].isWhite());
                board[startRow][startCol] = null;
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

        if (currPiece instanceof Pawn currPawn) {

            if (currPawn.canMove(this, endRow, endCol)) {

                if (Math.abs(currPawn.getRow() - endRow) == 2) {

                    enPassantIsWhite = currPawn.isWhite;
                    enPassantCol = endCol;
                    enPassantRow = currPawn.isWhite ? endRow + 1 : endRow - 1;
                }
                board[endRow][endCol] = new Pawn(endRow, endCol, currPawn.isWhite, true);
                board[startRow][startCol] = null;
                return true;
            }
        }

        System.out.println("currPiece instanceOf Pawn" + (currPiece instanceof Pawn));

        if (currPiece.canMove(this, endRow, endCol)) {
            uncheckedMove(currPiece, startRow, startCol, endRow, endCol);
            return true;
        }

        //Catches anything 
        return false;
    }

    public void uncheckedMove(Piece currPiece, int startRow, int startCol, int endRow, int endCol) {
        if (currPiece instanceof Pawn) {
            board[endRow][endCol] = new Pawn(endRow, endCol, board[startRow][startCol].isWhite(), true);
            board[startRow][startCol] = null;
        } else if (currPiece instanceof Bishop) {
            board[endRow][endCol] = new Bishop(endRow, endCol, board[startRow][startCol].isWhite());
            board[startRow][startCol] = null;
        } else if (currPiece instanceof Knight) {
            board[endRow][endCol] = new Knight(endRow, endCol, board[startRow][startCol].isWhite());
            board[startRow][startCol] = null;
        } else if (currPiece instanceof Rook) {
            board[endRow][endCol] = new Rook(endRow, endCol, board[startRow][startCol].isWhite());
            board[startRow][startCol] = null;
        } else if (currPiece instanceof Queen) {
            board[endRow][endCol] = new Queen(endRow, endCol, board[startRow][startCol].isWhite());
            board[startRow][startCol] = null;
        } else {
            board[endRow][endCol] = new King(endRow, endCol, board[startRow][startCol].isWhite());
            board[startRow][startCol] = null;
        }
    }

    private boolean movePassant(int startRow, int startCol, int endRow, int endCol) {
        int direction = board[startRow][startCol].isWhite ? 1 : -1;

        Piece currPiece = board[startRow][startCol];
        if (currPiece.canMove(this, endRow, endCol)) {

            uncheckedMove(currPiece, startRow, startCol, endRow, endCol);
            board[endRow + direction][endCol] = null;
        } else {

            return false;
        }

        return true;
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

                if (board[7][5] != null || board[7][6] != null) {

                    return false;
                }
                King tempKingFirst = new King(7, 4, board[7][4].isWhite);
                King tempKingSecond = new King(7, 5, board[7][4].isWhite);

                if ((tempKingFirst.canMove(this, 7, 5) && tempKingSecond.canMove(this, 7, 6)) == false) {
                    return false;
                }

                board[7][6] = new King(7, 6, true);
                board[7][5] = new Rook(7, 5, true);
                board[7][4] = null;
                board[7][7] = null;
                whiteShortCastle = false;
                whiteLongCastle = false;
                return true;
            }

            if (whiteTurn && whiteLongCastle) {
                if (board[7][1] != null || board[7][2] != null || board[7][3] != null) {
                    return false;
                }
                King tempKingFirst = new King(7, 4, board[7][4].isWhite);
                King tempKingSecond = new King(7, 3, board[7][4].isWhite);

                if ((tempKingFirst.canMove(this, 7, 3) && tempKingSecond.canMove(this, 7, 2)) == false) {
                    return false;
                }

                board[7][2] = new King(7, 2, true);
                board[7][3] = new Rook(7, 3, true);
                board[7][4] = null;
                board[7][0] = null;
                whiteShortCastle = false;
                whiteLongCastle = false;
                return true;
            }

            if (!whiteTurn && blackShortCastle) {
                if (board[0][5] != null || board[0][6] != null) {
                    return false;
                }
                King tempKingFirst = new King(0, 4, board[0][4].isWhite);
                King tempKingSecond = new King(0, 5, board[0][4].isWhite);

                if ((tempKingFirst.canMove(this, 0, 5) && tempKingSecond.canMove(this, 0, 6)) == false) {
                    return false;
                }

                board[0][6] = new King(0, 6, false);
                board[0][5] = new Rook(0, 5, false);
                board[0][4] = null;
                board[0][7] = null;
                blackShortCastle = false;
                blackLongCastle = false;
                return true;
            }

            if (!whiteTurn && blackLongCastle) {
                if (board[0][1] != null || board[0][2] != null || board[0][3] != null) {
                    return false;
                }
                King tempKingFirst = new King(0, 4, board[0][4].isWhite);
                King tempKingSecond = new King(0, 3, board[0][4].isWhite);

                if ((tempKingFirst.canMove(this, 0, 3) && tempKingSecond.canMove(this, 0, 2)) == false) {
                    return false;
                }

                board[0][2] = new King(0, 2, false);
                board[0][3] = new Rook(0, 3, false);
                board[0][4] = null;
                board[0][0] = null;
                blackShortCastle = false;
                blackLongCastle = false;
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate() {

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

                            boolean wouldCheck = getKing(whiteTurn).isInCheck(this);

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

    public void changeTurn() {
        whiteTurn = !whiteTurn;
        if (whiteTurn == enPassantIsWhite) {
            enPassantCol = -1;
            enPassantRow = -1;
            enPassantIsWhite = false;
        }

    }

}
