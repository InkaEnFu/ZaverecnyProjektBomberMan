import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    private GameBoard gameBoard;
    public Frame(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(gameOverLabel, BorderLayout.CENTER);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                gameBoard.getMainFrame().dispose();
                Main.main(new String[0]);
            }
        });
        panel.add(playAgainButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

}