import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
    private GameBoard gameBoard;
    private Player player;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard();
        player = new Player(gameBoard);
    }

    @Test
    void testKeyPressed_W() {
        int initialY = player.getY();
        KeyEvent keyEvent = new KeyEvent(gameBoard, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        player.keyPressed(keyEvent);
        int newY = player.getY();
        assertEquals(initialY - gameBoard.TILE_SIZE +49, newY, "Player should move up when pressing W");
    }


    @Test
    void testKeyPressed_S() {
        int initialY = player.getY();
        KeyEvent keyEvent = new KeyEvent(gameBoard, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        player.keyPressed(keyEvent);
        int newY = player.getY();
        assertEquals(initialY + gameBoard.TILE_SIZE-49, newY, "Player should move down when pressing S");
    }

    @Test
    void testKeyPressed_A() {
        int initialX = player.getX();
        KeyEvent keyEvent = new KeyEvent(gameBoard, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        player.keyPressed(keyEvent);
        int newX = player.getX();
        assertEquals(initialX - gameBoard.TILE_SIZE +49, newX, "Player should move left when pressing A");
    }

    @Test
    void testKeyPressed_D() {
        int initialX = player.getX();
        KeyEvent keyEvent = new KeyEvent(gameBoard, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
        player.keyPressed(keyEvent);
        int newX = player.getX();
        assertEquals(initialX + gameBoard.TILE_SIZE-49, newX, "Player should move right when pressing D");
    }
}