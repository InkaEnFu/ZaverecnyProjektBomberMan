import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bomb {
    private BufferedImage bombImage;
    private GameBoard gameBoard;

    public Bomb(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        try {
            bombImage = ImageIO.read(new File("src/Bomb.png"));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    public BufferedImage getBombImage() {
        return bombImage;
    }

    public void explode(int x, int y, int[][] scene) {
        List<Point> fireLocations = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 && j != 0) || (i != 0 && j == 0)) {
                    int newX = x + i;
                    int newY = y + j;
                    if (newX >= 0 && newX < scene[0].length && newY >= 0 && newY < scene.length) {
                        if (scene[newY][newX] == 2) {
                            scene[newY][newX] = 4;
                        }
                        fireLocations.add(new Point(newX * gameBoard.TILE_SIZE, newY * gameBoard.TILE_SIZE));
                        gameBoard.checkAndRemoveEnemy(newX, newY);
                    }
                }
            }
        }
        gameBoard.updateFireLocations(fireLocations);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                gameBoard.updateFireLocations(new ArrayList<>());
                gameBoard.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
