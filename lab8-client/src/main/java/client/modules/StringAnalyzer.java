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
                Platform.runLater(() -> ClientConnectionGUI.showAuthWindow(ClientConnectionGUI.currentLocale));
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

        else if (receivedData.contains("show-d")) {
            isStep = true;
            String[] lines = receivedData.split("\n");
            if (lines.length >= 2) {
                if (lines[1].contains("empty")) {
                    System.out.println("Setuping clear collection");
                    MovieManager.Allmovies = null;
                } else {
                    String data = lines[1];
                    System.out.println("Parsing");
                    MovieManager.Allmovies = MovieParser.parseMovies(data);
                    System.out.println(MovieManager.Allmovies);
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

        else if (receivedData.contains("UserId")) {
            String[] tokens = receivedData.split("\n");
            if (tokens.length == 3) {
                try {
                    int UserId = Integer.parseInt(tokens[1]);
                    ClientConnection.userId = UserId;
                    List<Integer> ids = parseIntegerList(tokens[2]);
                    ClientConnection.ids = ids;
                    System.out.println("userId was setupped");

                } catch (Exception e) {
                    System.out.println("userId is invalid");
                }
            } else {
                System.out.println("Tokens length of userId is invalid");
            }
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

        else if (receivedData.contains("All movies have been successfully removed")) {
            HandleUserInput.SendCommand("show");
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

    public static List<Integer> parseIntegerList(String input) {
        List<Integer> list = new ArrayList<>();
        String[] parts = input.replaceAll("\\[|\\]", "").split(", ");
        for (String part : parts) {
            int number = Integer.parseInt(part);
            list.add(number);
        }
        return list;
    }
}
