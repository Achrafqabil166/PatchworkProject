package patchwork;

import java.util.Objects;

/**
 * 
 * A representation of a player in the Patchwork game. Each player has a
 * patchwork, a name, a number of buttons, a position on the time board, a flag
 * to indicate if they have finished their turn, and a time remaining.
 */
public class Player {
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

	public void addTimePlayer(int n) {
		timePlayer += n;
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
	 * Pay cost buttons
	 *
	 * @param cost A cost less than the player's buttons
	 */
	public void payButtons(int cost) {
		if (cost < 0) {
			throw new IllegalArgumentException("cost < 0");
		}
		if (cost > buttons) {
			throw new IllegalArgumentException("cost > buttons");
		}
		buttons -= cost;
	}

	/**
	 * Move to the other Player's token position + 1 and receive one button per
	 * space moved.
	 *
	 * @param other     The
	 * 
	 *                  other player.
	 * @param timeBoard The time board of the game.
	 * @return Number of special patches to place.
	 */
	public int advanceAndReceiveButtons(Player other, TimeBoard timeBoard) {
		Objects.requireNonNull(other);
		Objects.requireNonNull(timeBoard);

		var destination = other.getPosition() + 1;

		buttons += (destination - position);

		if (destination == timeBoard.getSize()) {
			buttons--;
		}

		setPosition(destination);

		return (destination - position);
	}

	/**
	 * Checks if a certain piece of fabric is available to the player.
	 *
	 * @param piece The piece of cloth to check.
	 * @return True if the piece is available, false otherwise.
	 */
	public boolean openPatch(Patch patch) {
		return patchworkPlayer.openPatch(patch);
	}

	/**
	 * Returns a representation of the player object. 
	 * It's contains the player's name, their number of buttons, and their position on the time board.
	 *
	 * @return A string representation of the player object.
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		for (int i = 0; i < position * 4 + 1; i++) {
			builder.append(" ");
		}
		builder.append("P").append(name).append(" (").append(buttons).append(" buttons)");
		return builder.toString();
	}
}