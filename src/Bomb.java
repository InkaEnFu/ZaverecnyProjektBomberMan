import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bomb {
    private BufferedImage bombImage;

    public Bomb() {
        try {
            bombImage = ImageIO.read(new File("Bomb.png"));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    public BufferedImage getBombImage() {
        return bombImage;
    }
}
