import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Hrac implements KeyListener {
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
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                deltaY = -herniPole.VELIKOST_KOSTICKY;
                deltaX = 0;
                break;
            case KeyEvent.VK_S:
                deltaY = herniPole.VELIKOST_KOSTICKY;
                deltaX = 0;
                break;
            case KeyEvent.VK_A:
                deltaX = -herniPole.VELIKOST_KOSTICKY;
                deltaY = 0;
                break;
            case KeyEvent.VK_D:
                deltaX = herniPole.VELIKOST_KOSTICKY;
                deltaY = 0;
                break;

        }
        herniPole.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                deltaY = 0;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                deltaX = 0;
                break;
        }
    }








}
