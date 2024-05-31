import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * The Slime class represents a type of enemy in the game.
 * Slime enemies have random movement patterns and can split into smaller slimes.
 */
public class Slime implements Enemy {
    private int x;
    private int y;
    private BufferedImage slimeImage;
    private Random random;
    private GameBoard gameBoard;
    private boolean hasSplit;

    private boolean original;

    /**
     * Constructor for a new Slime object with the specified position and game board.
     * @param x         The x-coordinate of the slime's initial position
     * @param y         The y-coordinate of the slime's initial position
     * @param gameBoard The game board where the slime exists
     * @param original  Indicates whether this slime is an original instance
     */
    public Slime(int x, int y, GameBoard gameBoard, boolean original) {
        this.x = x;
        this.y = y;
        this.random = new Random();
        this.gameBoard = gameBoard;
        this.hasSplit = false;
        this.original = original;
        try {
            URL slimeImages = this.getClass().getResource("/Images/Slime.png");
            slimeImage = ImageIO.read(Objects.requireNonNull(slimeImages));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the slime on the given graphics context at the specified position and size.
     * @param g        The graphics context on which to draw the slime
     * @param x        The x-coordinate at which to draw the slime
     * @param y        The y-coordinate at which to draw the slime
     * @param tileSize The size of each tile on the game board
     */
    @Override
    public void draw(Graphics g, int x, int y, int tileSize) {
        g.drawImage(slimeImage, x, y, tileSize, tileSize, null);
    }

    /**
     * Moves the slime to a new position based on a random direction.
     * The slime will not move if the new position is out of bounds or is occupied by a bomb / tile with index 1;2;3;4;.
     * The slime will move to the new position only if it is occupied by a tile with index 0.
     */
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
    /**
     * Implements the ability of the slime.
     * The slime will split into two otheere slimes if it has not already split.
     * The new slimes will be spawned at random locations on the game board.
     * The ability can only be used by the original slime.
     */
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

    /**
     * Retrieves the current x-coordinate of the slime.
     * @return The x-coordinate of the slime
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Retrieves the current y-coordinate of the slime.
     * @return The y-coordinate of the slime
     */
    @Override
    public int getY() {
        return y;
    }

}