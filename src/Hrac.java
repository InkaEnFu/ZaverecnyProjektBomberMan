import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Hrac  {
    private int x;
    private int y;
    private int rychlost;
    private HerniPole herniPole;
    private int deltaX = 0;
    private int deltaY = 0;
    private BufferedImage postavaImage;

    public void vykreslit(Graphics g) {
        if (postavaImage != null) {
            g.drawImage(postavaImage, x, y, herniPole.VELIKOST_KOSTICKY, herniPole.VELIKOST_KOSTICKY, null);
        }
    }





}
