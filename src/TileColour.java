import java.awt.*;

enum TileColour {
    GREEN(Color.GREEN),
    GRAY(Color.GRAY),
    BROWN(new Color(186, 102, 39));

    private final Color colour;

    TileColour(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

}

