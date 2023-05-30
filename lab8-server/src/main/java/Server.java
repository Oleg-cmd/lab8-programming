import db.DatabaseConnection;
import modules.CollectionInit;
import modules.ServerConnection;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



import java.io.IOException;
import java.sql.SQLException;


public class Server {

    private static final Logger logger = LogManager.getLogger(ServerConnection.class);

    public static void main(String[] args) {
        try {

            // Create a new server connection
            ServerConnection server = new ServerConnection(11223);
            // Initiation of collection
            CollectionInit.Init();

            DatabaseConnection.getConnection();
            // Start the server
            server.start();


        } catch (IOException | SQLException | InterruptedException | ClassNotFoundException e) {
            logger.warn(e);
        }


    }




}
