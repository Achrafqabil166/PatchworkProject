package patchwork;

import java.util.ArrayList;
import java.util.List;

/* 
 * Representation of a patch
 */
public class Patch {
	private final int cost;
	private final int time;
	private final int income;
	private final boolean[][] grid;
	private final int width;
	private final int height;

	/**
	 * Creates a new patch with the given cost, time, income, and grid.
	 * 
	 * @param cost   The cost of the patch.
	 * @param time   The cost of the patch in time
	 * @param income The income of the patch in buttons
	 * @param grid   The grid representing the patch.
	 */
	public Patch(int cost, int time, int income, boolean[][] grid, int width, int height) {
		this.cost = cost;
		this.time = time;
		this.income = income;
		this.grid = grid;
		this.height = grid.length;
		this.width = grid[0].length;
	}

	/**
	 * Returns the cost of the patch.
	 * 
	 * @return The cost of the patch.
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Returns the cost of the patch in time
	 * 
	 * @return The cost of the patch in time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Returns the income of the patch in buttons
	 * 
	 * @return The income of the patch in buttons
	 */
	public int getIncome() {
		return income;
	}

	/**
	 * Returns the grid representing the patch.
	 * 
	 * @return The grid representing the patch.
	 */
	public boolean[][] getGrid() {
		return grid;
	}


	/**
	 * Returns a string representation of the patch with its graphical
	 * representation and information about its cost and income.
	 *
	 * @return A string representation of the patch.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(cost).append(",").append(time).append(",").append(income).append("]");
		sb.append("\n");
		for (int i = 0; i < height; i++) {
			sb.append("|");
			for (int j = 0; j < width; j++) {
				sb.append(grid[i][j] ? "#" : " ").append("|");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}