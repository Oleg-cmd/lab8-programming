package request;
import helpers.CommandObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class ReadFromClient {
    private static final Logger logger = LogManager.getLogger(ReadFromClient.class);

    public static CommandObject readFromClient(SocketChannel clientChannel) {
        logger.info("Starting readFromClient");

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = 0;
        try {
            if(clientChannel != null){
                bytesRead = clientChannel.read(buffer);
            }else {
                logger.warn("Client channel is null");
            }
        } catch (IOException e) {
            logger.error("Error reading from client channel: " + e.getMessage());
            return null;
        }

        if (bytesRead == -1) {
            logger.info("Client disconnected");
            return null;
        }

        byte[] bytes = buffer.array();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bis);
            CommandObject commandObject = (CommandObject) ois.readObject();
            logger.info("End of readFromClient");
            return commandObject;
        } catch (IOException e) {
            logger.error("Error reading object from input stream: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            logger.error("Error casting object to CommandObject class: " + e.getMessage());
            return null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                logger.error("Error closing input stream: " + e.getMessage());
            }
        }
    }

    public static String readStringFromClient(SocketChannel clientChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder sb = new StringBuilder();
        int bytesRead = 0;
        boolean messageComplete = false;
        while (!messageComplete) {
            bytesRead = clientChannel.read(buffer);
            if (bytesRead > 0) {
                buffer.flip();
                Charset charset = StandardCharsets.UTF_8;
                CharsetDecoder decoder = charset.newDecoder();
                CharBuffer charBuffer = decoder.decode(buffer);
                sb.append(charBuffer);
                buffer.clear();
                if (sb.indexOf("turn-off-the-lights") != -1) {
                    sb.delete(sb.length() - "turn-off-the-lights".length(), sb.length());
                    messageComplete = true;
                }else if(sb.indexOf("request") != -1){
                    sb.delete(sb.length() - "request".length(), sb.length());
                    messageComplete = true;
                }else if(sb.indexOf("repeat") != -1){
                    sb.delete(sb.length() - "repeat".length(), sb.length());
                    messageComplete = true;
                }
            } else if (bytesRead == -1) {
                // The client has disconnected
                logger.info("Client disconnect");
                return null;
            }
        }
        return sb.toString();
    }






}
