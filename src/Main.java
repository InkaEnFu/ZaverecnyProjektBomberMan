import javax.swing.*;
/**
 * The Main class serves as the entry point for the application.
 * It initializes the GUI by creating a new Frame with a GameBoard.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Frame(new GameBoard()));
    }
}
