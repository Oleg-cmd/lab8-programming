package modules;

import db.ClientSession;
import db.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import request.ReadFromClient;
import request.SendCommandList;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class LoginHandler implements Runnable {
    private SocketChannel clientChannel;
    private static final Logger logger = LogManager.getLogger(LoginHandler.class);

    private ClientSession session;
    private SelectionKey key;
    private Thread runningThread; // Field to store the thread that is currently running this LoginHandler

    public LoginHandler(SocketChannel clientChannel, ClientSession session, SelectionKey key) {
        this.clientChannel = clientChannel;
        this.session = session;
        this.key = key;
    }

    @Override
    public void run() {
        try {
            String credits = LogOrReq(clientChannel, session);
            if (credits != null) {
                logger.info(credits);
                runningThread = Thread.currentThread();
                if (isCorrectLogin(credits)) {
                    SendCommandList.sendString(clientChannel, "Auth completed! Sending to u command list!");
                    SendCommandList.sendCommandsList(clientChannel);
                } else {
                    SendCommandList.sendString(clientChannel,
                            "Your credits are wrong! Server will disconnect u to prevent hack");
                    handleDisconnect(clientChannel, session);
                }
            } else {
                SendCommandList.sendString(clientChannel,
                        "Your credits are wrong! Server will disconnect u to prevent hack");
                handleDisconnect(clientChannel, session);
            }

        } catch (SQLException | IOException e) {
            logger.error(e);
        }
    }

    public boolean isCorrectLogin(String credits) {
        // Check if login and password are correct
        // logger.info( "isCorrectLogin - " + credits);
        logger.info(session.handleRequest(credits));
        if (session.isAuthorized()) {
            return true;
        } else {
            return false;
        }
    }

    public static String LogOrReq(SocketChannel clientChannel, ClientSession session) throws IOException, SQLException {
        if (!clientChannel.isOpen()) {
            handleDisconnect(clientChannel, session);
            return null;
        }

        logger.info("LogOrReq sending");
        SendCommandList.sendString(clientChannel,
                "Welcome to application!\n" + "if u want to register - type 1\n if u want to login - type 2");
        String logs = ReadFromClient.readStringFromClient(clientChannel);
        if (logs != null) {
            logs = logs.trim();

            logger.info("LogOrReq get data: " + logs);

            if (!logs.equals("")) {
                if (logs.equalsIgnoreCase("1")) {
                    return setCredits(clientChannel, session);
                } else if (logs.equalsIgnoreCase("2")) {
                    SendCommandList.sendInvite(clientChannel);
                    String credits = ReadFromClient.readStringFromClient(clientChannel).trim();
                    String[] tokens = credits.split(" ");
                    if (tokens.length == 2) {
                        if (tokens[0] != null && tokens[1] != null) {
                            return credits;
                        }
                    } else {
                        SendCommandList.sendString(clientChannel, "Bad credits provided, Server will disconnect u");
                        handleDisconnect(clientChannel, session);
                        return null;
                    }
                } else {
                    SendCommandList.sendString(clientChannel,
                            "Operation " + logs + " not found. Server will disconnect u");
                    handleDisconnect(clientChannel, session);
                    return null;
                }
            }
        }
        return null;
    }

    public static String setCredits(SocketChannel clientChannel, ClientSession session)
            throws IOException, SQLException {
        if (!clientChannel.isOpen()) {
            handleDisconnect(clientChannel, session);
            return null;
        }

        SendCommandList.sendString(clientChannel, "Enter login and pass that u want to enter (throw space):");
        String userCredits = ReadFromClient.readStringFromClient(clientChannel).trim();
        String[] userTokens = userCredits.split(" ");
        if (userTokens != null && userTokens.length >= 1) {
            String userLogin = userTokens[0].trim();
            String userPassword = userTokens[1].trim();
            if (!userLogin.equals("") && !userPassword.equals("")) {

                String result = DatabaseManager.registerUser(userLogin, userPassword);
                logger.info(result);
                if (!result.equals("result")) {
                    return userLogin + " " + userPassword;
                } else {
                    logger.info("Failed while hashing password");
                    SendCommandList.sendString(clientChannel, "Server get error while hashing ur password");
                    handleDisconnect(session.getClientChannel(), session);
                    return null;
                }

            } else {
                SendCommandList.sendString(clientChannel,
                        "Your values cannot be empty! Server will disconnect u to prevent hack");
                handleDisconnect(session.getClientChannel(), session);
                return null;
            }
        }
        return null;
    }

    public static void handleDisconnect(SocketChannel clientChannel, ClientSession session) {
        try {
            if (clientChannel.isOpen()) {
                SendCommandList.sendString(clientChannel, "Server is disconnectiong u...");
                session.getExecutor().shutdownNow();
                logger.info("Client disconnected: " + clientChannel.getRemoteAddress());
                // clientChannel.close();
            }
        } catch (IOException e) {
            logger.warn("Failed to close client channel: " + e.getMessage());
        } finally {
            try {
                session.close();
            } catch (IOException e) {
                logger.warn(e);
            }

        }
    }

    public void close() {
        if (runningThread != null) {
            runningThread.interrupt();
        }
        // You can also close any resources used by this LoginHandler here
    }
}
