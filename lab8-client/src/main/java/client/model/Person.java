package client.model;

/**
 * Represents a person with name, birthday, height, eye color, and location.
 */
public class Person {
    private String name; // Поле не может быть null, Строка не может быть пустой
    private java.time.ZonedDateTime birthday; // Поле не может быть null
    private double height; // Значение поля должно быть больше 0
    private Color eyeColor; // Поле не может быть null
    private Location location; // Поле не может быть null

    public Person(String name, java.time.ZonedDateTime birthday, double height, Color eyeColor, Location location) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.eyeColor = eyeColor;
        this.location = location;
    }

    /**
     * Sets the name of the person.
     * 
     * @param name the name of the person
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Sets the birthday of the person.
     * 
     * @param birthday the birthday of the person
     * @throws IllegalArgumentException if the birthday is null
     */
    public void setBirthday(java.time.ZonedDateTime birthday) {
        if (birthday == null) {
            throw new IllegalArgumentException("Birthday cannot be null");
        }
        this.birthday = birthday;
    }

    /**
     * Sets the height of the person.
     * 
     * @param height the height of the person
     * @throws IllegalArgumentException if the height is less than or equal to 0
     */
    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        this.height = height;
    }

    /**
     * Sets the eye color of the person.
     * 
     * @param eyeColor the eye color of the person
     * @throws IllegalArgumentException if the eye color is null
     */
    public void setEyeColor(Color eyeColor) {
        if (eyeColor == null) {
            throw new IllegalArgumentException("Eye color cannot be null");
        }
        this.eyeColor = eyeColor;
    }

    /**
     * Sets the location of the person.
     * 
     * @param location the location of the person
     * @throws IllegalArgumentException if the location is null
     */
    public void setLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        this.location = location;
    }

    /**
     * Returns the name of the person.
     * 
     * @return the name of the person
     */
    // Getter methods for all fields
    public String getName() {
        return name;
    }

    /**
     * Returns the birthday of the person.
     * 
     * @return the birthday of the person
     */
    public java.time.ZonedDateTime getBirthday() {
        return birthday;
    }

    /**
     * Returns the height of the person.
     * 
     * @return the height of the person
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the eye color of the person.
     * 
     * @return the eye color of the person
     */
    public Color getEyeColor() {
        return eyeColor;
    }

    /**
     * Returns the location of the person.
     * 
     * @return the location of the person
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns a string representation of the person, including their name,
     * birthday, height, eye color, and location.
     * 
     * @return a string representation of the person
     */
    @Override
    public String toString() {
        return "Director{" +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", height='" + height + '\'' +
                ", eyeColor='" + eyeColor + '\'' +
                ", Location='" + location + '\'' +
                '}';
    }
}
