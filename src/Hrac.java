import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Hrac implements KeyListener {
    private int x;
    private int y;
    private int rychlost;
    private HerniPole herniPole;
    private int deltaX = 0;
    private int deltaY = 0;
    private BufferedImage postavaImage;
    private BufferedImage bombaImage;
    private BufferedImage ohenImage;
    private boolean zobrazBomby = false;
    private int bombaX = -1;
    private int bombaY = -1;
    private boolean zobrazOhen = false;
    private List<Point> ohnovePozice = new ArrayList<>();

    public Hrac(HerniPole herniPole) {
        this.herniPole = herniPole;
        x = (herniPole.POCET_SLOUPCU / 2) * herniPole.VELIKOST_KOSTICKY;
        y = (herniPole.POCET_RADKU / 2) * herniPole.VELIKOST_KOSTICKY;
        rychlost = herniPole.VELIKOST_KOSTICKY;
        herniPole.addKeyListener(this);

        try {
            postavaImage = ImageIO.read(new File("src/Postava.png"));
            bombaImage = ImageIO.read(new File("src/Bomba.png"));
            ohenImage = ImageIO.read(new File("src/Ohen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(this::pohyb);
        thread.start();
    }

    public void vykreslit(Graphics g) {
        if (postavaImage != null) {
            g.drawImage(postavaImage, x, y, herniPole.VELIKOST_KOSTICKY, herniPole.VELIKOST_KOSTICKY, null);
        }
            if (zobrazBomby && bombaImage != null) {
                if (bombaX != -1 && bombaY != -1) {
                    g.drawImage(bombaImage, bombaX - bombaImage.getWidth() / 2, bombaY - bombaImage.getHeight() / 2, null);
                }
            }
        if (zobrazOhen && ohenImage != null) {
            for (Point pozice : ohnovePozice) {
                g.drawImage(ohenImage, pozice.x - ohenImage.getWidth() / 2, pozice.y - ohenImage.getHeight() / 2, null);
            }
        }
        }

    private void pohyb() {
        while (true) {
            int novaX = x + (deltaX != 0 ? (deltaX / Math.abs(deltaX)) * herniPole.VELIKOST_KOSTICKY : 0);
            int novaY = y + (deltaY != 0 ? (deltaY / Math.abs(deltaY)) * herniPole.VELIKOST_KOSTICKY : 0);

            if (novaX >= 0 && novaX <= (herniPole.POCET_SLOUPCU - 1) * herniPole.VELIKOST_KOSTICKY &&
                    novaY >= 0 && novaY <= (herniPole.POCET_RADKU - 1) * herniPole.VELIKOST_KOSTICKY &&
                    herniPole.getMapa().getPolicko(novaX / herniPole.VELIKOST_KOSTICKY, novaY / herniPole.VELIKOST_KOSTICKY) == 0) {
                x = novaX;
                y = novaY;
                herniPole.repaint();
            }

            try {
                Thread.sleep(130);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            case KeyEvent.VK_SPACE:
                if(!zobrazBomby){
                    zobrazBomby = true;
                    bombaX = (x / herniPole.VELIKOST_KOSTICKY) * herniPole.VELIKOST_KOSTICKY + herniPole.VELIKOST_KOSTICKY / 2;
                    bombaY = (y / herniPole.VELIKOST_KOSTICKY) * herniPole.VELIKOST_KOSTICKY + herniPole.VELIKOST_KOSTICKY / 2;
                    new Thread(() -> {
                        try {
                            Thread.sleep(3000);
                            zobrazBomby = false;
                            herniPole.repaint();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }
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
