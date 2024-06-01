package Game;

/**
 * The Game.Main class serves as the entry point for the application.
 * It initializes the GUI by creating a new Game.Frame with a Game.GameBoard.
 */
public class Main {

    public static void main(String[] args) {
       new Frame(new GameBoard());
    }
}
