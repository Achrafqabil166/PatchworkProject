package patchwork;

import java.util.Arrays;

/**
 * Representation of a patch in the Patchwork game.
 */
public class Patch {
	private final int cost; // The cost of the patch.
	private final int time; // The cost of the patch in time.
	private final int income; // The income of the patch in buttons.
	private final boolean[][] grid; // The grid representing the patch.
	private final int width; // The width of the patch grid.
	private final int height; // The height of the patch grid.

	/**
	 * Creates a new patch with the given cost, time, income, and grid.
	 *
	 * @param cost   The cost of the patch.
	 * @param time   The cost of the patch in time.
	 * @param income The income of the patch in buttons.
	 * @param grid   The grid representing the patch.
	 * @param width  The width of the patch grid.
	 * @param height The height of the patch grid.
	 */
	public Patch(int cost, int time, int income, boolean[][] grid, int width, int height) {
		this.cost = cost;
		this.time = time;
		this.income = income;
		this.grid = grid;
		this.height = grid.length;
		if (grid != null && grid.length > 0) {
			this.width = grid[0].length;
		} else {
			this.width = 0;
		}
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
	 * Returns the cost of the patch in time.
	 *
	 * @return The cost of the patch in time.
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Returns the income of the patch in buttons.
	 *
	 * @return The income of the patch in buttons.
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
	 * Returns the width of the patch.
	 *
	 * @return The width of the patch.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the patch.
	 *
	 * @return The height of the patch.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns a string representation of the patch with its graphical
	 * representation and information about its cost and income.
	 *
	 * @return A string representation of the patch.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(cost).append(",").append(time).append(",").append(income).append("]");
		builder.append("\n");
		for (int i = 0; i < height; i++) {
			builder.append("|");
			for (int j = 0; j < width; j++) {
				builder.append(grid[i][j] ? "#" : " ");
			}
			builder.append("|");
			builder.append("\n");
		}
		return builder.toString();
	}

	/**
	 * Checks if a square at the specified coordinates is occupied in the patch.
	 *
	 * @param x The x-coordinate of the square.
	 * @param y The y-coordinate of the square.
	 * @return true if the square is occupied, false otherwise.
	 */
	public boolean squareOccupied(int

	x, int y) {
		return grid[y][x];
	}

	/**
	 * Checks if the patch overlaps with another patch.
	 *
	 * @param other The other patch to check for overlap.
	 * @return true if the patches overlap, false otherwise.
	 */
	public boolean patchOverlaps(Patch other) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j] && other.squareOccupied(j, i)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the patch is empty (contains no occupied squares).
	 *
	 * @return true if the patch is empty, false otherwise.
	 */
	public boolean isEmpty() {
		for (boolean[] row : grid) {
			for (boolean square : row) {
				if (square) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the total size (surface) of the patch.
	 *
	 * @return The total size of the patch.
	 */
	public int getSize() {
		return width * height;
	}

	/**
	 * Returns the value at the specified cell in the patch grid.
	 *
	 * @param row The row index of the cell.
	 * @param col The column index of the cell.
	 * @return The value at the specified cell.
	 * @throws IllegalArgumentException if the row or column indices are out of
	 *                                  bounds.
	 */
	public boolean getGridValue(int row, int col) {
		if (row < 0 || row >= height || col < 0 || col >= width) {
			throw new IllegalArgumentException("Invalid row or column index.");
		}
		return grid[row][col];
	}

	/**
	 * Sets the grid representing the patch.
	 *
	 * @param newGrid The new grid representing the patch.
	 */
	public void setGrid(boolean[][] newGrid) {
		if (newGrid.length != height || newGrid[0].length != width) {
			throw new IllegalArgumentException("Invalid grid dimensions.");
		}
		for (int i = 0; i < height; i++) {
			grid[i] = Arrays.copyOf(newGrid[i], width);
		}
	}

	/**
	 * Returns a new Patch instance with the grid rotated by a given degree.
	 *
	 * @param degree The degree of rotation (90, 180, or 270 degrees).
	 * @return A new Patch instance with the rotated grid.
	 * @throws IllegalArgumentException if the degree is not one of 90, 180, or 270.
	 */
	public Patch rotate(int degree) {
		if (degree != 90 && degree != 180 && degree != 270) {
			throw new IllegalArgumentException("Invalid degree of rotation. Must be one of 90, 180, or 270.");
		}

		boolean[][] rotatedGrid;

		switch (degree) {
		case 90:
			rotatedGrid = new boolean[width][height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					rotatedGrid[x][height - y - 1] = grid[y][x];
				}
			}
			break;
		case 180:
			rotatedGrid = new boolean[height][width];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					rotatedGrid[height - y - 1][width - x -

							1] = grid[y][x];
				}
			}
			break;
		case 270:
			rotatedGrid = new boolean[width][height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					rotatedGrid[width - x - 1][y] = grid[y][x];
				}
			}
			break;
		default:
			// Should never reach here due to the initial check.
			throw new IllegalArgumentException("Invalid degree of rotation. Must be one of 90, 180, or 270.");
		}

		return new Patch(cost, time, income, rotatedGrid, rotatedGrid[0].length, rotatedGrid.length);
	}
}