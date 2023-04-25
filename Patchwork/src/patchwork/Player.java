package patchwork;

public class Player {

	private final int INITIALFUNDS = 5;

	private final int EARLYSPACES = 81;

	private final int INITIALTIMING = 53;

	/*
	 * L'id du joueur
	 */
	public long id;

	/*
	 * Le prix des buttons en disposition du joueur
	 */
	public int prix;

	/*
	 * Nombre de bouttons appartenant au joueur
	 */
	public int buttons;

	/*
	 * Position de token du joueur sur le plateau de jeu
	 */
	public int tokenPosition;

	/*
	 * Nombre de revenus du joueur
	 */
	public int income;

	/* 
	 * Temps 
	 */
	public int time;

	/*
	 * Test si le joueur a finit sa partie 
	 */
	public boolean finished;

	public PlayerBoard board;

	
	public long getId() {
		return id;
	}

	public int getButtons() {
		return buttons;
	}

	public int getTokenPosition() {
		return tokenPosition;
	}

	public int getIncome() {
		return income;
	}

	public PlayerBoard getBoard() {
		return board;
	}

	public Player() {
		if (id < 0) {
			throw new IllegalArgumentException("id of polayer == null");
		}
		prix = INITIALFUNDS;
		buttons = 0;
		time = INITIALTIMING;
		finished = false;
	}

}
