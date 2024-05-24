import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends JPanel {
    public static final int ROW_COUNT = 15;
    public static final int COLUMN_COUNT = 17;
    public static final int TILE_SIZE = 49;

    private Player player;
    private Map map;
    private Bomb bomb;
    private BufferedImage playerImage;
    private BufferedImage boostImage;

    private List<Enemy> enemies;

    private List<Point> fireLocations;

    private JFrame mainFrame;
    private List<Enemy> newEnemies = new ArrayList<>();


    public GameBoard() {
        map = new Map();
        bomb = new Bomb(this);
        enemies = new ArrayList<>();
        fireLocations = new ArrayList<>();
        setPreferredSize(new Dimension(COLUMN_COUNT * TILE_SIZE, ROW_COUNT * TILE_SIZE));
        try {
            playerImage = ImageIO.read(new File("src/Player.png"));
            boostImage = ImageIO.read(new File("src/Boost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect(0, 0, COLUMN_COUNT * TILE_SIZE, ROW_COUNT * TILE_SIZE);

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COLUMN_COUNT; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                int policko = map.getTile(col, row);
                Color barva;

                if (policko == 1) {
                    barva = TileColour.GRAY.getColour();
                } else if (policko == 2) {
                    barva = Color.BLACK;
                } else if (policko == 3) {
                    barva = TileColour.BROWN.getColour();
                } else if (policko == 4) {
                    g.drawImage(boostImage, x, y, TILE_SIZE, TILE_SIZE, null);
                    continue;
                } else {
                    barva = TileColour.GREEN.getColour();
                }

                g.setColor(barva);
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }

        if (player != null) {
            player.print(g);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(g, enemy.getX(), enemy.getY(), TILE_SIZE);
        }
    }
    public void spawnEnemies() {
        Random random = new Random();

        List<Point> freeTiles = new ArrayList<>();
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COLUMN_COUNT; col++) {
                if (map.getTile(col, row) == 0) {
                    freeTiles.add(new Point(col * TILE_SIZE, row * TILE_SIZE));
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (freeTiles.isEmpty()) {
                break;
            }
            Point spawnPoint = freeTiles.remove(random.nextInt(freeTiles.size()));
            Enemy enemy;
            switch (i) {
                case 0:
                    enemy = new Skeleton(spawnPoint.x, spawnPoint.y, this);
                    break;
                case 1:
                    enemy = new Slime(spawnPoint.x, spawnPoint.y, this);
                    break;
                default:
                    enemy = new Dragon(spawnPoint.x, spawnPoint.y, this);
                    break;
            }

            enemies.add(enemy);
        }
    }

    public static void doGui() {
        JFrame frame = new JFrame("GameBoard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameBoard gameBoard = new GameBoard();
        frame.add(gameBoard);
        gameBoard.mainFrame = frame;

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);


        gameBoard.spawnEnemies();
        gameBoard.startGame();
    }
    public void startGame() {
        player = new Player(this);
        addKeyListener(player);
        setFocusable(true);
        new Thread(() -> {
            while (true) {
                for (Enemy enemy : enemies) {
                    enemy.movement();
                }
                checkFireCollisionWithEnemy();
                repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public Map getMap() {
        return map;
    }
    public Bomb getBomb(){
        return bomb;
    }
    public List<Point> getFireLocations() {
        return fireLocations;
    }

    public void checkAndRemoveEnemy(int x, int y) {
        enemies.removeIf(enemy -> {
            boolean isAtPosition = enemy.getX() / TILE_SIZE == x && enemy.getY() / TILE_SIZE == y;
            if (isAtPosition) {
                enemy.ability();
            }
            return isAtPosition;
        });
        enemies.addAll(newEnemies);
        newEnemies.clear();
    }
    public void updateFireLocations(List<Point> fireLocations){
        this.fireLocations = fireLocations;
    }

    private void checkFireCollisionWithEnemy(){
        if (fireLocations != null && !fireLocations.isEmpty()) {
            for (Point fireLocation : fireLocations) {
                int fireX = fireLocation.x;
                int fireY = fireLocation.y;
                enemies.removeIf(enemy -> enemy.getX() == fireX && enemy.getY() == fireY);
            }
        }
    }
    public void addNewEnemy(Enemy enemy) {
        newEnemies.add(enemy);
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
}

