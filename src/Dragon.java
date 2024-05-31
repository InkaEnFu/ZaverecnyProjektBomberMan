import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.swing.Timer;

/**
 * This class represents a dragon enemy in the game. It handles
 * drawing the dragon, its movement, and its special ability to shoot fireballs.
 */
public class Dragon implements Enemy {
    private int x;
    private int y;
    private GameBoard gameBoard;
    private BufferedImage dragonImage;
    private BufferedImage fireBallImage;
    public boolean showFireBall = false;
    private Timer abilityTimer;
    private Random random;
    private List<Point> fireBallLocations;

    /**
     * Constructor for the Dragon class with the specified position and game board.
     * @param x         the x-coordinate of the dragon
     * @param y         the y-coordinate of the dragon
     * @param gameBoard the gameboard on which the dragon exists
     */
    public Dragon(int x, int y, GameBoard gameBoard) {
        this.x = x;
        this.y = y;
        this.random = new Random();
        this.gameBoard = gameBoard;
        this.fireBallLocations = new ArrayList<>();
        try {
            dragonImage =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Dragon.png")));
            fireBallImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/FireBall.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        abilityTimer = new Timer(5000, e -> ability());
        abilityTimer.start();
    }

    /**
     * Draws the dragon and its fireballs on the game board.
     * @param g         the graphic object to draw on
     * @param x         the x-coordinate for drawing
     * @param y         the y-coordinate for drawing
     * @param tileSize  the size of the tile to draw the dragon and fireballs
     */
    @Override
    public void draw(Graphics g, int x, int y, int tileSize) {
        if (dragonImage != null){
        g.drawImage(dragonImage, x, y, tileSize, tileSize, null);
    }
        if (showFireBall && fireBallImage != null) {
            int offsetX = (tileSize - fireBallImage.getWidth()) / 2;
            int offsetY = (tileSize - fireBallImage.getHeight()) / 2;
            fireBallLocations.clear();
            fireBallLocations.add(new Point(x + offsetX, y - tileSize + offsetY));
            fireBallLocations.add(new Point(x + offsetX, y + tileSize + offsetY));
            fireBallLocations.add(new Point(x - tileSize + offsetX, y + offsetY));
            fireBallLocations.add(new Point(x + tileSize + offsetX, y + offsetY));
            for (Point p : fireBallLocations) {
                g.drawImage(fireBallImage, p.x, p.y, tileSize, tileSize, null);
            }
        }
    }

    /**
     * Moves the dragon to a new position based on a random direction.
     * The dragon will not move if the new position is out of bounds or is occupied by a bomb / tile with index 1;2;3;4;.
     * The dragon will move to the new position only if it is occupied by a tile with index 0.
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
     * Triggers the dragon to show fireballs, updating the game board.
     * The fireballs will be shown for 2 seconds before disappearing.
     * The fireballs will be shown in the 4 cardinal directions around the dragon.
     */
    private void triggerFireBall() {
        showFireBall = true;
        gameBoard.repaint();
        Timer timer = new Timer(2000, e -> {
            showFireBall = false;
            gameBoard.repaint();
        });

        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Activates the dragon's special ability, which triggers fireballs.
     */
    @Override
    public void ability() {
        triggerFireBall();

    }

    /**
     * Gets the list of fireball locations.
     * @return a list of points representing fireball locations
     */
    public List<Point> getFireBallLocations() {
        return fireBallLocations;
    }

    /**
     * Gets the x-coordinate of the dragon.
     * @return the x-coordinate of the dragon
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the dragon.
     * @return the y-coordinate of the dragon
     */
    @Override
    public int getY() {
        return y;
    }
}

