/**
 * The Main class is responsible for creating and starting a new game of Patchwork.
 */
package patchwork;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) {
		// Choice of the different phases of the game
		int choicePlayer;
		var level = new ChoiceLevel();
		System.out.println(level);
		choicePlayer = level.choose();

		if (choicePlayer == 1) {
			// Creating players with their corresponding patchwork
			Patchwork patchwork1 = new Patchwork();
			Player player1 = new Player("Player 1", patchwork1);

			Patchwork patchwork2 = new Patchwork();
			Player player2 = new Player("Player 2", patchwork2);

			// Creating the game with the two players and a board size of 5
			PatchworkGame game = new PatchworkGame(player1, player2, 5, false);

			// Starting the game
			game.start();
		} else if (choicePlayer == 2) {
			// Creating players with their corresponding patchwork
			Patchwork patchwork1 = new Patchwork();
			Player player1 = new Player("Player 1", patchwork1);

			Patchwork patchwork2 = new Patchwork();
			Player player2 = new Player("Player 2", patchwork2);

			// Creating the game with the two players and a board size of 5
			PatchworkGame game = new PatchworkGame(player1, player2, 5, true);
			

			// Loading the full set of patches for the complete game
			var path = Paths.get("src/data/dataPatch.data");
			game.loadDataPatch(path);

			// Starting the game
			game.start();
		}
		else if(choicePlayer == 0) {
			return; 
		}
	}
}
