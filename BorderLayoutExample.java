
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

public class BorderLayoutExample {

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // 1. Create the main application frame
        JFrame frame = new JFrame("BorderLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The content pane of a JFrame uses BorderLayout by default,
        // so calling setLayout() is optional but done here for clarity.
        frame.setLayout(new BorderLayout());

        // 2. Create components (JButtons in this case)
        chessBoard board = new chessBoard();

        // 3. Add components to the frame using BorderLayout constraints
        // Components in North and South are stretched horizontally.
        frame.add(board);

        // The center component gets all available extra space and stretches both ways.
        // Optional: Set preferred sizes for visibility/demonstration purposes
        board.setPreferredSize(new Dimension(500, 500));

        // 4. Size the frame to fit the preferred size of all components
        frame.pack();

        // 5. Make the window visible
        frame.setVisible(true);
    }

}
