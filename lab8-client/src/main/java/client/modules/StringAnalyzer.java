package client.modules;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import client.gui.ClientConnectionGUI;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class StringAnalyzer {

    private static CompletableFuture<String> future = new CompletableFuture<>();

    public static void setClientData(String data) {
        future.complete(data);
    }

    public static void checkString(String receivedData, SocketChannel socketChannel) {
        if (receivedData.contains("Welcome")) {
            String clientData;
            try {
                clientData = future.get();
                clientData = clientData + "\nrequest"; // adding special word for recognising special
                SendStringNow(clientData, socketChannel);
                future = new CompletableFuture<>(); // "Сбрасываем" future
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (receivedData.contains("Your credits are wrong!") || receivedData.contains("Bad credits provided")) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    // Выполняем show404()
                    Platform.runLater(() -> ClientConnectionGUI.show404());

                    // Задержка в три секунды
                    Thread.sleep(3000);

                    // Выполняем CloseUp()
                    Platform.runLater(() -> ClientConnectionGUI.CloseUp());

                    return null;
                }
            };

            // Запускаем задачу в отдельном потоке
            Thread thread = new Thread(task);
            thread.start();
        }

        if (receivedData.contains("Enter") || receivedData.contains("Please")) {
            String clientData;
            try {
                ClientConnectionGUI.showCredits();
                clientData = future.get();
                clientData = clientData + "\nrequest"; // adding special word for recognising special
                SendStringNow(clientData, socketChannel);
                future = new CompletableFuture<>(); // "Сбрасываем" future
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (receivedData.contains("Auth completed!")) {
            String clientData;
            ClientConnectionGUI.showMainScreen();

        }

        if (receivedData.trim().startsWith("repeat")) {
            String clientData = "fff";
            clientData = clientData + "\nrepeat"; // adding special word for recognising special command
                                                  // on server
            try {
                SendStringNow(clientData, socketChannel);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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

                System.out.println(myData);
                input.append("fff");
                input.append("\n"); // Add a newline character after each input

            }
            input.append("turn-off-the-lights");
            try {
                SendStringNow(String.valueOf(input), socketChannel);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
