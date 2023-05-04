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
		if (size < 1) {
            throw new IllegalArgumentException("size must be at least 1");
        }
		this.availablePieces = new ArrayList<>();
		this.player1 = player1;
		this.player2 = player2;
		this.size = size;
		this.scanner = new Scanner(System.in);
		this.timeBoard = new int[size][size];

		// Initialize the available pieces
		for (int i = 0; i < 20; i++) {
			availablePieces.add(new Patch(i, i % 2 == 0 ? 4 : 2, i % 2 == 0 ? i + 1 : 0, 2, 2));
		}

		// Shuffle the available pieces
		java.util.Collections.shuffle(availablePieces);

		// Initialize the time board
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				timeBoard[i][j] = i * size + j;
			}
		}
	}

	/**
	 * Starts the game.
	 */
	public void start() {
		while (!isGameFinished()) {
			playTurn(player1);
			playTurn(player2);
		}

		// Determine the winner
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
                System.out.println(i + " : " + availablePieces.get(i).toString());
            }
            int choice = choosePiece(player);
            if (choice >= 0 && choice < availablePieces.size()) {
                piece = availablePieces.get(choice);
                availablePieces.remove(choice);
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
            
            
            // Update the player's buttons and time
            player.addButtons(piece.getValue());
            player.addTimePlayer(piece.getCost());

            // Update the player's position on the time board
            int newPosition = Math.min(player.getPosition() + piece.getCost(), size * size - 1);
            for (int i = player.getPosition() + 1; i <= newPosition; i++) {
                player.addButtons(timeBoard[i / size][i % size]);
            }
            player.setPosition(newPosition);

            // End the turn
            player.setDone(true);
        }
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
