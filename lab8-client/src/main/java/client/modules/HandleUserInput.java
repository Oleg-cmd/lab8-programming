package client.modules;

import client.gui.ClientConnectionGUI;
import client.helpers.CommandHandler;
import client.helpers.CommandObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class HandleUserInput {
    public static void handleUserInput(Selector selector, SocketChannel socketChannel) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean First = false;
        boolean Second = false;
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
                        buffer.clear();
                        int bytesRead = channel.read(buffer);
                        if (bytesRead == -1) {
                            // The server has closed the connection
                            System.out.println("Server has closed the connection");
                            return;
                        }
                        buffer.flip();
                        String receivedData = new String(buffer.array(), buffer.position(), buffer.limit());

                        System.out.println(receivedData);

                        String[] tokensFree = receivedData.split("\n");

                        if (receivedData.contains("Welcome") || receivedData.contains("Enter")
                                || receivedData.contains("Please")) {
                            String clientData = reader.readLine();
                            while (clientData == null || clientData.isEmpty() || clientData.equals("")
                                    || clientData.equals(" ")) {
                                System.out.println("You should provide some values");
                                System.out.println(receivedData);
                                clientData = reader.readLine();
                            }
                            clientData = clientData + "\nrequest"; // adding special word for recognising special
                                                                   // command on server
                            SendStringNow(clientData, socketChannel);
                        }

                        if (tokensFree[tokensFree.length - 1].equals("commands")) {
                            ClientConnection.commandList = receivedData;
                            // System.out.println(receivedData);
                        }

                        if (receivedData.trim().startsWith("repeat")) {
                            String clientData = reader.readLine();
                            while (clientData == null || clientData.isEmpty() || clientData.equals("")
                                    || clientData.equals(" ")) {
                                System.out.println("You should provide some values");
                                System.out.println(receivedData);
                                clientData = reader.readLine();
                            }
                            clientData = clientData + "\nrepeat"; // adding special word for recognising special command
                                                                  // on server
                            SendStringNow(clientData, socketChannel);
                        }

                        if (receivedData.trim().startsWith("turn-off-the-lights")) {
                            // System.out.println("im here");
                            StringBuilder input = new StringBuilder();
                            for (int i = 0; i <= 10; i++) {
                                String myData;
                                if (i != 10) {
                                    myData = receivedData.split("turn-off-the-lights")[1].split("\n")[i + 2];
                                } else {
                                    myData = receivedData.split("turn-off-the-lights")[1].split("\n")[i + 1];
                                }

                                String s = reader.readLine();
                                while (s == null || s.isEmpty() || s.equals("") || s.equals(" ")) {
                                    System.out.println("Field cannot be empty, type again");
                                    System.out.println(receivedData.split("turn-off-the-lights")[1].split("\n")[i + 1]);
                                    s = reader.readLine();
                                }
                                // System.out.println(s);

                                System.out.println(myData);
                                input.append(s);
                                input.append("\n"); // Add a newline character after each input

                            }
                            input.append("turn-off-the-lights");
                            SendStringNow(String.valueOf(input), socketChannel);
                        }
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

    public static void SendStringNow(String userInput, SocketChannel socketChannel) throws IOException {
        // System.out.println(userInput);
        ByteBuffer writeBuffer = ByteBuffer.wrap(userInput.getBytes());
        socketChannel.write(writeBuffer);
        // System.out.println("was send successfully");/
    }
}
