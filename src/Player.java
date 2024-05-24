import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player implements KeyListener {
    private int x;
    private int y;
    private int speed;
    private GameBoard gameBoard;
    private int deltaX = 0;
    private int deltaY = 0;
    private BufferedImage playerImage;
    private BufferedImage bombImage;
    private BufferedImage fireImage;
    private boolean showBomb = false;
    private int bombX = -1;
    private int bombY = -1;
    private boolean showFire = false;
    private List<Point> fireLocation = new ArrayList<>();
    private boolean isGameOver = false;
    private boolean playerWasOnFire = false;

    public Player(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        x = (gameBoard.COLUMN_COUNT / 2) * gameBoard.TILE_SIZE;
        y = (gameBoard.ROW_COUNT / 2) * gameBoard.TILE_SIZE;
        speed = gameBoard.TILE_SIZE;
        gameBoard.addKeyListener(this);

        try {
            playerImage = ImageIO.read(new File("src/Player.png"));
            bombImage = ImageIO.read(new File("src/Bomb.png"));
            fireImage = ImageIO.read(new File("src/Fire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(this::movement);
        thread.start();
        new Thread(() -> {
            while (true) {
                checkEnemyCollision();
                checkFireCollision();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void print(Graphics g) {
        if (playerImage != null) {
            g.drawImage(playerImage, x, y, gameBoard.TILE_SIZE, gameBoard.TILE_SIZE, null);
        }
            if (showBomb && bombImage != null) {
                if (bombX != -1 && bombY != -1) {
                    g.drawImage(bombImage, bombX - bombImage.getWidth() / 2, bombY - bombImage.getHeight() / 2, null);
                }
            }
        if (showFire && fireImage != null) {
            for (Point position : fireLocation) {
                int indexX = position.x / gameBoard.TILE_SIZE;
                int indexY = position.y / gameBoard.TILE_SIZE;
                if (gameBoard.getMap().getTile(indexX, indexY) == 0) {
                    g.drawImage(fireImage, position.x - fireImage.getWidth() / 2, position.y - fireImage.getHeight() / 2, null);
                }
            }
        }
        }

    private void movement() {
        while (true) {
            int novaX = x + (deltaX != 0 ? (deltaX / Math.abs(deltaX)) * gameBoard.TILE_SIZE : 0);
            int novaY = y + (deltaY != 0 ? (deltaY / Math.abs(deltaY)) * gameBoard.TILE_SIZE : 0);

            if (novaX >= 0 && novaX <= (gameBoard.COLUMN_COUNT - 1) * gameBoard.TILE_SIZE &&
                    novaY >= 0 && novaY <= (gameBoard.ROW_COUNT - 1) * gameBoard.TILE_SIZE &&
                    gameBoard.getMap().getTile(novaX / gameBoard.TILE_SIZE, novaY / gameBoard.TILE_SIZE) == 0) {
                x = novaX;
                y = novaY;
                gameBoard.repaint();
            } else if (novaX >= 0 && novaX <= (gameBoard.COLUMN_COUNT - 1) * gameBoard.TILE_SIZE &&
                    novaY >= 0 && novaY <= (gameBoard.ROW_COUNT - 1) * gameBoard.TILE_SIZE &&
                    gameBoard.getMap().getTile(novaX / gameBoard.TILE_SIZE, novaY / gameBoard.TILE_SIZE) == 4) {

                x = novaX;
                y = novaY;
                gameBoard.getMap().getScene()[y / gameBoard.TILE_SIZE][x / gameBoard.TILE_SIZE] = 0;
                gameBoard.repaint();
            }

            try {
                Thread.sleep(130);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


            @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isGameOver) {
            return;
        }
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                deltaY = -gameBoard.TILE_SIZE;
                deltaX = 0;
                break;
            case KeyEvent.VK_S:
                deltaY = gameBoard.TILE_SIZE;
                deltaX = 0;
                break;
            case KeyEvent.VK_A:
                deltaX = -gameBoard.TILE_SIZE;
                deltaY = 0;
                break;
            case KeyEvent.VK_D:
                deltaX = gameBoard.TILE_SIZE;
                deltaY = 0;
                break;
            case KeyEvent.VK_SPACE:
                if (!showBomb) {
                    showBomb = true;
                    bombX = (x / gameBoard.TILE_SIZE) * gameBoard.TILE_SIZE + gameBoard.TILE_SIZE / 2;
                    bombY = (y / gameBoard.TILE_SIZE) * gameBoard.TILE_SIZE + gameBoard.TILE_SIZE / 2;
                    gameBoard.getBomb().setPosition(bombX / gameBoard.TILE_SIZE, bombY / gameBoard.TILE_SIZE);
                    gameBoard.repaint();

                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            showFire = true;
                            fireLocation.add(new Point(bombX, bombY - gameBoard.TILE_SIZE));
                            fireLocation.add(new Point(bombX, bombY + gameBoard.TILE_SIZE));
                            fireLocation.add(new Point(bombX + gameBoard.TILE_SIZE, bombY));
                            fireLocation.add(new Point(bombX - gameBoard.TILE_SIZE, bombY));
                            gameBoard.repaint();
                            gameBoard.getBomb().explode(bombX / gameBoard.TILE_SIZE, bombY / gameBoard.TILE_SIZE, gameBoard.getMap().getScene());
                            Thread.sleep(2000);
                            showFire = false;
                            fireLocation.clear();
                            bombX = -1;
                            bombY = -1;
                            showBomb = false;
                            gameBoard.repaint();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }
                break;
        }
        gameBoard.repaint();
        checkFireCollision();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(isGameOver) {
            return;
        }
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                deltaY = 0;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                deltaX = 0;
                break;
        }
    }
    private void checkFireCollision() {
        if (showFire) {
            Rectangle playerRect = new Rectangle(x, y, gameBoard.TILE_SIZE, gameBoard.TILE_SIZE);
            for (Point firePos : fireLocation) {
                Rectangle fireRect = new Rectangle(firePos.x - gameBoard.TILE_SIZE / 2, firePos.y - gameBoard.TILE_SIZE / 2, gameBoard.TILE_SIZE, gameBoard.TILE_SIZE);
                if (playerRect.intersects(fireRect)) {
                    if (!isGameOver) {
                        new GameOverFrame(gameBoard);
                        isGameOver = true;
                    }
                    return;
                }
            }
            if (playerWasOnFire && fireLocation.contains(new Point(x, y))) {
                if (!isGameOver) {
                    new GameOverFrame(gameBoard);
                    isGameOver = true;
                }
                return;
            }
            int playerTileX = x / gameBoard.TILE_SIZE;
            int playerTileY = y / gameBoard.TILE_SIZE;
            if (bombX != -1 && bombY != -1 && playerTileX == bombX / gameBoard.TILE_SIZE && playerTileY == bombY / gameBoard.TILE_SIZE) {
                if (!isGameOver) {
                    new GameOverFrame(gameBoard);
                    isGameOver = true;
                }
                return;
            }
            playerWasOnFire = fireLocation.contains(new Point(x, y));
        }
    }
    private void checkEnemyCollision() {
        Rectangle playerRect = new Rectangle(x, y, gameBoard.TILE_SIZE, gameBoard.TILE_SIZE);
        for (Enemy enemy : gameBoard.getEnemies()) {
            Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), gameBoard.TILE_SIZE, gameBoard.TILE_SIZE);
            if (playerRect.intersects(enemyRect)) {
                if (!isGameOver) {
                    new GameOverFrame(gameBoard);
                    isGameOver = true;
                }
                return;
            }
        }
    }

}




