package modules;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class CommandOutput {
    private final StringBuilder output = new StringBuilder();

    public void append(String s) {
        output.append(s);
    }

    public synchronized void sendOutputSync(SocketChannel channel) {
        try {
            if (channel != null && channel.isOpen()) {

                    ByteBuffer buffer = ByteBuffer.wrap(output.toString().getBytes());
                    int bytesWritten = 0;
                    while (bytesWritten < buffer.limit()) {
                        bytesWritten += channel.write(buffer);
                    }

                output.setLength(0); // clear the output
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

