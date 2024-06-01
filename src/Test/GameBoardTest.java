import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class GameBoardTest {
    private GameBoard gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard();
    }


    @Test
    void startGame() {
        gameBoard.startGame();

        assertNotNull(gameBoard.getPlayer());
        assertTrue(gameBoard.getGameTimer().isRunning());
    }
    @Test
    void isBombAt() {
        gameBoard.getBomb().setX(0 * GameBoard.TILE_SIZE);
        gameBoard.getBomb().setY(0 * GameBoard.TILE_SIZE);

        assertTrue(gameBoard.isBombAt(0, 0), "Bomb should be at position (0, 0)");
        assertFalse(gameBoard.isBombAt(3, 4), "Bomb should not be at position (3, 4)");
    }

}
