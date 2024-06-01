package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Game.NextLevelFrame class represents a frame that appears when a level is cleared in the game.
 * It provides options to proceed to the next level, return to the menu, or exit the game.
 */
public class NextLevelFrame extends JFrame {
    private GameBoard gameBoard;
    private JFrame previousFrame;

    /**
     * Constructor for Game.NextLevelFrame with the specified game board and previous frame.
     * @param gameBoard     the game board associated with the game
     * @param previousFrame the previous frame from which this frame was invoked
     */
    public NextLevelFrame(GameBoard gameBoard, JFrame previousFrame) {
        this.gameBoard = gameBoard;
        this.previousFrame = previousFrame;
        setTitle("Level Cleared");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel bannerLabel = new JLabel("LEVEL CLEARED", SwingConstants.CENTER);
        bannerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        bannerLabel.setForeground(Color.BLUE);
        panel.add(bannerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton nextLevelButton = new JButton("Next Level");
        JButton menuButton = new JButton("Menu");
        JButton exitButton = new JButton("Exit");

        nextLevelButton.setFont(new Font("Arial", Font.BOLD, 24));
        menuButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));

        /**
         * Handles the action event triggered when the "Next Level" button is pressed.
         * Disposes the current frame, proceeds to the next level, and centers the player on the game board.
         * @param e the event to be processed
         */
        nextLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                gameBoard.nextLevel();
                gameBoard.setPlayerToCenter();
            }
        });

        /**
         * Handles the action event triggered when the "Menu" button is pressed.
         * Disposes the current and previous frames and opens the main menu.
         * @param e the event to be processed
         */
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                previousFrame.dispose();
                new Frame(gameBoard);
            }
        });

        /**
         * Handles the action event triggered when the "Exit" button is pressed.
         * Exits the application.
         * @param e the event to be processed
         */
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(nextLevelButton);
        buttonPanel.add(menuButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        add(panel);
        setVisible(true);
    }
}
