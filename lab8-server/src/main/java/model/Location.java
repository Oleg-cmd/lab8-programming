package model;
/**
 A class representing a location with a name and 2D coordinates.
 */
public class Location {
    private double x;
    private double y;
    private String name; //Строка не может быть пустой, Поле не может быть null
    /**
     * Sets the location's coordinates and name.
     * @param x the x-coordinate of the location
     * @param y the y-coordinate of the location
     * @param name the name of the location (must not be null or empty)
     */
    public void setLocation(double x, double y, String name) {
        setName(name);
        this.x = x;
        this.y = y;
    }
    /**
     * Gets the x-coordinate of the location.
     * @return the x-coordinate of the location
     */
    public double getX() {
        return x;
    }
    /**
     * Gets the y-coordinate of the location.
     * @return the y-coordinate of the location
     */
    public double getY() {
        return y;
    }
    /**
     * Gets the name of the location.
     * @return the name of the location
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the location.
     * @param name the name of the location (must not be null or empty)
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }
    /**
     * Returns a string representation of the location.
     * @return a string representation of the location
     */
    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
