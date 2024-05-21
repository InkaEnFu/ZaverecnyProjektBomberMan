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
                JOptionPane.showMessageDialog(Frame.this,"TODO");
            }
        });

        storyButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(Frame.this,"TODO");
            }
        });
        panel.add(playButton);
        panel.add(controlButton);
        panel.add(storyButton);

        add(panel);
        setVisible(true);


    }
}