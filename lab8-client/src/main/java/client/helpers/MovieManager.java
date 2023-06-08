package client.helpers;

import java.util.ArrayList;
import java.util.List;

import client.gui.ClientConnectionGUI;
import client.model.Movie;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
            GridPane original = (GridPane) anchorPane.lookup("#grid");

            if (original != null) {
                original.setVisible(false);

                for (Movie movie : movies) {
                    System.out.println("Starting for movie: " + movie.getId());
                    GridPane copy = gridCopy(original);

                    for (Node child : copy.getChildren()) {
                        Integer columnIndex = GridPane.getColumnIndex(child);
                        Integer rowIndex = GridPane.getRowIndex(child);

                        columnIndex = (columnIndex == null) ? 0 : columnIndex;
                        rowIndex = (rowIndex == null) ? 0 : rowIndex;

                        if (columnIndex == 0 && rowIndex == 0 && child instanceof GridPane) {
                            GridPane innerGridPane = (GridPane) child;
                            for (Node innerChild : innerGridPane.getChildren()) {
                                Integer innerRowIndex = GridPane.getRowIndex(innerChild);
                                innerRowIndex = (innerRowIndex == null) ? 0 : innerRowIndex;

                                if (innerChild instanceof Label) {
                                    if (innerRowIndex == 0) {
                                        ((Label) innerChild).setText(movie.getName());
                                    }
                                    if (innerRowIndex == 1) {
                                        ((Label) innerChild).setText(movie.getTagline());
                                    }
                                    if (innerRowIndex == 2) {
                                        ((Label) innerChild).setText(movie.getId().toString());
                                    }
                                }
                            }
                        }

                        // 0 1
                        // 0 2
                        // ...
                        // 1 ...

                    }
                    System.out.println("Adding to scrollPane");
                    anchorPane.getChildren().addAll(copy);
                    System.out.println("Movie " + movie.getId() + " is done");
                }
                System.out.println("Returning scene after updating");
            } else {
                System.out.println("GridPane is null, im sry");
            }
            ClientConnectionGUI.updateSceneNow(scene);
        });
    }

    public static GridPane gridCopy(GridPane original) {
        GridPane copy = new GridPane();
        // Копирование свойств
        copyProperties(original, copy);

        // Копирование дочерних элементов (Node)
        copyChildren(original, copy);

        yOffset += 10; // Увеличение смещения для следующей копии
        return copy;
    }

    private static void copyProperties(GridPane original, GridPane copy) {
        copy.setAlignment(original.getAlignment());
        copy.setHgap(original.getHgap());
        copy.setVgap(original.getVgap());
        copy.setPadding(original.getPadding());
        copy.setPrefWidth(original.getPrefWidth());
        copy.setPrefHeight(original.getPrefHeight());
        copy.setLayoutX(original.getLayoutX());
        copy.setLayoutY(original.getLayoutY() + yOffset);
        copy.setId(null);
        copy.setVisible(true);

        // Копирование фона
        copy.setBackground(original.getBackground());

        // Копирование видимости линий сетки
        copy.setGridLinesVisible(original.isGridLinesVisible());

        // ... продолжайте копирование других свойств
    }

    private static void copyChildren(GridPane original, GridPane copy) {
        List<Node> childrenCopy = new ArrayList<>();
        for (Node child : original.getChildren()) {
            Node childCopy;
            if (child instanceof GridPane) {
                childCopy = gridCopy((GridPane) child);
            } else if (child instanceof Label) {
                childCopy = copyLabel((Label) child);
            } else {
                childCopy = child;
            }
            childrenCopy.add(childCopy);
        }
        copy.getChildren().setAll(childrenCopy);
    }

    private static Label copyLabel(Label original) {
        Label copy = new Label();
        copy.setText(original.getText());
        copy.setFont(original.getFont());
        copy.setTextFill(original.getTextFill());
        copy.setStyle(original.getStyle());
        copy.setAlignment(original.getAlignment());
        copy.setGraphic(original.getGraphic());
        // ... продолжайте копирование других свойств
        return copy;
    }

}