package patchwork;

public class Patchwork {
	private final Patch[][] grid;
	private int completedRows;
	private int completedCols;

	public Patchwork() {
		grid = new Patch[10][10];
		completedRows = 0;
		completedCols = 0;
	}


	public boolean isPieceAvailable(Patch piece) {
		if (piece == null) {
			throw new IllegalArgumentException("Piece cannot be null");
		}

		int count = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (grid[row][col] != null && grid[row][col].equals(piece)) {
					count++;
				}
			}
		}

		return count < 20;
	}

	public int getCompletedRows() {
		return completedRows;
	}

	public int getCompletedCols() {
		return completedCols;
	}

	public boolean isComplete() {
		return completedRows == 10 && completedCols == 10;
	}

	private boolean isRowCompleted(int row) {
		for (int col = 0; col < 10; col++) {
			if (grid[row][col] == null) {
				return false;
			}
		}
		return true;
	}

	private boolean isColCompleted(int col) {
		for (int row = 0; row < 10; row++) {
			if (grid[row][col] == null) {
				return false;
			}
		}
		return true;
	}

	public boolean addToPatchwork(Patch patch, int x, int y) {
		if (patch == null) {
			throw new IllegalArgumentException("Piece cannot be null");
		}
		if (x < 0 || x > 9 || y < 0 || y > 9) {
			throw new IllegalArgumentException("Invalid coordinates: (" + x + ", " + y + ")");
		}
		if (grid[x][y] != null) {
			return false;
		}

		// Check if there are adjacent pieces
		if ((x == 0 || grid[x - 1][y] != null) && (y == 0 || grid[x][y - 1] != null)
				&& (x == 9 || grid[x + 1][y] != null) && (y == 9 || grid[x][y + 1] != null)) {
			return false;
		}

		// Add the piece to the grid
		grid[x][y] = patch;

		// Update the completed rows and columns counters
		if (isRowCompleted(x)) {
			completedRows++;
		}
		if (isColCompleted(y)) {
			completedCols++;
		}

		return true;
	}
}
