package patchwork;

public class Main {
	public static void main(String[] args) {
		// Création des joueurs
		Patchwork patchwork1 = new Patchwork();
		Player player1 = new Player("Joueur 1", patchwork1);

		Patchwork patchwork2 = new Patchwork();
		Player player2 = new Player("Joueur 2", patchwork2);

		// Création du jeu
		PatchworkGame game = new PatchworkGame(player1, player2, 5);

		// Début du jeu
		game.start();
	}
}