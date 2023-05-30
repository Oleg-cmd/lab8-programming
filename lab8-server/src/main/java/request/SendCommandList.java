package request;

import collectionWorker.HelpCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class SendCommandList {
    private static final Logger logger = LogManager.getLogger(SendCommandList.class);

    public static void sendCommandsList(SocketChannel clientChannel) throws IOException {
        logger.info("Starting sendCommandsList");
        String[] commands = HelpCommand.myFuncsName;
        logger.info(Arrays.toString(commands));
        String commandList = String.join(", ", commands) + "\ncommands";
        ByteBuffer buffer = ByteBuffer.wrap((commandList).getBytes());
        clientChannel.write(buffer);
        logger.info("End of sendCommandsList");
    }

    public static void sendInvite(SocketChannel clientChannel) throws IOException{
        logger.info("Starting sendInvite");
        String invite = "\n Please, type login and password to enter to application \n" + "Example: 'login password'";
        ByteBuffer buffer = ByteBuffer.wrap((invite).getBytes());
        clientChannel.write(buffer);
        logger.info("End of sendInvite");
    }

    public static void sendString(SocketChannel clientChannel, String s) throws IOException{
        logger.info("End of sendString");
        ByteBuffer buffer = ByteBuffer.wrap((s).getBytes());
        clientChannel.write(buffer);
        logger.info("End of sendString");
    }
}
