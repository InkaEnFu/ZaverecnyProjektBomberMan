import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import javax.swing.Timer;

public class Skeleton implements Enemy {
    private int x;
    private int y;
    private BufferedImage skeletonImage;
    private BufferedImage trapImage;
    private List<Point> trapLocations;
    private Timer abilityTimer;
    private Random random;
    private GameBoard gameBoard;

    public Skeleton(int x, int y, GameBoard gameBoard) {
        this.x = x;
        this.y = y;
        this.random = new Random();
        this.trapLocations = new ArrayList<>();
        this.gameBoard = gameBoard;
        try {
            skeletonImage = ImageIO.read(new File("src/Skeleton.png"));
            trapImage = ImageIO.read(new File("src/Trap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        abilityTimer = new javax.swing.Timer(5000, e -> ability());
        abilityTimer.start();
    }

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




    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

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

    @Override
    public void ability() {
        placeTrap();
    }
    private void placeTrap(){
        int tileSize = GameBoard.TILE_SIZE;
        Point trapLocation = new Point(x, y);
        trapLocations.add(trapLocation);
        gameBoard.repaint();
    }
    public List<Point> getTrapLocations(){
        return trapLocations;
    }

}
