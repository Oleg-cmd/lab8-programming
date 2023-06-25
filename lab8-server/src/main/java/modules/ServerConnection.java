package modules;

import db.ClientSession;
import db.UserCollectionManager;
import helpers.CommandObject;
import helpers.UserInputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import request.HandleClientInput;
import request.SendCommandList;
import request.ReadFromClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class ServerConnection {
    private final int port;
    private static ServerSocketChannel serverChannel;
    public static SocketChannel clientChannel;

    public ServerConnection(int port) {
        this.port = port;
    }

    public static int connectedClients = 0;

    public static ServerSocketChannel getServerChannel() {
        return serverChannel;
    }

    private static final List<ClientSession> sessions = new ArrayList<>();

    private static final Logger logger = LogManager.getLogger(ServerConnection.class);

    private static int MAX_THREADS = 128;

    // ExecutorService executor = Executors.newCachedThreadPool();
    // Создание долгоживущего readExecutor
    public static ExecutorService readExecutor = Executors.newFixedThreadPool(MAX_THREADS);

    public void start() throws IOException, InterruptedException, ClassNotFoundException {
        UserCollectionManager userCollectionManager = new UserCollectionManager();
        logger.info("starting of start");
        // Open a non-blocking server socket channel
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        // Bind the server socket to the specified port
        serverChannel.bind(new InetSocketAddress(port));

        // Create a selector and register the server channel for accepting connections
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        logger.info("Server started on port " + port);

        Thread server_connection = new Thread() {
            public void run() {
                while (true) {
                    try {
                        // Wait for an event on one of the registered channels
                        selector.select();

                        // Iterate over the set of keys for which events are available
                        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            keyIterator.remove();
                            // Handle the event for the key
                            if (key.isAcceptable()) {
                                handleConnection(key, selector, readExecutor);
                            } else if (key.isReadable()) {
                                handleRead(key, selector);
                            } else if (key.isWritable()) { // Client disconnected
                                handleDisconnect(clientChannel);
                            }
                        }
                    } catch (IOException err) {
                        logger.warn("catch some er" + err);
                    }
                }
            }
        };

        Thread serverReading = new Thread() {
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {
                        String serverInput = reader.readLine();
                        if (serverInput != null) {
                            if (serverInput.equals("save")) {
                                UserInputHandler.toExecute("save");
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        server_connection.start();
        serverReading.start();
    }

    public static int getUserIdForSession(SocketChannel clientChannel) {
        for (ClientSession session : sessions) {
            if (session.getClientChannel() == clientChannel) {
                return session.getUserId();
            }
        }
        return -1; // user not found
    }

    private void handleConnection(SelectionKey key, Selector selector, ExecutorService readExecutor) {
        try {
            SocketChannel clientChannel = serverChannel.accept();
            ServerConnection.clientChannel = clientChannel;
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);

            logger.info("Client connected: " + clientChannel.getRemoteAddress());
            connectedClients++;
            logger.info("Count of connected clients:  " + connectedClients);
            ClientSession session = new ClientSession(clientChannel, readExecutor);
            sessions.add(session);
            // Pause for 1 second before sending the list of available commands
            Thread.sleep(10);

            readExecutor.execute(new LoginHandler(clientChannel, session, key));
        } catch (IOException | InterruptedException e) {
            logger.warn(e);
        }

    }

    // Метод handleRead
    private void handleRead(SelectionKey key, Selector selector) {
        SocketChannel clientChannel = (SocketChannel) key.channel();

        if (!clientChannel.isOpen()) {
            logger.warn("Client channel is closed");
            key.cancel(); // Отменяем ключ, чтобы избежать обработки закрытого канала
            return;
        }

        ServerConnection.clientChannel = clientChannel;
        CommandObject data = ReadFromClient.readFromClient(clientChannel);

        // Используем уже существующий пул потоков для обработки чтения
        readExecutor.submit(() -> {
            // Handle the client input
            try {
                HandleClientInput.handleClientInput(clientChannel, data, selector);
            } catch (IOException e) {
                logger.warn(e);
            }
        });
    }

    public static void handleDisconnect(SocketChannel clientChannel) {
        try {
            logger.info("Client disconnected: " + clientChannel.getRemoteAddress());
            try {
                // clientChannel.close();
                logger.info("NON-closing client channel");
            } finally {
                closeSession(clientChannel);
            }
        } catch (IOException e) {
            logger.warn("Failed to cast channel to SocketChannel: " + e.getMessage());
        }
    }

    public static void closeSession(SocketChannel clientChannel) throws IOException {
        synchronized (sessions) {
            int index = getIndexByChannel(clientChannel);
            if (index != -1 && index < sessions.size()) {
                Optional<ClientSession> session = sessions.stream()
                        .filter(s -> s.getClientChannel().equals(clientChannel))
                        .findFirst();
                if (session.isPresent()) {
                    ClientSession clientSession = session.get();
                    sessions.remove(clientSession);
                    clientSession.close();
                }
                logger.info("Session closed for client: " + clientChannel.getRemoteAddress());
            } else {
                logger.warn("some adds while removing session");
            }
        }
    }

    private static int getIndexByChannel(SocketChannel clientChannel) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getClientChannel().equals(clientChannel)) {
                return i;
            }
        }
        return -1; // если не найдено, возвращаем -1
    }

    public static ClientSession getSessionByChannel(SocketChannel clientChannel) {
        for (ClientSession session : sessions) {
            if (session.getClientChannel().equals(clientChannel)) {
                return session;
            }
        }
        return null; // if not found, return null
    }

}
