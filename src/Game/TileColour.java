package Game;

import java.awt.*;

/**
 * An Enum representing different tile colors.
 */
enum TileColour {
    GREEN(Color.GREEN),
    GRAY(Color.GRAY),
    BROWN(new Color(186, 102, 39));

    private final Color colour;

    /**
     * Constructor for a new Game.TileColour with the specified color.
     * @param colour the color of the tile
     */

    TileColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Gets the color of the tile.
     * @return the color of the tile
     */
    public Color getColour() {
        return colour;
    }

}

