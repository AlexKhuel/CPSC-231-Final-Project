import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class chessBoard extends JPanel {   

    private ChessGame game;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private int dragX = 0;
    private int dragY = 0;
    private boolean isDragging = false;
    private int squareSize = 0;

    public chessBoard() {
        game = new ChessGame();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseReleased(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }
        });
    }

    private void handleMousePressed(MouseEvent e) {
        // Calculate which square was clicked
        int col = e.getX() / squareSize;
        int row = e.getY() / squareSize;

        // Check if there's a piece at this location
        Piece piece = game.getPieceAt(row, col);
        if (piece != null) {
            selectedRow = row;
            selectedCol = col;
            isDragging = true;
            dragX = e.getX();
            dragY = e.getY();
        }
    }

    private void handleMouseDragged(MouseEvent e) {
        if (isDragging) {
            dragX = e.getX();
            dragY = e.getY();
            repaint();
        }
    }

    private void handleMouseReleased(MouseEvent e) {
        if (isDragging) {
            // Calculate target square
            int targetCol = e.getX() / squareSize;
            int targetRow = e.getY() / squareSize;

            // Let ChessGame handle all validation (bounds, move legality, etc.)
            if (game.movePiece(selectedRow, selectedCol, targetRow, targetCol)) {
                System.out.println("Valid move!");
            } else {
                System.out.println("Invalid move - piece cannot move there");
            }

            // Reset dragging state
            isDragging = false;
            selectedRow = -1;
            selectedCol = -1;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        squareSize = Math.min(panelWidth, panelHeight) / 8;

        // Draw the chess board squares
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    g2d.setColor(new Color(240, 217, 181)); // Light square
                } else {
                    g2d.setColor(new Color(181, 136, 99));  // Dark square
                }

                g2d.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
            }
        }

        // Get board state from game logic
        Piece[][] boardState = game.getBoardState();
        
        g2d.setFont(new Font("Arial", Font.BOLD, squareSize / 2));
        FontMetrics fm = g2d.getFontMetrics();

        // Draw pieces on the board (skip the selected piece if dragging)
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = boardState[row][col];

                // Don't draw the piece being dragged here
                if (piece != null && !(isDragging && row == selectedRow && col == selectedCol)) {
                    String symbol = getPieceSymbol(piece);
                    g2d.setColor(Color.BLACK);
                    int x = col * squareSize + (squareSize - fm.stringWidth(symbol)) / 2;
                    int y = row * squareSize + ((squareSize - fm.getHeight()) / 2) + fm.getAscent();
                    g2d.drawString(symbol, x, y);
                }
            }
        }

        // Draw the piece being dragged at the mouse cursor
        if (isDragging && selectedRow >= 0 && selectedCol >= 0) {
            Piece piece = boardState[selectedRow][selectedCol];
            if (piece != null) {
                String symbol = getPieceSymbol(piece);
                g2d.setColor(Color.BLACK);
                int x = dragX - (squareSize / 4);
                int y = dragY - (squareSize / 4);
                g2d.drawString(symbol, x, y);
            }
        }
    }

    private String getPieceSymbol(Piece piece) {
        // Convert piece objects to Unicode chess symbols
        boolean isWhite = piece.isWhite(); // Adjust based on your Piece class
        
        if (piece instanceof Pawn) {
            return isWhite ? "♙" : "♟";
        } else if (piece instanceof Rook) {
            return isWhite ? "♖" : "♜";
        } else if (piece instanceof Knight) {
            return isWhite ? "♘" : "♞";
        } else if (piece instanceof Bishop) {
            return isWhite ? "♗" : "♝";
        } else if (piece instanceof Queen) {
            return isWhite ? "♕" : "♛";
        } else if (piece instanceof King) {
            return isWhite ? "♔" : "♚";
        }
        return "?";
    }
}