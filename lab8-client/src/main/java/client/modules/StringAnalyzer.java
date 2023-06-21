package client.modules;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import client.gui.controllers.ClientConnectionGUI;
import client.gui.setups.Setup;
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
                if (lines[1].contains("empty")) {
                    System.out.println("Setuping clear collection");
                    MovieManager.movies = null;
                    MovieManager.setupMovies(Setup.setupScene);
                } else {
                    String data = lines[1];
                    System.out.println("Parsing");
                    MovieManager.movies = MovieParser.parseMovies(data);
                    MovieManager.setupMovies(Setup.setupScene);
                    System.out.println(MovieManager.movies);
                }

            } else {
                System.out.println("Some kind of error was made by recieving data from server");
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

        else if (receivedData.contains("Invalid")) {
            isStep = true;
            ClientConnectionGUI.updateTextArea(receivedData);
            ClientConnectionGUI.updateInfoArea(receivedData);
        }

        else if (receivedData.contains("Auth completed!")) {
            isStep = true;
            String clientData;
            ClientConnectionGUI.showMainScreen();
        }

        else if (receivedData.trim().startsWith("turn-off-the-lights")) {
            isStep = true;
            String clientData;
            // System.out.println("im here");
            try {
                clientData = future.get();

                clientData += "turn-off-the-lights";
                try {
                    SendStringNow(String.valueOf(clientData), socketChannel);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
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
