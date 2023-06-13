package client.helpers;

import java.util.ArrayList;
import java.util.List;

import client.gui.ClientConnectionGUI;
import client.model.Movie;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MovieManager {
    public static List<Movie> movies = new ArrayList<>();
    private static Scene currentScene; // Reference to the current scene
    private static double yOffset = 0;

    public static void addMovie(Movie movie) {
        movies.add(movie);
        setupMovies(currentScene);
    }

    public static void removeMovie(Movie movie) {
        movies.remove(movie);
        setupMovies(currentScene);
    }

    // Other methods for modifying the movies list as needed

    public static void setCurrentScene(Scene scene) {
        currentScene = scene;
        setupMovies(scene);
    }

    public static void setupMovies(Scene scene) {
        Platform.runLater(() -> {
            System.out.println("Starting of setuping movies");
            ScrollPane scrollPane = (ScrollPane) scene.lookup("#scrollpane");
            AnchorPane anchorPane = (AnchorPane) scrollPane.getContent();
            VBox vBox = (VBox) anchorPane.lookup("#xvbox");
            GridPane original = (GridPane) anchorPane.lookup("#grid");

            if (original != null) {
                original.setVisible(true);
                System.out.println("Movies size is: " + movies.size());
                Double sum = 20.0;
                for (Movie movie : movies) {
                    System.out.println("Starting for movie: " + movie.getId());
                    GridPane copy = gridCopy(original);
                    sum += 160.0;

                    // for (Node child : copy.getChildren()) {
                    // Integer columnIndex = GridPane.getColumnIndex(child);
                    // Integer rowIndex = GridPane.getRowIndex(child);

                    // columnIndex = (columnIndex == null) ? 0 : columnIndex;
                    // rowIndex = (rowIndex == null) ? 0 : rowIndex;

                    // // if (columnIndex == 0 && rowIndex == 0 && child instanceof GridPane) {
                    // // GridPane innerGridPane = (GridPane) child;
                    // // for (Node innerChild : innerGridPane.getChildren()) {
                    // // Integer innerRowIndex = GridPane.getRowIndex(innerChild);
                    // // innerRowIndex = (innerRowIndex == null) ? 0 : innerRowIndex;

                    // // if (innerChild instanceof Label) {
                    // // if (innerRowIndex == 0) {
                    // // ((Label) innerChild).setText(movies.get(0).getName());
                    // // }
                    // // if (innerRowIndex == 1) {
                    // // ((Label) innerChild).setText(movies.get(0).getTagline());
                    // // }
                    // // if (innerRowIndex == 2) {
                    // // ((Label) innerChild).setText(movies.get(0).getId().toString());
                    // // }
                    // // }
                    // // }
                    // // }

                    // // 0 1
                    // // 0 2
                    // // ...
                    // // 1 ...

                    // }

                    VBox vboxCopy = new VBox(copy);

                    vboxCopy.setStyle(vBox.getStyle());

                    vboxCopy.setPrefHeight(vBox.getPrefHeight());
                    vboxCopy.setPrefWidth(vBox.getPrefWidth());

                    vboxCopy.setMaxHeight(vBox.getMaxHeight());
                    vboxCopy.setMinHeight(vBox.getMinHeight());

                    vboxCopy.setMaxWidth(vBox.getMaxWidth());
                    vboxCopy.setMinWidth(vBox.getMinWidth());

                    vboxCopy.setLayoutX(vBox.getLayoutX());
                    vboxCopy.setLayoutY(vBox.getLayoutY());

                    vboxCopy.setBorder(null);

                    System.out.println("Adding to scrollPane");
                    VBox.setMargin(copy, new Insets(sum, 0, 0, 0));
                    anchorPane.getChildren().add(vboxCopy);
                    System.out.println("Movie " + movie.getId() + " is done");
                }

                System.out.println(anchorPane.getChildren());
                System.out.println("Returning scene after updating");
            } else {
                System.out.println("GridPane is null, im sry");
            }
            ClientConnectionGUI.updateSceneNow(scene);
        });
    }

    public static GridPane gridCopy(GridPane original) {
        GridPane copy = new GridPane();
        copyProperties(original, copy);
        copySize(original, copy);
        for (Node child : original.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(child);
            Integer colIndex = GridPane.getColumnIndex(child);

            // Если индексы не заданы, считаем их нулями
            rowIndex = (rowIndex == null) ? 0 : rowIndex;
            colIndex = (colIndex == null) ? 0 : colIndex;

            if (child instanceof GridPane) {
                GridPane childCopy = gridCopy((GridPane) child);
                copySize(child, childCopy); // copy size
                copy.add(childCopy, colIndex, rowIndex);
            } else if (child instanceof Label) {
                Label labelCopy = copyLabel((Label) child);
                copy.add(labelCopy, colIndex, rowIndex);
            } else {
                // Если появятся другие типы узлов, сделайте для них копии.
            }
        }

        return copy;
    }

    private static void copySize(Node original, Node copy) {
        if (original instanceof Region && copy instanceof Region) {
            Region originalRegion = (Region) original;
            Region copyRegion = (Region) copy;

            copyRegion.setPrefWidth(originalRegion.getPrefWidth());
            copyRegion.setPrefHeight(originalRegion.getPrefHeight());
            copyRegion.setMinWidth(originalRegion.getMinWidth());
            copyRegion.setMinHeight(originalRegion.getMinHeight());
            copyRegion.setMaxWidth(originalRegion.getMaxWidth());
            copyRegion.setMaxHeight(originalRegion.getMaxHeight());
        }
    }

    private static void copyProperties(GridPane original, GridPane copy) {
        copy.setAlignment(original.getAlignment());
        copy.setHgap(original.getHgap());
        copy.setVgap(original.getVgap());
        copy.setPadding(original.getPadding());
        copy.setPrefWidth(original.getPrefWidth());
        copy.setPrefHeight(original.getPrefHeight());
        copy.setLayoutX(original.getLayoutX());
        copy.setLayoutY(original.getLayoutY());
        copy.setId(original.getId());
        copy.setBackground(original.getBackground());
        copy.setGridLinesVisible(original.isGridLinesVisible());

        copy.setBorder(original.getBorder());

        // и так далее...
    }

    private static Label copyLabel(Label original) {
        Label copy = new Label();
        copySize(original, copy);
        copy.setFont(original.getFont());
        copy.setTextFill(original.getTextFill());
        copy.setStyle(original.getStyle());
        copy.setAlignment(original.getAlignment());
        copy.setGraphic(original.getGraphic());
        copy.setBorder(original.getBorder());
        copy.setText(original.getText()); // добавили копирование текста
        // и так далее...
        return copy;
    }

}