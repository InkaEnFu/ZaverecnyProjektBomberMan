import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWonFrame extends JFrame {
    private GameBoard gameBoard;
    private JFrame previousFrame;

    public GameWonFrame(GameBoard gameBoard, JFrame previousFrame) {
        this.gameBoard = gameBoard;
        this.previousFrame = previousFrame;
        setTitle("Game Won");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JLabel bannerLabel = new JLabel("GAME WON", SwingConstants.CENTER);
        bannerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        bannerLabel.setForeground(Color.GREEN);
        panel.add(bannerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton playAgainButton = new JButton("Play Again");
        JButton menuButton = new JButton("Menu");
        JButton exitButton = new JButton("Exit");

        playAgainButton.setFont(new Font("Arial", Font.BOLD, 24));
        menuButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                previousFrame.dispose();
                GameBoard.doGui();
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

        buttonPanel.add(playAgainButton);
        buttonPanel.add(menuButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        add(panel);
        setVisible(true);
    }
}