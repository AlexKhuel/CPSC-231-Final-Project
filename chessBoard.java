/*
CPSC-231 Final Project
Chess Board
By: Jake Puebla, Joshua Dowd, Nate Smith, Jerry Salas

Date Completed: December 14, 2025
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

/**
 * A JPanel that displays and manages an interactive chess board.
 * 
 * This class handles the graphical representation of the chess game,
 * including piece rendering, drag-and-drop functionality, and visual
 * feedback for game state. It processes mouse events to allow players
 * to move pieces by clicking and dragging.
 */
public class chessBoard extends JPanel {

    /**
     * The underlying game logic and board state
     */
    @SuppressWarnings("FieldMayBeFinal")
    private Board game;
    
    /**
     * The row of the currently selected piece (-1 if no piece is selected)
     */
    private int selectedRow = -1;
    
    /**
     * The column of the currently selected piece (-1 if no piece is selected)
     */
    private int selectedCol = -1;
    
    /**
     * The current x-coordinate of the mouse during dragging
     */
    private int dragX = 0;
    
    /**
     * The current y-coordinate of the mouse during dragging
     */
    private int dragY = 0;
    
    /**
     * Indicates whether a piece is currently being dragged
     */
    private boolean isDragging = false;
    
    /**
     * The size of each square on the chess board in pixels
     */
    private int squareSize = 0;

    /**
     * Constructs a new chess board panel with an initialized game.
     * Sets up mouse listeners for drag-and-drop piece movement.
     */
    public chessBoard() {
        game = new Board();

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

    /**
     * Handles mouse press events to initiate piece selection and dragging.
     * Determines which square was clicked and begins dragging if a piece
     * is present at that location.
     *
     * @param e the MouseEvent containing press location information
     */
    private void handleMousePressed(MouseEvent e) {
        // Calculate which square was clicked
        int col = e.getX() / squareSize;
        int row = e.getY() / squareSize;

        // Check if there's a piece at this location
        Piece piece = game.board[row][col];
        if (piece != null) {
            selectedRow = row;
            selectedCol = col;
            isDragging = true;
            dragX = e.getX();
            dragY = e.getY();
        }
    }

    /**
     * Handles mouse drag events to update the position of the piece being dragged.
     * Updates the drag coordinates and triggers a repaint to show the piece
     * following the cursor.
     *
     * @param e the MouseEvent containing current mouse position
     */
    private void handleMouseDragged(MouseEvent e) {
        if (isDragging) {
            dragX = e.getX();
            dragY = e.getY();
            repaint();
        }
    }

    /**
     * Handles mouse release events to complete a move attempt.
     * Calculates the target square, validates the move through the game logic,
     * updates the turn if the move is legal, and resets the dragging state.
     *
     * @param e the MouseEvent containing release location information
     */
    private void handleMouseReleased(MouseEvent e) {
        if (isDragging) {
            // Calculate target square
            int targetCol = e.getX() / squareSize;
            int targetRow = e.getY() / squareSize;

            // Let ChessGame handle all validation (bounds, move legality, etc.)
            System.out.println("Going into if statements");
            if (game.isCheckmate()) {
                System.out.println("Is checkmate");
                repaint();

            } else if (game.move(selectedRow, selectedCol, targetRow, targetCol)) {
                System.out.println("Valid move!");
                game.changeTurn();
            } else {
                System.out.println("Invalid move - piece cannot move there");
                System.out.println("Move Tried: selectedRow: " + selectedRow + "; selectedCol: " + selectedCol);
                System.out.println("             targetRow: " + targetRow + "; targetCol: " + targetCol);
                System.out.println("Board[selectedRow][selectedCol]: " + game.board[selectedRow][selectedCol]);
            }

            // Reset dragging state
            selectedRow = -1;
            selectedCol = -1;
            repaint();
        }
    }

    /**
     * Paints the chess board, pieces, and game status.
     * Draws the checkerboard pattern, renders all pieces in their current
     * positions (except the piece being dragged), shows the dragged piece
     * at the cursor position, and displays game over status if applicable.
     *
     * @param g the Graphics context for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        squareSize = Math.min(panelWidth, panelHeight) / 8;

        String gameStatus = getGameStatus();
        boolean gameOver = gameStatus.contains("Wins!");

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
        Piece[][] boardState = game.board;

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

        if (gameOver) {
            // Semi-transparent overlay
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, panelWidth, panelHeight);

            // Draw winner message
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            g2d.setColor(Color.WHITE);
            fm = g2d.getFontMetrics();

            String message = gameStatus;
            int x = (panelWidth - fm.stringWidth(message)) / 2;
            int y = (panelHeight - fm.getHeight()) / 2 + fm.getAscent();

            g2d.drawString(message, x, y);
        }
    }

    /**
     * Converts a Piece object to its corresponding Unicode chess symbol.
     *
     * @param piece the piece to convert
     * @return the Unicode character representing the piece (white or black variant)
     */
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

    /**
     * Determines the current game status.
     * Returns a message indicating checkmate (with the winner) or the
     * current player's turn.
     *
     * @return a String describing the current game state
     */
    public String getGameStatus() {
        if (game.isCheckmate()) {
            return game.whiteTurn ? "Black Wins!" : "White Wins!";
        }
        // King currking = game.getKing(game.whiteTurn);
        // if (currking.isInCheck(game, currking.getRow(), currking.getCol())) {
        //      return "Check!";
        // }
        return game.whiteTurn ? "White's Turn" : "Black's Turn";
    }
}