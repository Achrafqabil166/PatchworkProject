package patchwork;

import java.util.*;

/**
 * 
 * The Patchwork class represents the patchwork board of a player in the
 * Patchwork game. It consists of a 9x9 grid of Patches and tracks completed
 * rows and columns.
 */
public class Patchwork {
	private final Patch[][] grid;
	private int fullRows;
	private int fullCols;

	/**
	 * Constructs a new Patchwork object with an empty 9x9 grid and no completed
	 * rows or columns.
	 */
	public Patchwork() {
		grid = new Patch[9][9];
		fullRows = 0;
		fullCols = 0;
	}

	/**
	 * Checks if a given patch is available to be placed on the patchwork board.
	 * 
	 * @param piece The patch to check.
	 * @return True if the patch can be placed, false otherwise.
	 * @throws IllegalArgumentException if the patch is null.
	 */
	public boolean openPatch(Patch patch) {
		if (patch == null) {
			throw new IllegalArgumentException("Piece cannot be null");
		}
		var count = 0;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (grid[row][col] != null && grid[row][col].equals(patch)) {
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
	public int getFullRows() {
		return fullRows;
	}

	/**
	 * Gets the number of completed columns on the patchwork board.
	 * 
	 * @return The number of completed columns.
	 */
	public int getFullCols() {
		return fullCols;
	}

	/**
	 * Checks if the patchwork board is complete (all 81 patches have been placed).
	 * 
	 * @return True if the board is complete, false otherwise.
	 */
	public boolean isFull() {
		return fullRows == 9 && fullCols == 9;
	}

	/**
	 * Checks if a given row is complete (all 9 patches in the row have been
	 * placed).
	 * 
	 * @param row The row to check.
	 * @return True if the row is complete, false otherwise.
	 */
	private boolean isRowFull(int row) {
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
	private boolean isColFull(int col) {
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
	 * @param player The player who owns the patchwork.
	 * @param piece  The piece to add.
	 * @param row    The row to place the piece in.
	 * @param col    The column to place the piece in.
	 * @return True if the piece was added successfully, false otherwise.
	 */
	public boolean addToPatchwork(Player player, Patch piece, int row, int col) {
		var pieceGrid = piece.getGrid();
		var height = pieceGrid.length;
		var width = pieceGrid[0].length;
		// Check that the piece has a valid size
		if (height == 0 || width == 0) {
			System.out.println("Invalid patch size. The piece cannot be empty.");
			return false;
		}
		// Check that the piece can be placed at the specified coordinates
		if (row < 0 || col < 0 || row + height > 9 || col + width > 9) {
			System.out.println("Invalid coordinates. The piece cannot be placed at these coordinates.");
			return false;
		}
		// Check that the squares for the piece are free
		for (int i = row; i < row + height; i++) {
			for (int j = col; j < col + width; j++) {
				if (pieceGrid[i - row][j - col] && grid[i][j] != null) {
					return false;
				}
			}
		}
		// Check if the player has enough buttons to purchase the piece
		var pieceCost = piece.getCost();
		if (pieceCost > player.getButtons()) {
			return false;
		}
		// Add the piece to the patchwork
		for (int i = row; i < row + height; i++) {
			for (int j = col; j < col + width; j++) {
				if (pieceGrid[i - row][j - col]) {
					grid[i][j] = piece;
				}
			}
		}
		// Update the completed rows and columns
		fullRows = 0;
		fullCols = 0;
		for (int i = 0; i < 9; i++) {
			if (isRowFull(i)) {
				fullRows++;
			}
			if (isColFull(i)) {
				fullCols++;
			}
		}
		// Subtract the cost of the piece from the player's buttons
		player.payButtons(piece.getCost());
		return true;
	}

	public boolean movePiece(Patch piece, int currentX, int currentY, int newX, int newY) {
		// Check if the piece exists at the current coordinates
		if (grid[currentX][currentY] == piece) {
			// Check if the new coordinates are valid
			if (newX >= 0 && newX < 9 && newY >= 0 && newY < 9) {
				// Check if the new coordinates are empty
				if (grid[newX][newY] == null) {
					// Move the piece to the new coordinates
					grid[currentX][currentY] = null;
					grid[newX][newY] = piece;
					return true;
				} else {
					System.out.println("The new coordinates are already occupied.");
				}
			} else {
				System.out.println("Invalid new coordinates.");
			}
		} else {
			System.out.println("The piece does not exist at the current coordinates.");
		}
		return false;
	}

	public List<Patch> getPlacedPieces() {
		List<Patch> placedPieces = new ArrayList<>();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (grid[row][col] != null) {
					placedPieces.add(grid[row][col]);
				}
			}
		}
		return placedPieces;
	}

	public int getPieceX(Patch piece) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (grid[x][y] == piece) {
					return x;
				}
			}
		}
		return -1; // Piece not found
	}

	public int getPieceY(Patch piece) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (grid[x][y] == piece) {
					return y;
				}
			}
		}
		return -1; // Piece not found
	}

	/**
	 * Returns a string representation of the patchwork grid, including the patches
	 * and their positions.
	 * 
	 * @return A string representation of the patchwork grid.
	 */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		// Header row with column numbers
		builder.append(" ");
		for (int i = 0; i < 9; i++) {
			builder.append(String.format(" %-3d", i));
		}
		builder.append('\n');
		// Top border
		builder.append("  ╔");
		for (int i = 0; i < 8; i++) {
			builder.append("═══╦");
		}
		builder.append("═══╗\n");
		// Rows with patches
		for (int i = 0; i < 9; i++) {
			// Left border and row number
			builder.append(String.format("%-2d", i)).append("║");
			// Patches in the row
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] != null) {
					if (grid[i][j].toString().equals("#\n#\n#\n#")) {
						builder.append(" # \n# #\n # ");
					} else {
						builder.append(" # ");
					}
					builder.append("║");
				} else {
					builder.append("   ║");
				}
			}

			// Right border and row number
			builder.append(String.format("%2d", i)).append("\n");

			// Bottom border
			if (i < 8) {
				builder.append("  ╠");
				for (int j = 0; j < 8; j++) {
					builder.append("═══╬");
				}
				builder.append("═══╣\n");
			}
		}
		// Bottom border
		builder.append("  ╚");
		for (int i = 0; i < 8; i++) {
			builder.append("═══╩");
		}
		builder.append("═══╝\n");
		return builder.toString();
	}
}