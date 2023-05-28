package patchwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a circle of patches in the game.
 */
public class CircleOfPatches {
    private List<Patch> patches;
    private int neutralTokenIndex;

    /**
     * Constructs a CircleOfPatches object with the specified list of patches.
     * @param patches the list of patches
     * @throws IllegalArgumentException if the list of patches is null or empty
     */
    public CircleOfPatches(List<Patch> patches) {
        Objects.requireNonNull(patches, "patches is null");
        if (patches.isEmpty()) {
            throw new IllegalArgumentException("patches is empty");
        }
        this.patches = new ArrayList<>(patches);
        this.neutralTokenIndex = 0;
    }

    /**
     * Gets the patch at the current position of the neutral token.
     * @return the current patch
     */
    public Patch getCurrentPatch() {
        return patches.get(neutralTokenIndex);
    }

    /**
     * Checks if the circle of patches is empty.
     * @return true if the circle is empty, false otherwise
     */
    public boolean isEmpty() {
        return patches.isEmpty();
    }

    /**
     * Gets the total number of patches in the circle.
     * @return the total number of patches
     */
    public int getTotalPatchCount() {
        return patches.size();
    }

    /**
     * Gets the list of all patches in the circle.
     * @return the list of patches
     */
    public List<Patch> getAllPatches() {
        return new ArrayList<>(patches);
    }

    /**
     * Gets the current index of the neutral token.
     * @return the neutral token index
     */
    public int getNeutralTokenIndex() {
        return neutralTokenIndex;
    }

    /**
     * Moves the neutral token to the next patch in the circle.
     */
    public void moveNeutralToken() {
        neutralTokenIndex = (neutralTokenIndex + 1) % patches.size();
    }

    /**
     * Returns a list of the three patches in front of the neutral token.
     * @return the list of patches
     */
    public List<Patch> threePatchesFrontOfNeutralToken() {
        List<Patch> result = new ArrayList<>();
        int index = (neutralTokenIndex + 1) % patches.size();
        for (int i = 0; i < 3; i++) {
            result.add(patches.get(index));
            index = (index + 1) % patches.size();
        }
        return result;
    }
}
