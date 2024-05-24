import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Slime implements Enemy {
    private int x;
    private int y;
    private BufferedImage image;
    private Random random;
    private GameBoard gameBoard;
    private boolean hasSplit;

    private boolean original;

    public Slime(int x, int y, GameBoard gameBoard, boolean original) {
        this.x = x;
        this.y = y;
        this.random = new Random();
        this.gameBoard = gameBoard;
        this.hasSplit = false;
        this.original = original;
        try {
            image = ImageIO.read(new File("src/Slime.png"));
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
                gameBoard.getMap().getTile(newX / tileSize, newY / tileSize) == 0 &&
                !gameBoard.isBombAt(newX / tileSize, newY / tileSize)) {

            x = newX;
            y = newY;
        }
    }
    @Override
    public void ability() {
        if (!hasSplit && original) {
            hasSplit = true;
            List<Point> freeTiles = new ArrayList<>();
            for (int row = 0; row < GameBoard.ROW_COUNT; row++) {
                for (int col = 0; col < GameBoard.COLUMN_COUNT; col++) {
                    if (gameBoard.getMap().getTile(col, row) == 0) {
                        freeTiles.add(new Point(col * GameBoard.TILE_SIZE, row * GameBoard.TILE_SIZE));
                    }
                }
            }

            for (int i = 0; i < 2; i++) {
                if (!freeTiles.isEmpty()) {
                    Point spawnPoint = freeTiles.remove(random.nextInt(freeTiles.size()));
                    gameBoard.addNewEnemy(new Slime(spawnPoint.x, spawnPoint.y, gameBoard, false));
                }
            }
        }
    }
}