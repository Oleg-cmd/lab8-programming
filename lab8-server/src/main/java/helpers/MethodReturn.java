package helpers;

import model.Movie;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * This class represents a wrapper class used to return both the setters of a Movie object and the Movie object itself
 * from a method at the same time. It contains two instance variables: a HashMap that stores the setters of the Movie object,
 * and the Movie object itself. It has a constructor to initialize these instance variables, and getter methods to return
 * them.
 */
public record MethodReturn(HashMap<String, Consumer<String>> setters, Movie movie) {
    /**
     * Returns the setters of the Movie object.
     *
     */
    public MethodReturn {
    }

    /**
     * Returns the Movie object.
     *
     * @return a Movie object.
     */
    @Override
    public Movie movie() {
        return movie;
    }
}