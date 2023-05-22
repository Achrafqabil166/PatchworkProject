/**
 * The Main class is responsible for creating and starting a new game of Patchwork.
 */
package patchwork;

public class Main {
	public static void main(String[] args) {
		// choice of the different phases of the game
		int choicePlayer;
		var choice = new ChoiceLevel();
		System.out.println(choice); 
		choicePlayer = choice.choose();
		
		//phase
		if (choicePlayer == 1) {
		
		
		// Creating players with their corresponding patchworks
		Patchwork patchwork1 = new Patchwork();
		Player player1 = new Player("Player 1", patchwork1);

		Patchwork patchwork2 = new Patchwork();
		Player player2 = new Player("Player 2", patchwork2);

		// Creating the game with the two players and a board size of 5
		PatchworkGame game = new PatchworkGame(player1, player2, 5);

		// Starting the game
		game.start();
		}
	}
}
