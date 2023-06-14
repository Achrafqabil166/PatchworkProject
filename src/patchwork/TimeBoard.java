package patchwork;

import java.util.Arrays;

/**
 * 
 * A class that represents a time board in the Patchwork game. A time board is a
 * square grid of integers from 0 to (size^2 - 1), where size is the length of
 * each side. The value at each cell represents the order in which a player can
 * take a turn.
 */
public class TimeBoard {
	private final int[][] board;

	/**
	 * Creates a new time board of the specified size.
	 * 
	 * @param size the length of each side of the time board grid
	 * @throws IllegalArgumentException if size is less than or equal to 0
	 */
	public TimeBoard(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be positive");
		}
		board = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = i * size + j;
			}
		}
	}

	/**
	 * Returns the size of the time board grid.
	 * 
	 * @return the length of each side of the time board grid
	 */
	public int getSize() {
		return board.length;
	}

	/**
	 * Returns the value at the specified cell of the time board grid.
	 * 
	 * @param x the x coordinate of the cell
	 * @param y the y coordinate of the cell
	 * @return the value at the specified cell
	 * @throws IllegalArgumentException if the coordinates are invalid
	 */
	public int getValue(int x, int y) {
		if (x < 0 || x >= board.length || y < 0 || y >= board.length) {
			throw new IllegalArgumentException("Invalid coordinates");
		}
		return board[x][y];
	}

	/**
	 * Sets the value at the specified cell of the time board grid.
	 * 
	 * @param x     the x coordinate of the cell
	 * @param y     the y coordinate of the cell
	 * @param value the new value for the cell
	 * @throws IllegalArgumentException if the coordinates are invalid
	 */
	public void setValue(int x, int y, int value) {
		if (x < 0 || x >= board.length || y < 0 || y >= board.length) {
			throw new IllegalArgumentException("Invalid coordinates");
		}
		board[x][y] = value;
	}

	/**
	 * Returns a string representation of the time board grid.
	 * 
	 * @return a string representation of the time board grid
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			builder.append(Arrays.toString(board[i])).append("\n");
		}
		return builder.toString();
	}
}