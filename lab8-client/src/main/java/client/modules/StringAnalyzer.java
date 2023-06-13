package client.modules;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import client.gui.ClientConnectionGUI;
import client.gui.events.Setup;
import client.helpers.MovieManager;
import client.helpers.MovieParser;
import client.model.Movie;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class StringAnalyzer {

    private static CompletableFuture<String> future = new CompletableFuture<>();

    public static void setClientData(String data) {
        future.complete(data);
    }

    public static void checkString(String receivedData, SocketChannel socketChannel) {
        boolean isStep = false;

        if (receivedData.contains("Welcome")) {
            isStep = true;
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

        else if (receivedData.contains("show-c")) {
            isStep = true;
            String[] lines = receivedData.split("\n");
            if (lines.length >= 2) {
                String data = lines[1];
                System.out.println("Parsing");
                MovieManager.movies = MovieParser.parseMovies(data);
                MovieManager.setupMovies(Setup.setupScene);
                System.out.println(MovieManager.movies);
            }
        }

        else if (receivedData.contains("Your credits are wrong!") || receivedData.contains("Bad credits provided")) {
            isStep = true;
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

        else if (receivedData.contains("Enter") || receivedData.contains("Please")) {
            isStep = true;
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

        else if (receivedData.contains("Auth completed!")) {
            isStep = true;
            String clientData;
            ClientConnectionGUI.showMainScreen();
        }

        else if (receivedData.trim().startsWith("repeat")) {
            isStep = true;
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

        else if (receivedData.trim().startsWith("turn-off-the-lights")) {
            isStep = true;
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

        else {
            if (!isStep) {
                ClientConnectionGUI.updateTextArea(receivedData);
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
