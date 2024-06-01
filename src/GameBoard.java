import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * The GameBoard class represents the main gameboard where the game is played.
 * It handles the drawing of the game elements, game logic, and the user interface components.
 */
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
    public JFrame mainFrame;
    private List<Enemy> newEnemies = new ArrayList<>();
    private JLabel timerLabel;
    private Timer gameTimer;
    private int gameTimeInSeconds;
    private int currentLevel = 1;
    private boolean gameWon = false;
    public boolean levelCleared = false;
    public boolean boostSpawned = false;

    /**
     * Constructor for the GameBoard class. It initializes the game map, bomb, player, and enemies.
     * It also sets up the timer and the user interface components.
     */
    public GameBoard() {
        map = new Map(currentLevel);
        bomb = new Bomb(this);
        enemies = new ArrayList<>();
        fireLocations = new ArrayList<>();
        setPreferredSize(new Dimension(COLUMN_COUNT * TILE_SIZE, ROW_COUNT * TILE_SIZE));
        try {
            URL playerImages = this.getClass().getResource("/Images/Player.png");
            playerImage = ImageIO.read(Objects.requireNonNull(playerImages));
            URL boostImages = this.getClass().getResource("/Images/Boost.png");
            boostImage = ImageIO.read(Objects.requireNonNull(boostImages));
        } catch (IOException e) {
            e.printStackTrace();
        }

        timerLabel = new JLabel("Timer: 0 ");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Default", Font.BOLD, 28));
        timerLabel.setForeground(Color.WHITE);
        add(timerLabel, BorderLayout.NORTH);
        gameTimeInSeconds = 0;
        gameTimer = new Timer(1000, e -> {
            gameTimeInSeconds++;
            updateTimerLabel();
        });
    }

    /**
     * Paints the components of the game board including the map, player, enemies, and fire locations.
     * @param g the object to draw on
     * The game board is drawn with different colors based on the tile index.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect(0, 0, COLUMN_COUNT * TILE_SIZE, ROW_COUNT * TILE_SIZE);

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COLUMN_COUNT; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                int tile = map.getTile(col, row);
                Color barva;

                if (tile == 1) {
                    barva = Color.BLACK;
                } else if (tile == 2) {
                    barva = TileColour.GRAY.getColour();
                } else if (tile == 3) {
                    barva = TileColour.BROWN.getColour();
                } else if (tile == 4) {
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
        List<Enemy> enemiesCopy = new ArrayList<>(enemies);
        for (Enemy enemy : enemiesCopy) {
            enemy.draw(g, enemy.getX(), enemy.getY(), TILE_SIZE);
        }
    }

    /**
     * Spawns enemies on the game board based on the current level.
     */
    public void spawnEnemies() {
        enemies.clear();
        Random random = new Random();
        List<Point> freeTiles = new ArrayList<>();
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COLUMN_COUNT; col++) {
                if (map.getTile(col, row) == 0) {
                    freeTiles.add(new Point(col * TILE_SIZE, row * TILE_SIZE));
                }
            }
        }
        int slimesCount = 0 ;
        int skeletonsCount = 1;
        int dragonsCount = 0;
        if (currentLevel == 2) {
            slimesCount = 1;
            skeletonsCount = 2;
        } else if (currentLevel == 3) {
            slimesCount = 2;
            skeletonsCount = 2;
            dragonsCount = 1;
        }

        spawnSpecificEnemies(slimesCount, Slime.class, freeTiles, random, true);
        spawnSpecificEnemies(skeletonsCount, Skeleton.class, freeTiles, random, false);
        spawnSpecificEnemies(dragonsCount, Dragon.class, freeTiles, random, false);
    }

    /**
     * Spawns a specific number of enemies of a specific type on the game board.
     * The enemies are spawned on free tiles on the game board.
     * @param count      the number of enemies to spawn
     * @param enemyClass the class of the enemy to spawn
     * @param freeTiles  the list of free tiles on the game board
     * @param random     the random object to generate random numbers
     * @param original   the boolean value to determine if the enemy is the original enemy
     */

    private void spawnSpecificEnemies(int count, Class<? extends Enemy> enemyClass, List<Point> freeTiles, Random random, boolean original) {
        for (int i = 0; i < count; i++) {
            if (freeTiles.isEmpty()) {
                break;
            }
            Point spawnPoint = freeTiles.remove(random.nextInt(freeTiles.size()));
            try {
                Enemy enemy;
                if (enemyClass == Slime.class) {
                    enemy = new Slime(spawnPoint.x, spawnPoint.y, this, original);
                } else {
                    enemy = enemyClass.getConstructor(int.class, int.class, GameBoard.class).newInstance(spawnPoint.x, spawnPoint.y, this);
                }
                enemies.add(enemy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Checks for collisions between the fire and the enemies.
     * If the fire is at the same location as an enemy, the enemy is removed from the game.
     */
    private void checkFireCollisionWithEnemy(){
        if (fireLocations != null && !fireLocations.isEmpty()) {
            for (Point fireLocation : fireLocations) {
                int fireX = fireLocation.x;
                int fireY = fireLocation.y;
                Iterator<Enemy> iterator = enemies.iterator();
                while (iterator.hasNext()) {
                    Enemy enemy = iterator.next();
                    if (enemy.getX() == fireX && enemy.getY() == fireY) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * Initializes and displays the gameboard GUI.
     */
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
    /**
     * Starts the game by initializing the player and game timer, and continuously updates the game state.
     * The game state is updated by moving the enemies and checking for collisions between the player and enemies.
     */
    public void startGame() {
        player = new Player(this);
        addKeyListener(player);
        setFocusable(true);
        gameTimer.start();
        new Thread(() -> {
            while (true) {
                for (Enemy enemy : enemies) {
                    enemy.movement();
                }
                checkFireCollisionWithEnemy();
                if(enemies.isEmpty()&& !levelCleared){
                    levelCleared = true;
                    showNextLevelFrame();
                }
                repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Moves the game to the next level by updating the map, spawning enemies, and resetting the game state.
     */
    public void nextLevel() {
        if (currentLevel < 3) {
            currentLevel++;
            map = new Map(currentLevel);
            spawnEnemies();
            levelCleared = false;
        } else if (!gameWon) {
            gameWon = true;
            gameWon();
        }
    }
    /**
     * Restarts the current level by resetting the player, enemies, and game state when clicking Play Again.
     */
    public void restartCurrentLevel() {
        player = null;
        enemies.clear();
        fireLocations.clear();
        map = new Map(currentLevel);
        bomb = new Bomb(this);
        player = new Player(this);
        gameTimer.stop();
        gameTimeInSeconds = 0;
        gameTimer.start();
        spawnEnemies();
        levelCleared = false;
        gameWon = false;
        boostSpawned = false;
        repaint();
    }

    /**
     * Checks if an enemy is at a specific position and removes it from the game.
     * @param x the x-coordinate of the enemy
     * @param y the y-coordinate of the enemy
     */
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

    /**
     * Checks if a bomb is at a specific position.
     * @param x the x-coordinate of the bomb
     * @param y the y-coordinate of the bomb
     * @return true if the bomb is at the specified position, false otherwise
     */
    public boolean isBombAt(int x, int y) {
        Bomb bomb = getBomb();
        return bomb.getX() == x && bomb.getY() == y;
    }
    /**
     * Sets the player to the center of the game board.
     */
    public void setPlayerToCenter() {
        player.setX((COLUMN_COUNT / 2) *( TILE_SIZE));
        player.setY((ROW_COUNT / 2) *( TILE_SIZE));
    }

    /**
     * Displays the game won frame when the player wins the game.
     */
    private void gameWon() {
        SwingUtilities.invokeLater(() -> new GameWonFrame(this, mainFrame));
    }

    /**
     * Displays the next level frame when the player completes a level.
     */
    private void showNextLevelFrame() {
        if (!player.isGameOver) {
            SwingUtilities.invokeLater(() -> new NextLevelFrame(this, mainFrame));
        }
    }
    /**
     * Updates the timer label to display the current game time.
     */
    private void updateTimerLabel() {
        SwingUtilities.invokeLater(() -> timerLabel.setText("Timer: " + gameTimeInSeconds));
    }

    /**
     * Returns the current map of the game board.
     * @return the current map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns the bomb object associated with the game board.
     * @return the bomb object
     */
    public Bomb getBomb(){
        return bomb;
    }

    /**
     * Updates the locations of fire on the game board.
     * @param fireLocations the list of fire locations
     */
    public void updateFireLocations(List<Point> fireLocations){
        this.fireLocations = fireLocations;
    }

    /**
     * Adds a new enemy to be spawned on the game board.
     * @param enemy the enemy to add
     */
    public void addNewEnemy(Enemy enemy) {
        newEnemies.add(enemy);
    }

    /**
     * Returns the list of enemies currently on the game board.
     * @return the list of enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Returns the player object associated with the game board.
     * @return the player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the game timer associated with the game board.
     * @return the game timer
     */
    public Timer getGameTimer() {
        return gameTimer;
    }
}
