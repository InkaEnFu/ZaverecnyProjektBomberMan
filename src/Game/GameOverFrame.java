package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Game.GameOverFrame class represents a frame displayed when the game is over.
 * It provides options for the player to play again, return to the menu, or exit the game.
 */
public class GameOverFrame extends JFrame {
    private GameBoard gameBoard;
    private JFrame previousFrame;

    /**
     * Constructor for a Game.GameOverFrame.
     * @param gameBoard     the game board associated with the game
     * @param previousFrame the previous frame to be disposed when this frame is displayed
     */
    public GameOverFrame(GameBoard gameBoard, JFrame previousFrame) {
        this.gameBoard = gameBoard;
        this.previousFrame = previousFrame;
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel bannerLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        bannerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        bannerLabel.setForeground(Color.RED);
        panel.add(bannerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton playAgainButton = new JButton("Play Again");
        JButton menuButton = new JButton("Menu");
        JButton exitButton = new JButton("Exit");

        playAgainButton.setFont(new Font("Arial", Font.BOLD, 24));
        menuButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));

        /**
         * ActionListener for the playAgainButton.
         * Disposes the current frame and the previous frame, restarts the current level on the game board,
         * and makes the main frame of the game board visible.
         * @param e The action event
         */
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                previousFrame.dispose();
                gameBoard.restartCurrentLevel();
                gameBoard.mainFrame.setVisible(true);
            }
        });

        /**
         * ActionListener for the menuButton.
         * Disposes the current frame and the previous frame, and creates a new Game.Frame using the game board.
         * @param e The action event
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
         * ActionListener for the exitButton.
         * Exits the game.
         * @param e The action event
         */
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(menuButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        add(panel);
        setVisible(true);
    }
}