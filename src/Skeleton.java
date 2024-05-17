import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Skeleton implements Enemy {
    private int x;
    private int y;
    private BufferedImage image;

    public Skeleton(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            image = ImageIO.read(new File("src/Skeleton.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int tileSize) {
        g.drawImage(image, x, y, tileSize, tileSize, null);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}