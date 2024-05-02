import javax.swing.*;
import java.awt.*;

public class HerniPole extends JPanel {
    public static final int POCET_RADKU = 15;
    public static final int POCET_SLOUPCU = 17;
    public static final int VELIKOST_KOSTICKY = 49;

    public HerniPole() {
        setPreferredSize(new Dimension(POCET_SLOUPCU * VELIKOST_KOSTICKY, POCET_RADKU * VELIKOST_KOSTICKY));
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        g.drawRect(0, 0, POCET_SLOUPCU * VELIKOST_KOSTICKY, POCET_RADKU * VELIKOST_KOSTICKY);

        for (int row = 0; row < POCET_RADKU; row++) {
            for (int col = 0; col < POCET_SLOUPCU; col++) {
                int x = col * VELIKOST_KOSTICKY;
                int y = row * VELIKOST_KOSTICKY;

                if (row == 0 || col == 0 || row == POCET_RADKU - 1 || col == POCET_SLOUPCU - 1) {
                    g.setColor(Color.GRAY);
                    g.fillRect(x, y, VELIKOST_KOSTICKY, VELIKOST_KOSTICKY);
                }
                else if (row == 1 || col == 1 || row == POCET_RADKU - 2 || col == POCET_SLOUPCU - 2) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x, y, VELIKOST_KOSTICKY, VELIKOST_KOSTICKY);
                }

                else {

                    if (row % 2 == 0 && col % 2 == 0) {
                        g.setColor(Color.GRAY);
                        g.fillRect(x, y, VELIKOST_KOSTICKY, VELIKOST_KOSTICKY);
                    } else {

                        g.setColor(Color.GREEN);
                        g.fillRect(x, y, VELIKOST_KOSTICKY, VELIKOST_KOSTICKY);
                    }
                }
            }
        }
    }
    public static void vytvorAProvedGUI() {
        JFrame frame = new JFrame("Herní pole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HerniPole herniPole = new HerniPole();
        frame.add(herniPole);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
