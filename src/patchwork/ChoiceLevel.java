/**
 * ChoiceLevel class - allows the user to choose the game level.
 */
package patchwork;

import java.util.Scanner;

/**
 * The ChoiceLevel class represents a game level selection menu, allowing the user to choose the desired game level.
 */
public class ChoiceLevel {

    private final Scanner scanner;

    /**
     * Default constructor for ChoiceLevel. Initializes the scanner that will read
     * user input.
     */
    public ChoiceLevel() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Overrides the toString method to display the game level options.
     *
     * @return A message indicating how the user can choose the game level.
     */
    @Override
    public String toString() {
        return "Choose your game level: (1) Phase 1 = base game / (2) Phase 2 = complete game / (0) Exit";
    }

    /**
     * Method to read the user's choice of game level.
     *
     * @return An integer representing the game level chosen by the user.
     * @throws IllegalArgumentException if the user enters a value other than 0, 1,
     *                                  or 2.
     */
    public int choose() {
        int choice = scanner.nextInt();
        if (choice < 0 || choice > 2) {
            throw new IllegalArgumentException("Unexpected value: " + choice);
        }
        return choice;
    }
}
