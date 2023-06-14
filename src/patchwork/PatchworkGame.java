package patchwork;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PatchworkGame {
	private final List<Patch> patches; // List of available patches for the game.
	private final Player player1; // Player 1 instance.
	private final Player player2; // Player 2 instance.
	private final int size; // Size of the game board.
	private final TimeBoard timeBoard; // Array to store time board.
	private final boolean fullGame; // Determines whether the game is in phase 1 or 2.

	/**
	 * Constructor for the PatchworkGame class.
	 *
	 * @param player1 Player 1 instance.
	 * @param player2 Player 2 instance.
	 * @param size    Size of the game board.
	 * @throws IllegalArgumentException if the size is less than 1.
	 */
	public PatchworkGame(Player player1, Player player2, int size, boolean fullGame){
		if (size < 1) {
			throw new IllegalArgumentException("Size must be at least 1");
		}

		this.patches = new ArrayList<>(); // Initialize the availablePieces list.
		this.player1 = player1; // Initialize player 1.
		this.player2 = player2; // Initialize player 2.
		this.size = size; // Initialize the size.
		this.timeBoard = new TimeBoard(size); // Initialize the time board.
		this.fullGame = fullGame; // Initialize fullGame.
		if (fullGame) {
			Path path = Paths.get("src/data/dataPatch.data");
			loadDataPatch(path); // Load pieces from file if it's a full game.
		} else {
			initializeOpenPatch(); // Initialize pieces for simplified game.
		}

		// Shuffle the available pieces.
		Collections.shuffle(patches);

		// Initialize the time board.
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				timeBoard.setValue(i, j, i * size + j);
			}
		}
	}

	/**
	 * Initializes the list of available pieces based on whether the game is in
	 * phase 1 or 2.
	 */
	private void initializeOpenPatch() {
		patches.clear();

		if (fullGame) {
			var path = Paths.get("src/data/dataPatch.data");
			loadDataPatch(path); // Load pieces from file if it's a full game.

		} else {
			// The current code for initializing the available pieces for the simplified
			// game can stay here.
			var p1 = new Patch(3, 4, 1, new boolean[][] { { true, true }, { true, true } }, size, size);
			patches.add(p1);
			var p2 = new Patch(2, 2, 0, new boolean[][] { { true, true }, { true, true } }, size, size);
			patches.add(p2);
			for (int i = 0; i < 20; i++) {
				var grid = new boolean[][] { { true, true }, { true, true } };
				int cost, income, time, width, height;

				if (i % 2 == 0) {
					cost = 3;
					time = 4;
					income = 1;
				} else {
					cost = 2;
					time = 2;
					income = 0;
				}

				width = 2;
				height = 2;

				patches.add(new Patch(cost, time, income, grid, width, height));
			}
		}

		Collections.shuffle(patches); // Shuffle the available pieces.
	}

	/**
	 * Starts the game. Plays turns for both players until the game is finished.
	 * Determines the winner and prints the result.
	 */
	public void start() {
		while (!isGameFinished()) {
			playTurn(player1);
			playTurn(player2);
		}
		// Determine the winner
		var winner = player1.getButtons() > player2.getButtons() ? player1
				: player2.getButtons() > player1.getButtons() ? player2 : null;

		// Print the result
		System.out.println("The game has ended!");
		if (winner == null) {
			System.out.println("It's a tie!");
		} else {
			System.out.println("Player " + winner.getName() + " has won!");
		}
	}

	/**
	 * Plays a turn for the given player. This method prompts the player to choose a
	 * piece of fabric to add to their patchwork and to choose the location on the
	 * patchwork where the fabric will be placed. It then updates the player's
	 * buttons, time, position on the time board, and sets the "done" flag to true
	 * to indicate that the player's turn is over.
	 * 
	 * @param player The player to play the turn for.
	 */
	private void playTurn(Player player) {
		// Print the current state of the game
		System.out.println(player.getName() + " starts their turn!");
		System.out.println("Buttons: " + player.getButtons());
		System.out.println("Remaining time: " + player.getTimePlayer());
		System.out.println("Position on time board: " + player.getPosition());
		System.out.println("Patchwork:");
		System.out.println(player.getPatchworkPlayer().toString());

		// Choose a piece or advance
		var inputScanner = new Scanner(System.in);
		var choice = -1;
		Patch patch = null; 

		while (patch == null) {
			System.out.println("Here are the available pieces:");
			var displayedPieces = 0;
			//&& displayedPieces < 3
			for (int i = 0; i < patches.size() ; i++) {
				Patch p = patches.get(i);
				if (player.openPatch(p)) {
					System.out.println(i + " : " + p.toString());
					displayedPieces++;
				}
			}

			while (true) {
				try {
					System.out.print("Choose a piece (number) or enter -1 to advance: ");
					choice = Integer.parseInt(inputScanner.nextLine());

					if (choice == -1) {
						int buttonsToAdd = player.advanceAndReceiveButtons(player == player1 ? player2 : player1,
								timeBoard);
						player.payButtons(buttonsToAdd);
						return;
					} else if (choice >= 0 && choice < patches.size()
							&& player.openPatch(patches.get(choice))) {
						var chosenPiece = patches.get(choice); // Just get the chosen piece
						if (chosenPiece.getCost() > player.getButtons()) {
							System.out.println("You don't have enough buttons to purchase this piece. "
									+ "Please choose another piece or enter -1 to advance.");
						} else {
							patch = chosenPiece; // Assign the chosen piece to 'piece'
							patches.remove(choice); // Remove the chosen piece from the list of available pieces
							break;
						}
					} else {
						System.out.println("Invalid choice. Please choose another piece or enter -1 to advance.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter a number.");
					inputScanner.nextLine(); // consume the invalid token
				}
			}
		}

		// Add the piece to the player's patchwork
		var added = false;
		var rotationDegrees = 0; 
		while (!added) {
			System.out.println("Here is your patchwork:");
			System.out.println(player.getPatchworkPlayer().toString());
			while (true) {
				try {
					System.out.println("Choose the coordinates where you want to place the piece (x,y):");
					var x = Integer.parseInt(inputScanner.nextLine());
					var y = Integer.parseInt(inputScanner.nextLine());

					// Ask the user for the rotation degree
					System.out.println("Choose the degree of rotation for the piece (90, 180, 270):");
					rotationDegrees = Integer.parseInt(inputScanner.nextLine());

					// Rotate the piece before adding it to the patchwork
					patch = patch.rotate(rotationDegrees);
				

					// Check if the player can afford the piece
					var pieceCost = patch.getCost();
					if (pieceCost > player.getButtons()) {
						System.out.println("The player doesn't have enough buttons to purchase this piece.");
						System.out.println("Enter -1 to move without taking a patch");
						choice = Integer.parseInt(inputScanner.nextLine());
						if (choice == -1) {
							// Move without taking a patch
							var buttonsToAdd = player.advanceAndReceiveButtons(player == player1 ? player2 : player1,
									timeBoard);
							player.payButtons(buttonsToAdd);
							return;
						} else {
							System.out.println("Invalid choice.");
							inputScanner.nextLine();
						}
					}
					// Add the piece to the player's patchwork
					added = player.getPatchworkPlayer().addToPatchwork(player, patch, x, y);

					if (!added) {
						System.out.println("The piece cannot be placed at these coordinates. Please choose again.");
					}
					break;
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter a number.");
					inputScanner.nextLine(); // consume the invalid token
				}
			}
		}
		// Remove the piece from the available pieces
		patches.remove(patch);

		// Update the player's buttons and time
		if (added) {
			player.addTimePlayer(patch.getTime());

			var buttonsToAdd = patch.getIncome() - patch.getCost();
			if (buttonsToAdd > 0) {
				player.payButtons(buttonsToAdd);
			}
		}

		// Update the player's position on the time board
		var newPosition = Math.min(player.getPosition() + patch.getCost(), size * size - 1);
		for (int i = player.getPosition() + 1; i <= newPosition; i++) {
//      player.payButtons(timeBoard.getValueAt(i / size, i % size));
		}
		player.setPosition(newPosition);

		// End the turn
		player.setDone(true);
	}

	

	public void loadDataPatch(Path path) {
		try(var reader = Files.newBufferedReader(path)) {
			var scanner = new Scanner(reader);

			patches.clear();

			while (scanner.hasNextLine()) {
				var line = scanner.nextLine();
				var parts = line.split(",");

				var cost = Integer.parseInt(parts[0]);
				var time = Integer.parseInt(parts[1]);
				var income = Integer.parseInt(parts[2]);
				var width = Integer.parseInt(parts[3]);
				var height = Integer.parseInt(parts[4]);

				var grid = new boolean[height][width];

				for (int i = 0; i < height; i++) {
					line = scanner.nextLine();
					var gridValues = line.split(",");
					for (int j = 0; j < width; j++) {
						grid[i][j] = Integer.parseInt(gridValues[j]) == 1;
					}
				}
				if (cost != 0 || time != 0 || income != 0) {
					patches.add(new Patch(cost, time, income, grid, width, height));
				}

			}

			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error has occurred while loading the complete set of parts.");
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Collections.shuffle(patches);
	}

	/**
	 * Returns true if the game is finished.
	 * 
	 * @return True if the game is finished, false otherwise.
	 */
	private boolean isGameFinished() {
		return player1.getDone() && player2.getDone() && patches.isEmpty();
	}
}
