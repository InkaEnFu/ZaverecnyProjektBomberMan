import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bomba {
    private BufferedImage bombaImage;

    public Bomba() {
        try {
            bombaImage = ImageIO.read(new File("Bomba.png"));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    public BufferedImage getBombaImage() {
        return bombaImage;
    }
}
