package request;

import helpers.CommandObject;
import modules.Process;
import modules.ServerConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class HandleClientInput {
    private static final Logger logger = LogManager.getLogger(HandleClientInput.class);

    public static void handleClientInput(SocketChannel clientChannel, CommandObject input, Selector selector) throws IOException {
        if (!clientChannel.isOpen()) {
            logger.warn("Client channel is closed");
            return;
        }

        logger.info("Starting handleClientInput");
        if (clientChannel.isOpen() && input != null) {
            // Print the client input to the console
            logger.info("Client input: " + " " + Arrays.toString(input.getTokens()) + " " + input.getXmlData());
            //proccessing input
            Process.Process(input.getTokens(), input.getXmlData());

        }else{
            logger.info("Client disconnected: " + clientChannel.getRemoteAddress());
            ServerConnection.connectedClients--;
            logger.info("Count of connected clients:  "  + ServerConnection.connectedClients);
            clientChannel.close();
            clientChannel.keyFor(selector).cancel();
        }
        logger.info("End of handleClientInput");
    }
}
