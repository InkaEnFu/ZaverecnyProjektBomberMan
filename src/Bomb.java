import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * This class represents a bomb in the game. It handles bomb placement,
 * explosion logic, and updating the game state accordingly.
 */

public class Bomb {
    private BufferedImage bombImage;
    private GameBoard gameBoard;
    private int x;
    private int y;
    Random random = new Random();

    /**
     * Constructor for the Bomb class. It initializes the bomb image and the game board.
     */
    public Bomb(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        try {
            URL bombImages = this.getClass().getResource("/Images/Bomb.png/");
            bombImage = ImageIO.read(Objects.requireNonNull(bombImages));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    /**
     * Causes the bomb to explode at the specified position, updating the game scene
     * accordingly.
     * The explosion will destroy tiles with index 2.
     * @param x     the x-coordinate of the explosion
     * @param y     the y-coordinate of the explosion
     * @param scene the game scene represented as a 2D array
     */
    public void explode(int x, int y, int[][] scene) {
        List<Point> fireLocations = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 && j != 0) || (i != 0 && j == 0)) {
                    int newX = x + i;
                    int newY = y + j;
                    if (newX >= 0 && newX < scene[0].length && newY >= 0 && newY < scene.length) {
                        if (scene[newY][newX] == 2) {
                            scene[newY][newX] = 0;
                            if (!gameBoard.boostSpawned && random.nextFloat() < 0.2) {
                                scene[newY][newX] = 4;
                                gameBoard.boostSpawned = true;
                            }
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
    /**
     * Sets the position of the bomb.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the bomb.
     * @return The x-coordinate of the bomb
     */

    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the bomb.
     * @return The y-coordinate of the bomb
     */

    public int getY() {
        return y;
    }
}