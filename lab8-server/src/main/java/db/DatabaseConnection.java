package db;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5439/studs";
    private static final String DB_USER = "s373757";
    private static final String DB_PASSWORD = "XZsaUa73Z8Su7TX5";

    private static Connection connection;
    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName(DB_DRIVER);
            } catch (ClassNotFoundException e) {
                logger.warn("Unable to load database driver: " + e.getMessage());
            }

            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                logger.warn("Unable to connect to database: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}