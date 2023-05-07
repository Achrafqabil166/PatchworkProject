package patchwork;

/**
 * 
 * The Patchwork class represents the patchwork board of a player in the
 * Patchwork game. It consists of a 9x9 grid of Patches and tracks completed
 * rows and columns.
 */
public class Patchwork {
	private final Patch[][] grid;
	private int completedRows;
	private int completedCols;

	/**
	 * Constructs a new Patchwork object with an empty 9x9 grid and no completed
	 * rows or columns.
	 */
	public Patchwork() {
		grid = new Patch[9][9];
		completedRows = 0;
		completedCols = 0;
	}

	/**
	 * Checks if a given patch is available to be placed on the patchwork board.
	 * 
	 * @param piece The patch to check.
	 * @return True if the patch can be placed, false otherwise.
	 * @throws IllegalArgumentException if the patch is null.
	 */
	public boolean isPieceAvailable(Patch piece) {
		if (piece == null) {
			throw new IllegalArgumentException("Piece cannot be null");
		}
		int count = 0;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (grid[row][col] != null && grid[row][col].equals(piece)) {
					count++;
				}
			}
		}
		return count < 20;
	}

	/**
	 * Gets the number of completed rows on the patchwork board.
	 * 
	 * @return The number of completed rows.
	 */
	public int getCompletedRows() {
		return completedRows;
	}

	/**
	 * Gets the number of completed columns on the patchwork board.
	 * 
	 * @return The number of completed columns.
	 */
	public int getCompletedCols() {
		return completedCols;
	}

	/**
	 * Checks if the patchwork board is complete (all 81 patches have been placed).
	 * 
	 * @return True if the board is complete, false otherwise.
	 */
	public boolean isComplete() {
		return completedRows == 9 && completedCols == 9;
	}

	/**
	 * Checks if a given row is complete (all 9 patches in the row have been
	 * placed).
	 * 
	 * @param row The row to check.
	 * @return True if the row is complete, false otherwise.
	 */
	private boolean isRowCompleted(int row) {
		for (int col = 0; col < 9; col++) {
			if (grid[row][col] == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if a given column is complete (all 9 patches in the column have been
	 * placed).
	 * 
	 * @param col The column to check.
	 * @return True if the column is complete, false otherwise.
	 */
	private boolean isColCompleted(int col) {
		for (int row = 0; row < 9; row++) {
			if (grid[row][col] == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds the given piece to the player's patchwork.
	 *
	 * @param piece The piece to add.
	 * @param row   The row to place the piece in.
	 * @param col   The column to place the piece in.
	 * @return True if the piece was added successfully, false otherwise.
	 */
	public boolean addToPatchwork(Patch piece, int row, int col) {
		boolean[][] pieceGrid = piece.getGrid();
		int height = pieceGrid.length;
		int width = pieceGrid[0].length;

		// Check that the piece can be placed at the specified coordinates
		if (row + 1 > 9 || col + 1 > 9) {
			return false;
		}

		// Check that the squares for the piece are free
		for (int i = row; i < row + 2; i++) {
			for (int j = col; j < col + 2; j++) {
				if (pieceGrid[i - row][j - col] && grid[i][j] != null) {
					return false;
				}
			}
		}

		// Add the piece to the patchwork
		for (int i = row; i < row + 2; i++) {
			for (int j = col; j < col + 2; j++) {
				grid[i][j] = piece;
			}
		}

		// Update the completed rows and columns
		for (int i = 0; i < 9; i++) {
			if (isRowCompleted(i)) {
				completedRows++;
			}
			if (isColCompleted(i)) {
				completedCols++;
			}
		}

		return true;
	}

	/**
	 * Returns a string representation of the patchwork grid, including the patches
	 * and their positions.
	 *
	 * @return A string representation of the patchwork grid.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// Header row with column numbers
		sb.append("   ");
		for (int i = 0; i < 9; i++) {
			sb.append(String.format(" %-3d", i));
		}
		sb.append('\n');

		// Top border
		sb.append("  ╔");
		for (int i = 0; i < 8; i++) {
			sb.append("═══╦");
		}
		sb.append("═══╗\n");

		// Rows with patches
		for (int i = 0; i < 9; i++) {
			// Left border and row number
			sb.append(String.format("%-2d", i)).append("║");

			// Patches in the row
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] != null) {
					if (grid[i][j].toString().equals("#\n#\n#\n#")) {
						sb.append(" # \n# #\n # ");
					} else {
						sb.append(" # ");
					}
					sb.append("║");
				} else {
					sb.append("   ║");
				}
			}

			// Right border and row number
			sb.append(String.format("%2d", i)).append("\n");

			// Bottom border
			if (i < 8) {
				sb.append("  ╠");
				for (int j = 0; j < 8; j++) {
					sb.append("═══╬");
				}
				sb.append("═══╣\n");
			}
		}

		// Bottom border
		sb.append("  ╚");
		for (int i = 0; i < 8; i++) {
			sb.append("═══╩");
		}
		sb.append("═══╝\n");

		return sb.toString();
	}

}