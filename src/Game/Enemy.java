package Game;

import java.awt.*;

/**
 * The Game.Enemy interface defines the methods that all enemy entities
 * in the game must implement. It includes methods for drawing the enemy,
 * handling movement, executing special abilities, and getting the enemy's position.
 */
public interface Enemy {
    /**
     * Draws the enemy on the game board.
     * @param g        the graphic object to draw on
     * @param x        the x-coordinate for drawing
     * @param y        the y-coordinate for drawing
     * @param tileSize the size of the tile to draw the enemy
     */
    void draw(Graphics g, int x, int y, int tileSize);

    /**
     * Moves the enemy on the game board.
     */
    void movement();

    /**
     * Executes the enemy's special ability.
     */
    void ability();

    /**
     * Gets the x-coordinate of the enemy.
     * @return the x-coordinate of the enemy
     */
    int getX();

    /**
     * Gets the y-coordinate of the enemy.
     * @return the y-coordinate of the enemy
     */
    int getY();
}
