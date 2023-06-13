package client.modules;

import client.gui.ClientConnectionGUI;
import client.helpers.CommandHandler;
import helpers.CommandObject;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class HandleUserInput {
    public static SocketChannel defaultChannel;

    public static void handleUserInput(Selector selector, SocketChannel socketChannel) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            int numKeys = selector.select();
            if (numKeys > 0) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        defaultChannel = channel;
                        buffer.clear();
                        int bytesRead = channel.read(buffer);
                        if (bytesRead == -1) {
                            // The server has closed the connection
                            System.out.println("Server has closed the connection");
                            return;
                        }
                        buffer.flip();

                        Charset charset = StandardCharsets.UTF_8;
                        CharsetDecoder decoder = charset.newDecoder();
                        String receivedData;
                        try {
                            receivedData = decoder.decode(buffer).toString();
                        } catch (CharacterCodingException e) {
                            // Error decoding data
                            System.out.println("Failed to decode received data: " + e.getMessage());
                            return;
                        }

                        System.out.println(receivedData);

                        String[] tokensFree = receivedData.split("\n");

                        if (tokensFree[tokensFree.length - 1].equals("commands")) {
                            ClientConnection.commandList = receivedData;
                            // System.out.println(receivedData);
                        }

                        StringAnalyzer.checkString(receivedData, socketChannel);
                    }

                    if (key.isWritable()) {
                        if (reader.ready()) {
                            String userInput = reader.readLine();
                            CommandHandler commandHandler = new CommandHandler(ClientConnection.commandList);
                            CommandObject processedInput = CommandHandler.handleCommand(userInput);

                            if (processedInput != null) {
                                ByteBuffer writeBuffer = ByteBuffer.wrap(processedInput.toByteArray());
                                socketChannel.write(writeBuffer);
                            }
                        }
                    }
                }

            }
        }
    }

    public static void SendCommand(String userInput) {
        try {
            CommandHandler commandHandler = new CommandHandler(ClientConnection.commandList);
            CommandObject processedInput = CommandHandler.handleCommand(userInput);
            if (processedInput != null) {
                ByteBuffer writeBuffer = ByteBuffer.wrap(processedInput.toByteArray());
                defaultChannel.write(writeBuffer);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
