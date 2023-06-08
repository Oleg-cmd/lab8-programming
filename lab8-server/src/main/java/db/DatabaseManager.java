package db;

import model.Color;
import model.Coordinates;
import model.Location;
import model.Movie;
import model.MpaaRating;
import model.Person;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5439/studs";
    private static final String DB_USERNAME = "s373757";
    private static final String DB_PASSWORD = "XZsaUa73Z8Su7TX5";

    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);

    public static int getUserId(String login, String password) {
        try (Connection connection = getConnection()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM users WHERE login = ?");
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                statement = connection.prepareStatement("SELECT password FROM users WHERE id = ?");
                statement.setInt(1, id);
                result = statement.executeQuery();
                if (result.next()) {
                    String hashedPassword = result.getString("password");
                    // logger.info(password + " " + hashedPassword);
                    if (verifyPassword(password, hashedPassword)) {
                        return id;
                    } else {
                        logger.warn("non-correct pass");
                    }
                }
            } else {
                logger.warn("non-correct log");
            }
            return -1; // or throw an exception if the user is not found
        } catch (SQLException e) {
            logger.warn(e);
            return -1;
        }
    }

    public static String registerUser(String login, String password) throws SQLException {
        try (Connection connection = getConnection()) {
            assert connection != null;
            String s;
            if (userExists(connection, login)) {
                s = "such user already exist";
                logger.info(s);
                return s;
            } else {
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
                statement.setString(1, login);
                statement.setString(2, hashPassword(password));
                statement.executeUpdate();

                s = "register complete";
                logger.info(s);
                return s;
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("Failed to hash password", e);
            return "fail";
        }
    }

    private static boolean userExists(Connection connection, String login) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE login = ?");
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return count > 0;
    }

    public static boolean verifyPassword(String password, String hash) {
        try {
            String passwordHash = hashPassword(password);
            return passwordHash.equals(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.warn("JDBC driver not found");
        }
        if (DB_USERNAME == null || DB_USERNAME.isEmpty() || DB_PASSWORD == null) {
            logger.warn("Invalid username or password");
        }
        if (DB_URL == null || DB_URL.isEmpty()) {
            logger.warn("Invalid database URL");
        }
        try {
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            logger.warn("Failed to connect to the database: " + e.getMessage());
        }
        return null;
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hash = digest.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b : hash) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static void saveMovies(Collection<Movie> movies, int userId) {
        try (Connection connection = getConnection()) {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM movies WHERE user_id = ?");
            deleteStatement.setInt(1, userId);
            deleteStatement.executeUpdate();
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO movies (name, coordinate_x, coordinate_y, creation_date, oscars_count, golden_palm_count, tagline, mpaa_rating, director_name, director_birthday, director_height, user_id, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (Movie movie : movies) {
                insertStatement.setString(1, movie.getName());
                insertStatement.setDouble(2, movie.getCoordinates().getX());
                insertStatement.setFloat(3, movie.getCoordinates().getY());
                insertStatement.setDate(4, Date.valueOf(movie.getCreationDate().toLocalDate()));
                insertStatement.setInt(5, movie.getOscarsCount());
                insertStatement.setInt(6, movie.getGoldenPalmCount());
                insertStatement.setString(7, movie.getTagline());
                insertStatement.setString(8, movie.getMpaaRating().toString());
                insertStatement.setString(9, movie.getDirector().getName());
                insertStatement.setDate(10, Date.valueOf(movie.getDirector().getBirthday().toLocalDate()));
                insertStatement.setDouble(11, movie.getDirector().getHeight());
                insertStatement.setInt(12, userId);
                insertStatement.setString(13, movie.getDirector().getEyeColor().toString());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Collection<Movie> loadMovies(int userId) {
        Collection<Movie> movies = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movies WHERE user_id = ?");
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                float coordinateX = result.getFloat("coordinate_x");
                float coordinateY = result.getFloat("coordinate_y");
                ZonedDateTime creationDate = result.getDate("creation_date").toLocalDate()
                        .atStartOfDay(ZoneId.systemDefault());
                Integer oscarsCount = result.getInt("oscars_count");
                Integer goldenPalmCount = result.getInt("golden_palm_count");
                String tagline = result.getString("tagline");
                MpaaRating mpaaRating = MpaaRating.valueOf(result.getString("mpaa_rating"));
                String directorName = result.getString("director_name");
                LocalDate directorBirthday = result.getDate("director_birthday").toLocalDate();
                double directorHeight = result.getDouble("director_height");
                Color color = Color.valueOf(result.getString("color"));
                Person director = new Person();
                Location location = new Location();
                location.setLocation(coordinateX, coordinateY, directorName);
                director.setName(directorName);
                director.setBirthday(directorBirthday.atStartOfDay(ZoneId.systemDefault()));
                director.setHeight(directorHeight);
                director.setLocation(location);
                director.setEyeColor(color);
                Movie movie = new Movie();
                movie.setId(id);
                movie.setName(name);
                Coordinates thisCoordinates = new Coordinates();
                thisCoordinates.setX(coordinateX);
                thisCoordinates.setY(coordinateY);
                movie.setCoordinates(thisCoordinates);
                movie.setCreationDate(creationDate);
                movie.setOscarsCount(oscarsCount);
                movie.setGoldenPalmCount(goldenPalmCount);
                movie.setTagline(tagline);
                movie.setMpaaRating(mpaaRating);
                movie.setDirector(director);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

}
