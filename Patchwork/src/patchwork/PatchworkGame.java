package patchwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatchworkGame {
	private final List<Patch> availablePieces;
	private final Player player1;
	private final Player player2;
	private final int size;
	private final Scanner scanner;
	private final int[][] timeBoard;

	public PatchworkGame(Player player1, Player player2, int size) {
		// Vérifie que la taille est valide
		if (size < 1) {
			throw new IllegalArgumentException("La taille doit être au moins de 1");
		}

		// Initialise les champs de la classe
		this.availablePieces = new ArrayList<>();
		this.player1 = player1;
		this.player2 = player2;
		this.size = size;
		this.scanner = new Scanner(System.in);
		this.timeBoard = new int[size][size];

		// Initialise les pièces disponibles
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

		// Mélange les pièces disponibles
		java.util.Collections.shuffle(availablePieces);

		// Initialise le time board
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				timeBoard[i][j] = i * size + j;
			}
		}
	}

	/**
	 * Démarre le jeu.
	 */
	public void start() {
		while (!isGameFinished()) {
			playTurn(player1);
			playTurn(player2);
		}

		// Détermine le vainqueur
		Player winner = player1.getButtons() > player2.getButtons() ? player1
				: player2.getButtons() > player1.getButtons() ? player2 : null;
		System.out.println("Le jeu est terminé !");
		if (winner == null) {
			System.out.println("C'est un match nul !");
		} else {
			System.out.println("Le joueur " + winner.getName() + " a gagné !");
		}
	}

	/**
	 * Plays a turn for the given player.
	 * 
	 * @param player The player to play for.
	 */
	private void playTurn(Player player) {
	    // Print the current state of the game
	    System.out.println(player.getName() + " commence son tour !");
	    System.out.println("Boutons : " + player.getButtons());
	    System.out.println("Temps restant : " + player.getTimePlayer());
	    System.out.println("Position sur le time board : " + player.getPosition());
	    System.out.println("Patchwork :");
	    System.out.println(player.getPatchworkPlayer().toString());

	    // Choose a piece
	    Patch piece = null;
	    while (piece == null) {
	        System.out.println("Voici les pièces disponibles :");
	        for (int i = 0; i < availablePieces.size(); i++) {
	            Patch p = availablePieces.get(i);
	            if (!p.equals(piece) && player.isPieceAvailable(p)) {
	                System.out.println(i + " : " + p.toString());
	            }
	        }
	        System.out.print("Choisissez une pièce (numéro) : ");
	        int choice = scanner.nextInt();
	        if (choice >= 0 && choice < availablePieces.size() && player.isPieceAvailable(availablePieces.get(choice))) {
	            piece = availablePieces.remove(choice); // Supprime la pièce choisie de la liste des pièces disponibles
	        } else {
	            System.out.println("La pièce choisie n'est pas disponible. Veuillez choisir une autre pièce.");
	        }
	    }

		// Add the piece to the player's patchwork
		boolean added = false;
		while (!added) {
			System.out.println("Voici votre patchwork :");
			System.out.println(player.getPatchworkPlayer().toString());
			System.out.println("Choisissez les coordonnées où vous voulez placer la pièce (x,y) :");
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			added = player.addToPatchwork(piece, x, y);
			if (!added) {
				System.out.println("La pièce ne peut pas être placée à ces coordonnées. Veuillez choisir à nouveau.");
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

		// Calculate the buttons remaining for each player
		int buttonsRemainingPlayer1 = player1.getButtons() - player2.getButtons();
		int buttonsRemainingPlayer2 = player2.getButtons() - player1.getButtons();
		System.out.println("Boutons restants pour le joueur 1 : " + buttonsRemainingPlayer1);
		System.out.println("Boutons restants pour le joueur 2 : " + buttonsRemainingPlayer2);
	}

	/**
	 * Chooses a piece for the given player.
	 * 
	 * @param player The player to choose the piece for.
	 * @return The index of the chosen piece.
	 */
	private int choosePiece(Player player) {
		System.out.print("Choisissez une pièce (numéro) : ");
		int choice = scanner.nextInt();
		while (choice < 0 || choice >= availablePieces.size()
				|| !player.isPieceAvailable(availablePieces.get(choice))) {
			System.out.println("La pièce choisie n'est pas disponible. Veuillez choisir une autre pièce.");
			choice = scanner.nextInt();
		}
		return choice;
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