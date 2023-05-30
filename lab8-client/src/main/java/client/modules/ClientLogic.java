package client.modules;

public class ClientLogic implements Runnable {
    @Override
    public void run() {
        System.out.println("ClientLogic starting...");
        // Логика работы клиента
        ClientConnection clientConnection = new ClientConnection();
        clientConnection.run();
        System.out.println("ClientLogic finished");
    }
}