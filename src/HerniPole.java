import javax.swing.*;
import java.awt.*;

public class HerniPole extends JPanel {
    public static final int POCET_RADKU = 15;
    public static final int POCET_SLOUPCU = 15;
    public static final int VELIKOST_KOSTICKY = 49;

    public HerniPole() {

        setPreferredSize(new Dimension(POCET_SLOUPCU * VELIKOST_KOSTICKY, POCET_RADKU * VELIKOST_KOSTICKY));
    }
}