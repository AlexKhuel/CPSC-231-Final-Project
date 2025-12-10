class Board{

    Piece[][] board = new Piece[8][8];

    int enPassantCol;
    boolean whiteTurn;
    boolean whiteShortCastle;
    boolean whiteLongCastle;
    boolean blackShortCastle;
    boolean blackLongCastle;

    public void move(int startRow, int startCol, int endRow, int endCol){

    }
    private void movePassant(int startRow, int startCol, int endRow, int endCol){

    }
    private void castle(boolean isKingSide){

    }
    public boolean isCheckmate(){
        int kingRow = -1;
        int kingCol = -1;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.board[i][j] != null && this.board[i][j].isWhite == this.whiteTurn && this.board[i][j].type == 6){
                    kingRow = i;
                    kingCol = j;
                }
            }
        }

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.board[i][j] == null)
                    continue;
                if(this.board[i][j].isWhite != this.whiteTurn)
                    continue;
                
                switch(this.board[i][j].type){
                    case 1:
                        for(int r = 0; r < 8; r++){
                            for(int c = 0; c < 8; c++){
                                if(this.board[i][j].canMove(this, r, c) == true){
                                    Piece oldPiece = board[r][c];
                                    this.board[r][c] = new Pawn(r, c, this.board[i][j].isWhite);
                                    this.board[i][j] = null;
                                    boolean wouldCheck = this.board[kingRow][kingCol].isInCheck();
                                    this.board[i][j] = new Pawn(i, j, this.board[r][c].isWhite);
                                    this.board[r][c] = oldPiece;
                                    if(!wouldCheck)
                                        return false;
                                }
                            }
                        }
                        break;
                    case 2:
                        for(int r = 0; r < 8; r++){
                            for(int c = 0; c < 8; c++){
                                if(this.board[i][j].canMove(this, r, c) == true){
                                    Piece oldPiece = board[r][c];
                                    this.board[r][c] = new Knight(r, c, this.board[i][j].isWhite);
                                    this.board[i][j] = null;
                                    boolean wouldCheck = this.board[kingRow][kingCol].isInCheck();
                                    this.board[i][j] = new Knight(i, j, this.board[r][c].isWhite);
                                    this.board[r][c] = oldPiece;
                                    if(!wouldCheck)
                                        return false;
                                }
                            }
                        }
                        break;
                    case 3:
                        for(int r = 0; r < 8; r++){
                            for(int c = 0; c < 8; c++){
                                if(this.board[i][j].canMove(this, r, c) == true){
                                    Piece oldPiece = board[r][c];
                                    this.board[r][c] = new Bishop(r, c, this.board[i][j].isWhite);
                                    this.board[i][j] = null;
                                    boolean wouldCheck = this.board[kingRow][kingCol].isInCheck();
                                    this.board[i][j] = new Bishop(i, j, this.board[r][c].isWhite);
                                    this.board[r][c] = oldPiece;
                                    if(!wouldCheck)
                                        return false;
                                }
                            }
                        }
                        break;
                    case 4:
                        for(int r = 0; r < 8; r++){
                            for(int c = 0; c < 8; c++){
                                if(this.board[i][j].canMove(this, r, c) == true){
                                    Piece oldPiece = board[r][c];
                                    this.board[r][c] = new Rook(r, c, this.board[i][j].isWhite);
                                    this.board[i][j] = null;
                                    boolean wouldCheck = this.board[kingRow][kingCol].isInCheck();
                                    this.board[i][j] = new Rook(i, j, this.board[r][c].isWhite);
                                    this.board[r][c] = oldPiece;
                                    if(!wouldCheck)
                                        return false;
                                }
                            }
                        }
                        break;
                    case 5:
                        for(int r = 0; r < 8; r++){
                            for(int c = 0; c < 8; c++){
                                if(this.board[i][j].canMove(this, r, c) == true){
                                    Piece oldPiece = board[r][c];
                                    this.board[r][c] = new Queen(r, c, this.board[i][j].isWhite);
                                    this.board[i][j] = null;
                                    boolean wouldCheck = this.board[kingRow][kingCol].isInCheck();
                                    this.board[i][j] = new Queen(i, j, this.board[r][c].isWhite);
                                    this.board[r][c] = oldPiece;
                                    if(!wouldCheck)
                                        return false;
                                }
                            }
                        }
                        break;
                    case 6:
                        for(int r = 0; r < 8; r++){
                            for(int c = 0; c < 8; c++){
                                if(this.board[i][j].canMove(this, r, c) == true){
                                    Piece oldPiece = board[r][c];
                                    this.board[r][c] = new King(r, c, this.board[i][j].isWhite);
                                    this.board[i][j] = null;
                                    boolean wouldCheck = this.board[r][c].isInCheck();
                                    this.board[i][j] = new King(i, j, this.board[r][c].isWhite);
                                    this.board[r][c] = oldPiece;
                                    if(!wouldCheck)
                                        return false;
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("You are dumb");
                        break;
                }
            }
        }

        return true;

    }

}