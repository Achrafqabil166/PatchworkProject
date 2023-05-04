package patchwork;

/* 
 * Representation of a patch
 */
public class Patch {
    private final int id;
    private final int cost;
    private final int value;
    private final int width;
    private final int height;

    /**
     * Creates a new patch with the given id, cost and value.
     * 
     * @param id     The id of the patch.
     * @param cost   The cost of the patch.
     * @param value  The value of the patch
     * @param width  The width of the patch.
     * @param height The height of the patch.
     */
    public Patch(int id, int cost, int value, int width, int height) {
        this.id = id;
        this.cost = cost;
        this.value = value;
        this.width = width; 
        this.height = height; 
    }

    /**
     * Returns the id of the patch.
     * 
     * @return The id of the patch.
     */
    public int getId() {
        return id;
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
     * Returns the value of the patch.
     * 
     * @return The value of the patch.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the width of the patch.
     * 
     * @return the width of the patch.
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
    
}