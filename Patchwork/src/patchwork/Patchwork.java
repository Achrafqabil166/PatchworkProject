package patchwork;

public class Patchwork {
	private final Patch[][] grid;
	private int completedRows;
	private int completedCols;

	public Patchwork() {
		grid = new Patch[9][9];
		completedRows = 0;
		completedCols = 0;
	}

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

	public int getCompletedRows() {
		return completedRows;
	}

	public int getCompletedCols() {
		return completedCols;
	}

	public boolean isComplete() {
		return completedRows == 9 && completedCols == 9;
	}

	private boolean isRowCompleted(int row) {
		for (int col = 0; col < 9; col++) {
			if (grid[row][col] == null) {
				return false;
			}
		}
		return true;
	}

	private boolean isColCompleted(int col) {
		for (int row = 0; row < 9; row++) {
			if (grid[row][col] == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Ajoute la pièce au patchwork du joueur.
	 *
	 * @param piece La pièce à ajouter.
	 * @param row   La ligne où placer la pièce.
	 * @param col   La colonne où placer la pièce.
	 * @return true si la pièce a été ajoutée avec succès, false sinon.
	 */
	public boolean addToPatchwork(Patch piece, int row, int col) {
		boolean[][] pieceGrid = piece.getGrid();
		int height = pieceGrid.length;
		int width = pieceGrid[0].length;

		// Vérifie que la pièce peut être placée aux coordonnées spécifiées
		if (row + 1 > 9 || col + 1 > 9) {
			return false;
		}

		// Vérifie que les cases de la pièce sont libres
		for (int i = row; i < row + 2; i++) {
			for (int j = col; j < col + 2; j++) {
				if (pieceGrid[i - row][j - col] && grid[i][j] != null) {
					return false;
				}
			}
		}

		// Ajoute la pièce au patchwork
		for (int i = row; i < row + 2; i++) {
			for (int j = col; j < col + 2; j++) {
				grid[i][j] = piece;
			}
		}

		// Mettre à jour les lignes et colonnes complétées
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