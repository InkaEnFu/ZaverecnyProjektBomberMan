import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextLevelFrame extends JFrame {
    private GameBoard gameBoard;
    private JFrame previousFrame;

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

        nextLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                gameBoard.nextLevel();
                gameBoard.setPlayerToCenter();
            }
        });

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                previousFrame.dispose();
                new Frame(gameBoard);
            }
        });

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
