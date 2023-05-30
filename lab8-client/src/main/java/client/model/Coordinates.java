package client.model;

/**
 * A class representing the coordinates of a point on a two-dimensional plane.
 */
public class Coordinates {
    private Float x; // Поле не может быть null
    private Float y; // Поле не может быть null

    /**
     * Returns the x coordinate of the point.
     * 
     * @return the x coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x coordinate of the point to the given value.
     * 
     * @param x the new x coordinate
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate of the point.
     * 
     * @return the y coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y coordinate of the point to the given value.
     * 
     * @param y the new y coordinate
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Returns a string representation of the coordinates in the format
     * "Coordinates{x=x, y=y}".
     * 
     * @return a string representation of the coordinates
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
