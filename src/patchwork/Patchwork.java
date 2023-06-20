package patchwork;

import java.util.*;

/**
 * The Patchwork class represents the patchwork board of a player in the Patchwork game. It consists of a 9x9 grid of Patches and tracks completed rows and columns.
 */
public class Patchwork {
    private final Patch[][] grid; // 9x9 grid to hold the patchwork.
    private int fullRows; // The number of complete rows in the grid.
    private int fullCols; // The number of complete columns in the grid.

    /**
     * Constructs a new Patchwork object with an empty 9x9 grid and no completed rows or columns.
     */
    public Patchwork() {
        grid = new Patch[9][9];
        fullRows = 0;
        fullCols = 0;
    }

    /**
     * Checks if a given patch is available to be placed on the patchwork board.
     *
     * @param patch The patch to check.
     * @return True if the patch can be placed, false otherwise.
     * @throws IllegalArgumentException if the patch is null.
     */
    public boolean openPatch(Patch patch) {
        if (patch == null) {
            throw new IllegalArgumentException("Patch cannot be null");
        }
        int count = 0;
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
     * Checks if a given row is complete (all 9 patches in the row have been placed).
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
     * Checks if a given column is complete (all 9 patches in the column have been placed).
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
     * @throws IllegalArgumentException if the player or piece is null, or if the row or column is out of bounds.
     */
    public boolean addToPatchwork(Player player, Patch piece, int row, int col) {
        Objects.requireNonNull(player, "Player cannot be null");
        Objects.requireNonNull(piece, "Piece cannot be null");

        if (row < 0 || row >= 9) {
            throw new IllegalArgumentException("Row must be between 0 and 8");
        }
        if (col < 0 || col >= 9) {
            throw new IllegalArgumentException("Column must be between 0 and 8");
        }

        boolean[][] pieceGrid = piece.getGrid();
        int height = pieceGrid.length;
        int width = pieceGrid[0].length;

        if (height == 0 || width == 0) {
            throw new IllegalArgumentException("Invalid patch size. The piece cannot be empty.");
        }

        if (row + height > 9 || col + width > 9) {
            throw new IllegalArgumentException("Invalid coordinates. The piece cannot be placed at these coordinates.");
        }

        for (int i = row; i < row + height; i++) {
            for (int j = col; j < col + width; j++) {
                if (pieceGrid[i - row][j - col] && grid[i][j] != null) {
                    return false;
                }
            }
        }

        int pieceCost = piece.getCost();
        if (pieceCost > player.getButtons()) {
            return false;
        }

        for (int i = row; i < row + height; i++) {
            for (int j = col; j < col + width; j++) {
                if (pieceGrid[i - row][j - col]) {
                    grid[i][j] = piece;
                }
            }
        }

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

        player.payButtons(piece.getCost());
        return true;
    }

    /**
     * Moves the given piece to a new position on the patchwork board.
     *
     * @param piece     The piece to move.
     * @param currentX  The current x-coordinate of the piece.
     * @param currentY  The current y-coordinate of the piece.
     * @param newX      The new x-coordinate to move the piece to.
     * @param newY      The new y-coordinate to move the piece to.
     * @return True if the piece was moved successfully, false otherwise.
     * @throws IllegalArgumentException if the piece is null or if the coordinates are out of bounds.
     */
    public boolean movePiece(Patch piece, int currentX, int currentY, int newX, int newY) {
        Objects.requireNonNull(piece, "Piece cannot be null");

        if (currentX < 0 || currentX >= 9 || currentY < 0 || currentY >= 9) {
            throw new IllegalArgumentException("Current coordinates must be between 0 and 8");
        }
        if (newX < 0 || newX >= 9 || newY < 0 || newY >= 9) {
            throw new IllegalArgumentException("New coordinates must be between 0 and 8");
        }

        if (grid[currentX][currentY] != piece) {
            throw new IllegalArgumentException("The piece does not exist at the current coordinates");
        }

        if (grid[newX][newY] != null) {
            throw new IllegalArgumentException("The new coordinates are already occupied");
        }

        grid[currentX][currentY] = null;
        grid[newX][newY] = piece;

        return true;
    }

    /**
     * Returns a list of all placed pieces on the patchwork board.
     *
     * @return A list of placed pieces.
     */
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

    /**
     * Returns the x-coordinate of the given piece on the patchwork board.
     *
     * @param piece The piece to find the x-coordinate of.
     * @return The x-coordinate of the piece, or -1 if the piece is not found.
     */
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

    /**
     * Returns the y-coordinate of the given piece on the patchwork board.
     *
     * @param piece The piece to find the y-coordinate of.
     * @return The y-coordinate of the piece, or -1 if the piece is not found.
     */
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
     * Returns a string representation of the patchwork grid, including the patches and their positions.
     *
     * @return A string representation of the patchwork grid.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        // Header row with column numbers
        builder.append("   ");
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
                    builder.append(" # ");
                } else {
                    builder.append("   ");
                }
                builder.append("║");
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
