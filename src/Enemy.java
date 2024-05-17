import java.awt.*;

public interface Enemy {
    void draw(Graphics g, int x, int y, int tileSize);
    int getX();
    int getY();
}
