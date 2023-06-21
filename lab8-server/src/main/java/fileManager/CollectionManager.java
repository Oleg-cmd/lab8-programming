package fileManager;

import model.Movie;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The CollectionManager class manages a collection of movies.
 */

public class CollectionManager {
    private final HashSet<Movie> movies;
    private final ReadWriteLock lock;

    /**
     * Constructs a CollectionManager object with an empty collection of movies.
     */

    public CollectionManager() {
        movies = new HashSet<>();
        lock = new ReentrantReadWriteLock();
    }

    /**
     * Returns the collection of movies managed by this CollectionManager object.
     * 
     * @return the collection of movies
     */
    public HashSet<Movie> getMovies() {
        lock.readLock().lock();
        try {
            return movies;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Adds a movie to the collection managed by this CollectionManager object.
     * 
     * @param movie the movie to add
     */
    public void addMovie(Movie movie) {
        lock.writeLock().lock();
        try {
            movies.add(movie);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Adds a collection of movies to the collection managed by this
     * CollectionManager object.
     * 
     * @param moviesToAdd the collection of movies to add
     */

    public void addAllMovies(Collection<Movie> moviesToAdd) {
        lock.writeLock().lock();
        try {
            for (Movie movie : moviesToAdd) {
                if (!movies.contains(movie)) {
                    movies.add(movie);
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Integer getRandomID() {
        Random rand = new Random();
        int upperbound = 9999999;
        int id;
        boolean idExists;
        do {
            id = rand.nextInt(upperbound);
            idExists = false;
            for (Movie m : movies) {
                if (m.getId() == id) {
                    idExists = true;
                    break;
                }
            }
        } while (idExists);
        return id;
    }

    public ZonedDateTime getNow() {
        return ZonedDateTime.now();
    }

    /**
     * Removes a movie from the collection managed by this CollectionManager object.
     *
     * @param movie the movie to remove
     */
    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    public boolean removeId(Integer id) {
        return movies.remove(getById(id));
    }

    /**
     * Returns the number of movies in the collection managed by this
     * CollectionManager object.
     * 
     * @return the number of movies
     */

    public int getMoviesCount() {
        return movies.size();
    }

    /**
     * Removes all movies from the collection managed by this CollectionManager
     * object.
     */

    public void clearMovies() {
        lock.writeLock().lock(); // Захват блокировки записи
        try {
            movies.clear();
        } finally {
            lock.writeLock().unlock(); // Освобождение блокировки записи
        }
    }

    /**
     * Returns the movie with the specified ID from the collection managed by this
     * CollectionManager object.
     * 
     * @param id the ID of the movie to find
     * @return the movie with the specified ID, or null if no such movie exists
     */

    public Movie getById(int id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Returns a collection of movies from the collection managed by this
     * CollectionManager object that have the specified
     * number of Oscars.
     * 
     * @param oscarsCount the number of Oscars to search for
     * @return the collection of movies with the specified number of Oscars
     */

    public HashSet<Movie> getMoviesWithOscarsCount(int oscarsCount) {
        HashSet<Movie> result = new HashSet<>();
        for (Movie movie : movies) {
            if (movie.getOscarsCount() == oscarsCount) {
                result.add(movie);
            }
        }
        return result;
    }

    public boolean checkFieldsByNull(Movie movie) {
        if (movie != null) {
            if (movie.getId() == null || movie.getId() <= 0) {
                return false;
            }
            if (movie.getName() == null || movie.getName().isEmpty()) {
                return false;
            }
            if (movie.getCoordinates() == null || Float.compare(movie.getCoordinates().getX(), 0.0f) == 0
                    || Float.compare(movie.getCoordinates().getY(), 0.0f) == 0) {
                return false;
            }
            if (movie.getCreationDate() == null) {
                return false;
            }
            if (movie.getOscarsCount() != null && movie.getOscarsCount() <= 0) {
                return false;
            }
            if (movie.getGoldenPalmCount() == null || movie.getGoldenPalmCount() <= 0) {
                return false;
            }
            if (movie.getTagline() == null || movie.getTagline().isEmpty()) {
                return false;
            }
            if (movie.getMpaaRating() == null) {
                return false;
            }
            if (movie.getDirector() == null || movie.getDirector().getName() == null
                    || movie.getDirector().getName().isEmpty()) {
                return false;
            }
            if (movie.getDirector().getBirthday() == null) {
                return false;
            }
            if (movie.getDirector().getHeight() <= 0) {
                return false;
            }
            if (movie.getDirector().getEyeColor() == null) {
                return false;
            }
            if (movie.getDirector().getLocation() == null || movie.getDirector().getLocation().getName() == null
                    || movie.getDirector().getLocation().getName().isEmpty()) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

}