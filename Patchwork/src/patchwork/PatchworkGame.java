package patchwork;

/*
 * PatchworkGame class represents the game logic for a Patchwork game.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatchworkGame {
	private final List<Patch> availablePieces; // List of available patches for the game.
	private final Player player1; // Player 1 instance.
	private final Player player2; // Player 2 instance.
	private final int size; // Size of the game board.
	private final Scanner scanner; // Scanner instance to read player input.
	private final int[][] timeBoard; // Array to store time board.

	/**
	 * Constructor for the PatchworkGame class.
	 * 
	 * @param player1 Player 1 instance.
	 * @param player2 Player 2 instance.
	 * @param size    Size of the game board.
	 * @throws IllegalArgumentException if the size is less than 1.
	 */
	public PatchworkGame(Player player1, Player player2, int size) throws IllegalArgumentException {
		if (size < 1) {
			throw new IllegalArgumentException("Size must be at least 1");
		}

		this.availablePieces = new ArrayList<>(); // Initialize the availablePieces list.
		this.player1 = player1; // Initialize player 1.
		this.player2 = player2; // Initialize player 2.
		this.size = size; // Initialize the size.
		this.scanner = new Scanner(System.in); // Initialize scanner to read player input.
		this.timeBoard = new int[size][size]; // Initialize the time board.

		// Add initial patches to availablePieces list.
		Patch p1 = new Patch(3, 4, 1, new boolean[][] { { true, true }, { true, true } }, size, size);
		availablePieces.add(p1);
		Patch p2 = new Patch(2, 2, 0, new boolean[][] { { true, true }, { false, false } }, size, size);
		availablePieces.add(p2);
		for (int i = 0; i < 20; i++) {
			boolean[][] grid = new boolean[][] { { true, true }, { true, true } };
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

			availablePieces.add(new Patch(cost, time, income, grid, width, height));
		}

		// Shuffle the available pieces.
		java.util.Collections.shuffle(availablePieces);

		// Initialize the time board.
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				timeBoard[i][j] = i * size + j;
			}
		}
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
		Player winner = player1.getButtons() > player2.getButtons() ? player1
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
	 * 
	 * Plays a turn for the given player. This method prompts the player to choose a
	 * piece of fabric to add to their patchwork, and to choose the location on the
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
		// Choose a piece
		Patch piece = null;
		while (piece == null) {
			System.out.println("Here are the available pieces:");
			for (int i = 0; i < availablePieces.size(); i++) {
				Patch p = availablePieces.get(i);
				if (!p.equals(piece) && player.isPieceAvailable(p)) {
					System.out.println(i + " : " + p.toString());
				}
			}
			System.out.print("Choose a piece (number): ");
			int choice = scanner.nextInt();
			if (choice >= 0 && choice < availablePieces.size()
					&& player.isPieceAvailable(availablePieces.get(choice))) {
				piece = availablePieces.remove(choice); // Remove the chosen piece from the list of available pieces
			} else {
				System.out.println("The chosen piece is not available. Please choose another piece.");
			}
		}
		// Add the piece to the player's patchwork
		boolean added = false;
		while (!added) {
			System.out.println("Here is your patchwork:");
			System.out.println(player.getPatchworkPlayer().toString());
			System.out.println("Choose the coordinates where you want to place the piece (x,y):");
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			added = player.addToPatchwork(piece, x, y);
			if (!added) {
				System.out.println("The piece cannot be placed at these coordinates. Please choose again.");
			}
		}
		// Update the player's buttons and time
		int buttonsToAdd = piece.getIncome() - piece.getCost();
		if (buttonsToAdd > 0) {
			player.addButtons(buttonsToAdd);
		}
		player.addTimePlayer(piece.getTime());
		// Update the player's position on the time board
		int newPosition = Math.min(player.getPosition() + piece.getCost(), size * size - 1);
		for (int i = player.getPosition() + 1; i <= newPosition; i++) {
			player.addButtons(timeBoard[i / size][i % size]);
		}
		player.setPosition(newPosition);
		// End the turn
		player.setDone(true);
	}

	/**
	 * Returns true if the game is finished.
	 * 
	 * @return True if the game is finished, false otherwise.
	 */
	private boolean isGameFinished() {
		return player1.getDone() && player2.getDone() && availablePieces.isEmpty();
	}

}