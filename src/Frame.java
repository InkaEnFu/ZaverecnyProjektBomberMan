import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private GameBoard gameBoard;
    public Frame(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        setTitle("BombreMan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton playButton = new JButton("Play");
        JButton controlButton = new JButton("Controls");
        JButton storyButton = new JButton("Story");

        playButton.setFont(new Font("Arial", Font.BOLD, 24));
        controlButton.setFont(new Font("Arial", Font.BOLD, 24));
        storyButton.setFont(new Font("Arial", Font.BOLD, 24));

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                gameBoard.doGui();
            }
        });

        controlButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(Frame.this,
                        "Controls:\n" +
                                "Space - Place bomb\n" +
                                "W - Go Up\n" +
                                "S - Go Down\n" +
                                "A - Go Left\n" +
                                "D - Go Right");
            }
        });

            storyButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    JOptionPane.showMessageDialog(Frame.this,"Trapped in a maze, a robot named BomberMan seeks freedom.\n" +
                            "But three levels stand in his way, each filled with enemies.\n" +
                            "To escape, he must outsmart them all, using bombs as his key to break through obstacles.\n" +
                            "Will he succeed, or will the maze keep him captive?\n" +
                            "That is only up to you!");
                }
            });
        panel.add(playButton);
        panel.add(controlButton);
        panel.add(storyButton);

        add(panel);
        setVisible(true);


    }
}