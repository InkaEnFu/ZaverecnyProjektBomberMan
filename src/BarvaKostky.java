
import java.awt.*;

enum BarvaKostky {
    ZELENA(Color.GREEN),
    SEDE(Color.GRAY),
    HNEDA(new Color(186, 102, 39));

    private final Color barva;

    BarvaKostky(Color barva) {
        this.barva = barva;
    }

    public Color getBarva() {
        return barva;
    }

}

