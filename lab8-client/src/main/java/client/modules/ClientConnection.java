package client.modules;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;

import client.gui.controllers.ClientConnectionGUI;

public class ClientConnection {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 11223;
    public static String commandList;
    public static int userId;
    public static List<Integer> ids;

    public void run() {
        boolean connected = false;

        while (!connected) {
            try (SocketChannel socketChannel = SocketChannel.open()) {
                socketChannel.configureBlocking(false);
                socketChannel.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
                while (!socketChannel.finishConnect()) {
                    // Wait until the connection is established
                }
                connected = true;

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                socketChannel.read(buffer);
                buffer.flip();
                String commandString = new String(buffer.array(), buffer.position(), buffer.limit());

                Selector selector = Selector.open();
                socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                ClientConnectionGUI.showLoadingWindow(ClientConnectionGUI.currentLocale);
                HandleUserInput.handleUserInput(selector, socketChannel);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Connection refused");

            }
            try {
                Thread.sleep(1000); // добавили задержку в 1 секунду для реконекта
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }
    }

}
