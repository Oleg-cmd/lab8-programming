package client.model;

import java.time.ZonedDateTime;

/**
 * Represents a movie.
 */
public class Movie {
    /**
     * The unique identifier for the movie. Must be greater than 0 and generated
     * automatically.
     */
    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого
                        // поля должно быть уникальным, Значение этого поля должно генерироваться
                        // автоматически
    /**
     * The name of the movie. Cannot be null or an empty string.
     */
    private String name;

    /**
     * The coordinates of the movie's location. Cannot be null.
     */
    private Coordinates coordinates;

    /**
     * The date and time when the movie was created. Cannot be null and generated
     * automatically.
     */
    private ZonedDateTime creationDate;

    /**
     * The number of Oscars won by the movie. Must be greater than 0 and can be
     * null.
     */
    private Integer oscarsCount;

    /**
     * The number of Golden Palms won by the movie. Must be greater than 0 and
     * cannot be null.
     */
    private Integer goldenPalmCount;

    /**
     * The tagline of the movie. Cannot be null or an empty string.
     */
    private String tagline;

    /**
     * The MPAA rating of the movie. Cannot be null.
     */
    private MpaaRating mpaaRating;

    /**
     * The director of the movie. Cannot be null.
     */
    private Person director;

    public Movie(Integer id, String name, Coordinates coordinates, ZonedDateTime creationDate, Integer oscarsCount,
            Integer goldenPalmCount, String tagline, MpaaRating mpaaRating, Person director) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.goldenPalmCount = goldenPalmCount;
        this.tagline = tagline;
        this.mpaaRating = mpaaRating;
        this.director = director;
    }

    /**
     * Returns the id of the movie.
     *
     * @return The id of the movie.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id of the movie.
     *
     * @param id The id of the movie.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the name of the movie.
     *
     * @return The name of the movie.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the movie.
     *
     * @param name The name of the movie.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the coordinates of the movie's location.
     *
     * @return The coordinates of the movie's location.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the coordinates of the movie's location.
     *
     * @param coordinates The coordinates of the movie's location.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Returns the creation date of the movie.
     *
     * @return The creation date of the movie.
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the movie.
     *
     * @param creationDate The creation date of the movie.
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Returns the number of Oscars won by the movie.
     *
     * @return The number of Oscars won by the movie.
     */
    public Integer getOscarsCount() {
        return oscarsCount;
    }

    /**
     * Sets the number of Oscars won by the movie.
     *
     * @param oscarsCount The number of Oscars won by the movie.
     */
    public void setOscarsCount(Integer oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    /**
     * Returns the number of Golden Palms won by the movie.
     *
     * @return The number of Golden Palms won by the movie.
     */

    public Integer getGoldenPalmCount() {
        return goldenPalmCount;
    }

    /**
     * Sets the number of GoldenPalm won by the movie.
     *
     * @param goldenPalmCount The number of Oscars won by the movie.
     */
    public void setGoldenPalmCount(Integer goldenPalmCount) {
        this.goldenPalmCount = goldenPalmCount;
    }

    /**
     * Returns the tagline of the movie.
     *
     * @return The string tagline of movie.
     */
    public String getTagline() {
        return tagline;
    }

    /**
     * Sets the String of Tagline of the movie.
     *
     * @param tagline The string tagline of movie.
     */
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    /**
     * Returns the Mpaa Rating of the movie.
     *
     * @return The mpaaRating rating of the movie.
     */

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    /**
     * Sets the MpaaRating of the movie.
     *
     * @param mpaaRating The mpaaRating rating of the movie.
     */
    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    /**
     * Returns the Director object of the movie.
     *
     * @return The director of the movie.
     */
    public Person getDirector() {
        return director;
    }

    /**
     * Sets the Director of the movie.
     *
     * @param director The director of the movie.
     */
    public void setDirector(Person director) {
        this.director = director;
    }

    /**
     * Returns a string representation of the movie.
     * 
     * @return a string representation of the movie
     */
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", oscarsCount=" + oscarsCount +
                ", goldenPalmCount=" + goldenPalmCount +
                ", tagline='" + tagline + '\'' +
                ", mpaaRating=" + mpaaRating +
                ", director=" + director +
                '}';
    }

}
