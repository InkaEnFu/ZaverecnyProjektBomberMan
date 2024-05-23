import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Dragon implements Enemy {
    private int x;
    private int y;
    private BufferedImage image;
    private Random random;
    private GameBoard gameBoard;


    public Dragon(int x, int y, GameBoard gameBoard) {
        this.x = x;
        this.y = y;
        this.random = new Random();
        this.gameBoard = gameBoard;
        try {
            image = ImageIO.read(new File("src/Dragon.png"));
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
    @Override
    public void movement() {

        int moveDirection = random.nextInt(4);
        int tileSize = GameBoard.TILE_SIZE;
        int newX = x;
        int newY = y;

        switch (moveDirection) {
            case 0:
                newY = y - tileSize;
                break;
            case 1:
                newY = y + tileSize;
                break;
            case 2:
                newX = x - tileSize;
                break;
            case 3:
                newX = x + tileSize;
                break;
        }

        if (newX >= 0 && newX < GameBoard.COLUMN_COUNT * tileSize &&
                newY >= 0 && newY < GameBoard.ROW_COUNT * tileSize &&
                gameBoard.getMap().getTile(newX / tileSize, newY / tileSize) == 0){

            x = newX;
            y = newY;
        }
    }

    @Override
    public void ability() {

    }
}

