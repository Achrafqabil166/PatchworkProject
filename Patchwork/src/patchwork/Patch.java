package patchwork;

public class Patch {

	private int prix; 
	
	private int income; 
	
	private int time; 
	
	private int id; 
	
	private int tiles;

	public Patch(int prix, int income, int time, int id, int tiles) {
		super();
		this.prix = prix;
		this.income = income;
		this.time = time;
		this.id = id;
		this.tiles = tiles;
	}

	public int getPrix() {
		return prix;
	}

	public int getIncome() {
		return income;
	}

	public int getTime() {
		return time;
	}

	public int getId() {
		return id;
	}

	public int getTiles() {
		return tiles;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	
	
}
