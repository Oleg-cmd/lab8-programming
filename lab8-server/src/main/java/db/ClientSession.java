package db;

import fileManager.CollectionManager;
import model.Movie;
import modules.ServerConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientSession implements Runnable {

    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    private boolean authorized;
    private int userId; // новое поле для хранения идентификатора пользователя

    private final SocketChannel clientChannel;
    private ExecutorService executor;
    private String username;

    private static final Logger logger = LogManager.getLogger(ClientSession.class);

    public ClientSession(SocketChannel clientChannel, ExecutorService executor) {
        this.clientChannel = clientChannel;
        this.executor = executor;

        logger.info("new Client session was created");
    }

    @Override
    public void run() {
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public String handleRequest(String request) {
        String[] tokens = request.split(" ");

        if (tokens != null && tokens.length > 1) {

            String login = tokens[0].trim();
            String password = tokens[1].trim();

            int myId = DatabaseManager.getUserId(login, password);
            if (myId != -1) {
                authorized = true;
                username = login;
                setUserId(myId);

                // init & load collection of movies
                // System.out.println(getUserId());
                CollectionManager local = new CollectionManager();
                UserCollectionManager.addCollection(myId, local);
                dbHelperCommand.preLoad(loadObjects(), local);
                logger.info("pre-load of collection for user was made");

                //
                return "Auth complete";
            } else {
                return "Auth failed";
            }
        } else {
            return "Bad tokens provided";
        }

    }

    public SocketChannel getClientChannel() {
        return clientChannel;
    }

    public void close() throws IOException {
        UserCollectionManager.deleteCollectionManager(getUserId());
        clientChannel.close();
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public String getUsername() {
        return username;
    }

    // Метод для установки идентификатора пользователя
    private void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public static void saveObjects(Collection<Movie> movies) {
        DatabaseManager.saveMovies(movies, ServerConnection.getUserIdForSession(ServerConnection.clientChannel));
    }

    public static Collection<Movie> loadObjects() {
        return DatabaseManager.loadMovies(ServerConnection.getUserIdForSession(ServerConnection.clientChannel));
    }

}
