package patchwork;

/**
 * The Main class is responsible for creating and starting a new game of Patchwork.
 */
public class Main {
    public static void main(String[] args) {
        // choice of the different phases of the game
        int choicePlayer;
        var choice = new ChoiceLevel();
        System.out.println(choice);
        choicePlayer = choice.choose();

        if (choicePlayer == 1) {
            // Creating players with their corresponding patchworks
            Patchwork patchwork1 = new Patchwork(9);
            Player player1 = new Player("Player 1", patchwork1);

            Patchwork patchwork2 = new Patchwork(9);
            Player player2 = new Player("Player 2", patchwork2);

            // Creating the game with the two players and a board size of 5
            PatchworkGame game = new PatchworkGame(player1, player2, 9);

            // Starting the game
            game.start();
        }
    }
}
