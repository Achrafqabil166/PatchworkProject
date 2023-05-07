package patchwork;

import java.util.Objects;

/**
 * 
 * A representation of a player in the Patchwork game. Each player has a
 * patchwork, a name, a number of buttons, a position on the time board, a flag
 * to indicate if they have finished their turn, and a time remaining.
 */
public class Player {
// Fields
	private final Patchwork patchworkPlayer;
	private final String name;
	private int buttons;
	private int position;
	private boolean done;
	private int timePlayer;

	/**
	 * Creates a new player with the specified name and patchwork.
	 * 
	 * @param name            The name of the player.
	 * @param patchworkPlayer The patchwork of the player.
	 * @throws NullPointerException if either name or patchworkPlayer is null.
	 */
	public Player(String name, Patchwork patchworkPlayer) {
		this.name = Objects.requireNonNull(name, "Name must not be null.");
		this.patchworkPlayer = Objects.requireNonNull(patchworkPlayer, "Patchwork must not be null.");
		this.buttons = 5;
		this.position = 0;
		this.done = false;
		this.timePlayer = 0;
	}

	/**
	 * Gets the name of the player.
	 * 
	 * @return The name of the player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the patchwork of the player.
	 * 
	 * @return The patchwork of the player.
	 */
	public Patchwork getPatchworkPlayer() {
		return patchworkPlayer;
	}

	/**
	 * Gets the number of buttons of the player.
	 * 
	 * @return The number of buttons of the player.
	 */
	public int getButtons() {
		return buttons;
	}

	/**
	 * Gets the time remaining of the player.
	 * 
	 * @return The time remaining of the player.
	 */
	public int getTimePlayer() {
		return timePlayer;
	}

	/**
	 * Gets the position of the player on the time board.
	 * 
	 * @return The position of the player on the time board.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Sets the position of the player on the time board.
	 * 
	 * @param position The new position of the player on the time board.
	 * @throws IllegalArgumentException if the position is negative.
	 */
	public void setPosition(int position) {
		if (position < 0) {
			throw new IllegalArgumentException("Position cannot be negative.");
		}
		this.position = position;
	}

	/**
	 * Returns true if the player has finished their turn.
	 * 
	 * @return True if the player has finished their turn, false otherwise.
	 */
	public boolean getDone() {
		return done;
	}

	/**
	 * Sets whether the player has finished their turn.
	 * 
	 * @param done True if the player has finished their turn, false otherwise.
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * Gets the patchwork of the player.
	 * 
	 * @return The patchwork of the player.
	 * @deprecated This method is deprecated and should not be used.
	 */
	@Deprecated
	public Patchwork getQuilt() {
		return patchworkPlayer;
	}

	/**
	 * Adds a certain number of buttons to the player.
	 * 
	 * @param nbButtons The number of buttons to add.
	 * @throws IllegalArgumentException if nbButtons is not positive or if the cost
	 *                                  of a patch is greater than the number of
	 *                                  buttons.
	 */
	public void addButtons(int nbButtons) {
		if (nbButtons <= 0) {
			throw new IllegalArgumentException("The player needs to acquire a positive number of buttons.");
		}
		if (nbButtons > buttons) {
			throw new IllegalArgumentException("cost > buttons");
		}
		buttons -= nbButtons;
	}

	/**
	 * Ajoute un certain nombre de secondes au temps restant du joueur.
	 * 
	 * @param n Le nombre de secondes à ajouter.
	 */
	public void addTimePlayer(int n) {
		timePlayer += n;
	}

	/**
	 * Ajoute une pièce de tissu au patchwork du joueur.
	 * 
	 * @param piece La pièce de tissu à ajouter.
	 * @param row   La rangée à laquelle ajouter la pièce.
	 * @param col   La colonne à laquelle ajouter la pièce.
	 * @return True si la pièce a été ajoutée avec succès, false sinon.
	 */
	public boolean addToPatchwork(Patch patch, int x, int y) {
		boolean added = patchworkPlayer.addToPatchwork(patch, x, y);
		if (added) {
			buttons += patch.getCost();
			timePlayer += patch.getTime();
		}
		return added;
	}

	/**
	 * Checks if a certain piece of fabric is available to the player.
	 * 
	 * @param piece The piece of cloth to check.
	 * @return True if the piece is available, false otherwise.
	 */
	public boolean isPieceAvailable(Patch piece) {
		return patchworkPlayer.isPieceAvailable(piece);
	}

	public int advanceAndReceiveButtons(Player other, TimeBoard timeBoard) {
		Objects.requireNonNull(other);
		Objects.requireNonNull(timeBoard);

		int destination = other.getPosition() + 1;

		// Add a button for each browsed square
		buttons += (destination - position);

		// Si le joueur atteint la dernière case, il perd un bouton
		if (destination == timeBoard.getSize()) {
			buttons--;
		}

		// If the player reaches the last square, he loses a button
		setPosition(destination);

		return 0;
	}

	/**
	 * 
	 * Returns a string representation of the player object. The string contains the
	 * player's name, their number of buttons, and their position on the time board.
	 * The position is represented by a number of spaces in the beginning of the
	 * string, based on the player's position.
	 * 
	 * @return A string representation of the player object.
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		for (var i = 0; i < position * 4 + 1; i++) {
			builder.append(" ");
		}
		builder.append("P").append(name).append(" (").append(buttons).append(" buttons)");
		return builder.toString();
	}
}