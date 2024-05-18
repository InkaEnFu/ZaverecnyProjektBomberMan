import java.awt.*;

public interface Enemy {
    void draw(Graphics g, int x, int y, int tileSize);
    void movement();
    int getX();
    int getY();
}
