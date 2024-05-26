import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

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



    public Dragon(int x, int y, GameBoard gameBoard) {
        this.x = x;
        this.y = y;
        this.random = new Random();
        this.gameBoard = gameBoard;
        this.fireBallLocations = new ArrayList<>();
        try {
            dragonImage = ImageIO.read(new File("src/Dragon.png"));
            fireBallImage = ImageIO.read(new File("src/FireBall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        abilityTimer = new Timer(5000, e -> ability());
        abilityTimer.start();
    }

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
        triggerFireBall();

    }
    public List<Point> getFireBallLocations() {
        return fireBallLocations;
    }

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
}

