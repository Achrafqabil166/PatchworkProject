package patchwork;
import patchwork.Patch;
import java.util.Objects;

/* 
 * Representation of a player
 */
public class Player {
	/*
	 * Le patchwork du joueur.
	 */
	private final Patchwork patchworkPlayer;

	/*
	 * name Le nom du joueur.
	 */
	private final String name;

	/*
	 * Le nombre de boutons du joueur.
	 */
	private int buttons;

	/*
	 * La position du joueur sur le time board.
	 */
	private int position;

	/*
	 * Indique si le joueur à finit son tour.
	 */
	private boolean done;

	/*
	 * Le temps restant du joueur.
	 */
	private int timePlayer;

	/**
	 * Crée un nouveau joueur qui est définit par son nom et son plateau de jeu.
	 * 
	 * @param name      Le nom du joueur
	 * @param patchwork Le patchwork du joueur
	 */
	public Player(String name, Patchwork patchworkPlayer) {
		this.name = Objects.requireNonNull(name, "Le nom ne doit pas être nul.");
		this.patchworkPlayer = Objects.requireNonNull(patchworkPlayer, "Le patchwork ne doit pas être nul.");
		this.buttons = 5; // Starting buttons
		this.position = 0; // Starting position
		this.done = false; // Not done at the beginning of the game
		this.timePlayer = 0; // Starting time
	}

	/**
	 * Retourne le nom du joueur.
	 * 
	 * @return Le nom du joueur.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retourne le Patchwork du joueur.
	 *
	 * @return Le Patchwork du joueur.
	 */
	public Patchwork getPatchworkPlayer() {
		return patchworkPlayer;
	}

	/**
	 * Retourne le nombre de boutons du joueur.
	 *
	 * @return Le nombre de boutons du joueur.
	 */
	public int getButtons() {
		return buttons;
	}

	/**
	 * Retourne le temps restant du joueur.
	 * 
	 * @return Le temps restant du joueur.
	 */
	public int getTimePlayer() {
		return timePlayer;
	}

	/**
	 * Retourne la position du joueur sur le time board.
	 *
	 * @return La position du joueur sur le time board.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Modifie la position du joueur sur le time board.
	 *
	 * @param position La nouvelle position du joueur.
	 */
	public void setPosition(int position) {
	    if (position < 0) {
	        throw new IllegalArgumentException("La position ne peut pas être négative.");
	    }
	    this.position = position;
	}

	/**
	 * Retourne true si le joueur a finit son tour.
	 *
	 * @return True si le joueur a finit son tour, false sinon.
	 */
	public boolean getDone() {
		return done;
	}
	
	public void setDone(boolean done) {
	    this.done = done;
	}


	/**
	 * Retourne le patchwork du joueur.
	 * 
	 * @return Le patchwork du joueur.
	 */
	public Patchwork getQuilt() {
		return patchworkPlayer;
	}

	/**
	 * Ajoute un certain nombre de boutons au joueur.
	 * 
	 * @param n Le nombre de boutons à ajouter.
	 */
	public void addButtons(int nbButtons) {
		if (nbButtons < 0) {
			throw new IllegalArgumentException("The player needs to acquire a positive number of buttons");
		}
		buttons += nbButtons;
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
			buttons += patch.getValue();
			timePlayer += patch.getCost();
		}
		return added;
	}

	/**
	 * Vérifie si une certaine pièce de tissu est disponible pour le joueur.
	 * 
	 * @param piece La pièce de tissu à vérifier.
	 * @return True si la pièce est disponible, false sinon.
	 */
	public boolean isPieceAvailable(Patch piece) {
		return patchworkPlayer.isPieceAvailable(piece);
	}
}
