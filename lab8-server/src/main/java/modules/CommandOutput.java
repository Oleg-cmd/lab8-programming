package modules;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandOutput {
    private final StringBuilder output = new StringBuilder();
    private static final Logger logger = LogManager.getLogger(CommandOutput.class);

    public void append(String s) {
        output.append(s);
    }

    public synchronized void sendOutputSync(SocketChannel channel) {
        try {
            if (channel != null && channel.isOpen()) {
                Charset charset = StandardCharsets.UTF_8;
                ByteBuffer buffer = charset.encode(output.toString());
                int totalBytes = buffer.remaining();

                while (buffer.hasRemaining()) {
                    int bytesWritten = channel.write(buffer);
                    if (bytesWritten <= 0) {
                        logger.warn("Error while trying to send dats");
                        // Возникла ошибка при записи в канал, можно предпринять дополнительные действия
                        // или выбросить исключение
                        break;
                    }
                }

                if (buffer.remaining() == 0) {
                    // Весь буфер был успешно записан
                    logger.info("Data was write to buffer successfuly");
                    output.setLength(0); // clear the output
                }
            } else {
                System.out.println("Client channel is closed.");
                output.setLength(0); // clear the output
            }
        } catch (IOException e) {
            System.out.println("Failed to send output: " + e.getMessage());
            output.setLength(0); // clear the output
        }
    }

    public void sendOutput(SocketChannel channel) {
        sendOutputSync(channel);
    }
}
