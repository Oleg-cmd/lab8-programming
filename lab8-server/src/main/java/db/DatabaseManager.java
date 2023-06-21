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
import java.util.List;
import java.util.stream.Collectors;

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
            PreparedStatement upsertStatement = connection.prepareStatement(
                    "INSERT INTO movies (id, name, coordinate_x, coordinate_y, creation_date, oscars_count, golden_palm_count, tagline, mpaa_rating, director_name, director_birthday, director_height, user_id, color, directorx, directory, directorplacename) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                            + "ON CONFLICT (id) DO UPDATE SET "
                            + "name = EXCLUDED.name, "
                            + "coordinate_x = EXCLUDED.coordinate_x, "
                            + "coordinate_y = EXCLUDED.coordinate_y, "
                            + "creation_date = EXCLUDED.creation_date, "
                            + "oscars_count = EXCLUDED.oscars_count, "
                            + "golden_palm_count = EXCLUDED.golden_palm_count, "
                            + "tagline = EXCLUDED.tagline, "
                            + "mpaa_rating = EXCLUDED.mpaa_rating, "
                            + "director_name = EXCLUDED.director_name, "
                            + "director_birthday = EXCLUDED.director_birthday, "
                            + "director_height = EXCLUDED.director_height, "
                            + "user_id = EXCLUDED.user_id, "
                            + "color = EXCLUDED.color, "
                            + "directorx = EXCLUDED.directorx, "
                            + "directory = EXCLUDED.directory, "
                            + "directorplacename = EXCLUDED.directorplacename");

            for (Movie movie : movies) {
                upsertStatement.setInt(1, movie.getId());
                upsertStatement.setString(2, movie.getName());
                upsertStatement.setDouble(3, movie.getCoordinates().getX());
                upsertStatement.setFloat(4, movie.getCoordinates().getY());
                upsertStatement.setDate(5, Date.valueOf(movie.getCreationDate().toLocalDate()));
                upsertStatement.setInt(6, movie.getOscarsCount());
                upsertStatement.setInt(7, movie.getGoldenPalmCount());
                upsertStatement.setString(8, movie.getTagline());
                upsertStatement.setString(9, movie.getMpaaRating().toString());
                upsertStatement.setString(10, movie.getDirector().getName());
                upsertStatement.setDate(11, Date.valueOf(movie.getDirector().getBirthday().toLocalDate()));
                upsertStatement.setDouble(12, movie.getDirector().getHeight());
                upsertStatement.setInt(13, userId);
                upsertStatement.setString(14, movie.getDirector().getEyeColor().toString());
                upsertStatement.setDouble(15, movie.getDirector().getLocation().getX());
                upsertStatement.setDouble(16, movie.getDirector().getLocation().getY());
                upsertStatement.setString(17, movie.getDirector().getLocation().getName());
                upsertStatement.executeUpdate();
            }

            removeNotExistingMovies(movies, userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeNotExistingMovies(Collection<Movie> movies, int userId) {
        try (Connection connection = getConnection()) {
            // Создаем список всех id фильмов в коллекции
            List<Integer> ids = movies.stream()
                    .map(Movie::getId)
                    .collect(Collectors.toList());

            // Создаем строку с этими id для запроса SQL
            String idString = ids.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            // Удаляем все фильмы пользователя, которые не находятся в списке id
            String sql = "DELETE FROM movies WHERE user_id = ? AND id NOT IN (" + idString + ")";
            PreparedStatement deleteStatement = connection.prepareStatement(sql);
            deleteStatement.setInt(1, userId);
            deleteStatement.executeUpdate();

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
                Double directorX = Double.parseDouble(result.getString("directorx"));
                Double directorY = Double.parseDouble(result.getString("directory"));
                String directorPlaceName = result.getString("directorplacename");
                location.setLocation(directorX, directorY, directorPlaceName);
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
