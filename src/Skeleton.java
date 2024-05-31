import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.List;
import javax.swing.Timer;

/**
 * The Skeleton class represents an enemy character in the game.
 * It implements the Enemy interface and provides functionality for movement, drawing, and special abilities.
 */
public class Skeleton implements Enemy {
    private int x;
    private int y;
    private BufferedImage skeletonImage;
    private BufferedImage trapImage;
    private List<Point> trapLocations;
    private Timer abilityTimer;
    private Random random;
    private GameBoard gameBoard;

    /**
     * Constructs a Skeleton at the specified coordinates on the given game board.
     * @param x         the initial x-coordinate of the Skeleton
     * @param y         the initial y-coordinate of the Skeleton
     * @param gameBoard the game board on which the Skeleton exists
     */
    public Skeleton(int x, int y, GameBoard gameBoard) {
        this.x = x;
        this.y = y;
        this.random = new Random();
        this.trapLocations = new ArrayList<>();
        this.gameBoard = gameBoard;
        try {
            URL skeletonImages = this.getClass().getResource("/Images/Skeleton.png");
            skeletonImage = ImageIO.read(Objects.requireNonNull(skeletonImages));
            URL trapImages = this.getClass().getResource("/Images/Trap.png");
            trapImage = ImageIO.read(Objects.requireNonNull(trapImages));
        } catch (IOException e) {
            e.printStackTrace();
        }
        abilityTimer = new javax.swing.Timer(5000, e -> ability());
        abilityTimer.start();
    }

    /**
     * Draws the Skeleton and its traps on the game board.
     * @param g         the Graphics object used for drawing
     * @param x         the x-coordinate at which to draw the Skeleton
     * @param y         the y-coordinate at which to draw the Skeleton
     * @param tileSize  the size of the tiles on the game board
     */
    @Override
    public void draw(Graphics g, int x, int y, int tileSize) {
        if (skeletonImage != null){
            g.drawImage(skeletonImage, x, y, tileSize, tileSize, null);
    }
    if(trapImage !=null)

    {
        for (Point trapLocation : trapLocations) {
            g.drawImage(trapImage, trapLocation.x, trapLocation.y, tileSize, tileSize, null);
        }
    }
}
    /**
     * Moves the skeleton to a new position based on a random direction.
     * The skeleton will not move if the new position is out of bounds or is occupied by a bomb / tile with index 1;2;3;4;.
     * The skeleton will move to the new position only if it is occupied by a tile with index 0.
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
     * Triggers the Skeleton's special ability, which is to place a trap on the game board.
     */
    @Override
    public void ability() {
        placeTrap();
    }
    /**
     * Places a trap on the game board at the Skeleton's current position.
     */
    private void placeTrap(){
        int tileSize = GameBoard.TILE_SIZE;
        Point trapLocation = new Point(x, y);
        trapLocations.add(trapLocation);
        gameBoard.repaint();
    }
    /**
     * Returns the locations of the traps placed by the Skeleton.
     * @return  the locations of the traps
     */
    public List<Point> getTrapLocations(){
        return trapLocations;
    }


    /**
     * Gets the x-coordinate of the Skeleton.
     * @return the x-coordinate of the Skeleton
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the Skeleton.
     * @return the y-coordinate of the Skeleton
     */
    @Override
    public int getY() {
        return y;
    }

}
