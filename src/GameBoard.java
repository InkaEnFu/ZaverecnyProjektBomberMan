import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameBoard extends JPanel {
    public static final int ROW_COUNT = 15;
    public static final int COLUMN_COUNT = 17;
    public static final int TILE_SIZE = 49;

    private Player player;
    private Map map;
    private Bomb bomb;
    private BufferedImage playerImage;
    private BufferedImage boostImage;



    public GameBoard() {
        map = new Map();
        bomb = new Bomb();
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
    }
    public static void doGui() {
        JFrame frame = new JFrame("GameBoard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameBoard gameBoard = new GameBoard();
        frame.add(gameBoard);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        gameBoard.startGame();
    }
    public void startGame() {
        player = new Player(this);
        addKeyListener(player);
        setFocusable(true);
    }
    public Map getMap() {
        return map;
    }
    public Bomb getBomb(){ return bomb;}
}
