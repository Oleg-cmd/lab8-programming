package client.helpers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import client.gui.ClientConnectionGUI;
import client.model.Movie;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MovieManager {
    public static List<Movie> movies = new ArrayList<>();
    private static Scene currentScene; // Reference to the current scene
    private static boolean init = true;

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
            VBox vBoxM = (VBox) anchorPane.lookup("#mainV");
            vBoxM.setSpacing(10);
            VBox vBox = (VBox) vBoxM.lookup("#xvbox");
            GridPane original = (GridPane) anchorPane.lookup("#grid");

            if (init) {
                System.out.println("Setuping filters");
                SetupFilters.setupOriginal(original);
            }

            init = true;

            if (original != null) {

                if (movies != null) {
                    System.out.println("Movies size is: " + movies.size());
                    Double sum = 200.0;

                    System.out.println("Clearing fields");
                    ObservableList<Node> children = vBoxM.getChildren();
                    List<Node> nodesToRemove = new ArrayList<>();

                    for (Node child : children) {
                        if (child != vBox) {
                            nodesToRemove.add(child);
                        }
                    }

                    vBoxM.getChildren().removeAll(nodesToRemove);
                    System.out.println("End of fields");

                    for (Movie movie : movies) {
                        System.out.println("Starting for movie: " + movie.getId());
                        GridPane copy = CopyHelper.gridCopy(original);
                        sum += 160.0;

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
                                            ((Label) innerChild).setText(movie.getDirector().getName());
                                            ((Label) innerChild).setOnMouseClicked(event -> {
                                                final Clipboard clipboard = Clipboard.getSystemClipboard();
                                                final ClipboardContent content = new ClipboardContent();
                                                content.putString(((Label) innerChild).getText());
                                                clipboard.setContent(content);
                                            });
                                        }
                                        if (innerRowIndex == 1) {
                                            ((Label) innerChild).setText(movie.getDirector().getBirthday()
                                                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

                                            ((Label) innerChild).setOnMouseClicked(event -> {
                                                final Clipboard clipboard = Clipboard.getSystemClipboard();
                                                final ClipboardContent content = new ClipboardContent();
                                                content.putString(((Label) innerChild).getText());
                                                clipboard.setContent(content);
                                            });
                                        }
                                        if (innerRowIndex == 2) {
                                            ((Label) innerChild)
                                                    .setText(String.valueOf(movie.getDirector().getHeight()));

                                            ((Label) innerChild).setOnMouseClicked(event -> {
                                                final Clipboard clipboard = Clipboard.getSystemClipboard();
                                                final ClipboardContent content = new ClipboardContent();
                                                content.putString(((Label) innerChild).getText());
                                                clipboard.setContent(content);
                                            });
                                        }

                                    }
                                }
                            }

                            if (columnIndex == 0 && rowIndex == 1 && child instanceof GridPane) {
                                GridPane innerGridPane = (GridPane) child;
                                for (Node innerChild : innerGridPane.getChildren()) {

                                    Integer innerRowIndex = GridPane.getRowIndex(innerChild);
                                    innerRowIndex = (innerRowIndex == null) ? 0 : innerRowIndex;

                                    if (innerChild instanceof Label) {
                                        if (innerRowIndex == 0) {
                                            ((Label) innerChild).setText(movie.getDirector().getEyeColor().toString());

                                            ((Label) innerChild).setOnMouseClicked(event -> {
                                                final Clipboard clipboard = Clipboard.getSystemClipboard();
                                                final ClipboardContent content = new ClipboardContent();
                                                content.putString(((Label) innerChild).getText());
                                                clipboard.setContent(content);
                                            });
                                        }
                                        if (innerRowIndex == 2) {
                                            // System.out.println(movie.getDirector().getLocation().getName());
                                            ((Label) innerChild).setText(movie.getDirector().getLocation().getName());
                                            ((Label) innerChild).setOnMouseClicked(event -> {
                                                final Clipboard clipboard = Clipboard.getSystemClipboard();
                                                final ClipboardContent content = new ClipboardContent();
                                                content.putString(((Label) innerChild).getText());
                                                clipboard.setContent(content);
                                            });
                                        }
                                    }

                                    if (innerChild instanceof GridPane) {
                                        if (innerRowIndex == 1) {

                                            GridPane innerInnerGridPane = (GridPane) innerChild;
                                            for (Node innerInnerChild : innerInnerGridPane.getChildren()) {
                                                Integer innerInnerRowIndex = GridPane.getColumnIndex(innerInnerChild);
                                                innerInnerRowIndex = (innerInnerRowIndex == null) ? 0
                                                        : innerInnerRowIndex;

                                                if (innerInnerChild instanceof Label) {
                                                    if (innerInnerRowIndex == 0) {

                                                        ((Label) innerInnerChild).setText(String
                                                                .valueOf(movie.getDirector().getLocation().getX()));

                                                        ((Label) innerInnerChild).setOnMouseClicked(event -> {
                                                            final Clipboard clipboard = Clipboard.getSystemClipboard();
                                                            final ClipboardContent content = new ClipboardContent();
                                                            content.putString(((Label) innerInnerChild).getText());
                                                            clipboard.setContent(content);
                                                        });

                                                    }
                                                    if (innerInnerRowIndex == 1) {

                                                        ((Label) innerInnerChild).setText(String
                                                                .valueOf(movie.getDirector().getLocation().getY()));

                                                        ((Label) innerInnerChild).setOnMouseClicked(event -> {
                                                            final Clipboard clipboard = Clipboard.getSystemClipboard();
                                                            final ClipboardContent content = new ClipboardContent();
                                                            content.putString(((Label) innerInnerChild).getText());
                                                            clipboard.setContent(content);
                                                        });
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }

                            if (columnIndex == 1 && rowIndex == 0 && child instanceof GridPane) {
                                GridPane innerGridPane = (GridPane) child;
                                for (Node innerChild : innerGridPane.getChildren()) {
                                    Integer innerRowIndex = GridPane.getColumnIndex(innerChild);
                                    innerRowIndex = (innerRowIndex == null) ? 0 : innerRowIndex;

                                    if (innerChild instanceof Label) {
                                        if (innerRowIndex == 0) {
                                            ((Label) innerChild).setText(String.valueOf(movie.getCoordinates().getX()));
                                            ((Label) innerChild).setOnMouseClicked(event -> {
                                                final Clipboard clipboard = Clipboard.getSystemClipboard();
                                                final ClipboardContent content = new ClipboardContent();
                                                content.putString(((Label) innerChild).getText());
                                                clipboard.setContent(content);
                                            });
                                        }
                                        if (innerRowIndex == 1) {
                                            ((Label) innerChild).setText(String.valueOf(movie.getCoordinates().getY()));
                                            ((Label) innerChild).setOnMouseClicked(event -> {
                                                final Clipboard clipboard = Clipboard.getSystemClipboard();
                                                final ClipboardContent content = new ClipboardContent();
                                                content.putString(((Label) innerChild).getText());
                                                clipboard.setContent(content);
                                            });
                                        }
                                    }
                                }
                            }

                            if (columnIndex == 2 && rowIndex == 0 && child instanceof Label) {
                                Label innerLabel = (Label) child;
                                innerLabel.setText(String.valueOf(movie.getOscarsCount()));
                                innerLabel.setOnMouseClicked(event -> {
                                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                                    final ClipboardContent content = new ClipboardContent();
                                    content.putString(innerLabel.getText());
                                    clipboard.setContent(content);
                                });
                            }

                            if (columnIndex == 3 && rowIndex == 0 && child instanceof Label) {
                                Label innerLabel = (Label) child;
                                ((Label) innerLabel).setText(movie.getTagline());
                                innerLabel.setOnMouseClicked(event -> {
                                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                                    final ClipboardContent content = new ClipboardContent();
                                    content.putString(innerLabel.getText());
                                    clipboard.setContent(content);
                                });
                            }

                            if (columnIndex == 4 && rowIndex == 0 && child instanceof Label) {
                                Label innerLabel = (Label) child;
                                innerLabel.setText(String.valueOf(movie.getId()));
                                innerLabel.setOnMouseClicked(event -> {
                                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                                    final ClipboardContent content = new ClipboardContent();
                                    content.putString(innerLabel.getText());
                                    clipboard.setContent(content);
                                });
                            }

                            if (columnIndex == 1 && rowIndex == 1 && child instanceof Label) {
                                Label innerLabel = (Label) child;
                                ((Label) innerLabel).setText(movie.getName());
                                innerLabel.setOnMouseClicked(event -> {
                                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                                    final ClipboardContent content = new ClipboardContent();
                                    content.putString(innerLabel.getText());
                                    clipboard.setContent(content);
                                });
                            }

                            if (columnIndex == 2 && rowIndex == 1 && child instanceof Label) {
                                Label innerLabel = (Label) child;
                                innerLabel.setText(String.valueOf(movie.getGoldenPalmCount()));
                                innerLabel.setOnMouseClicked(event -> {
                                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                                    final ClipboardContent content = new ClipboardContent();
                                    content.putString(innerLabel.getText());
                                    clipboard.setContent(content);
                                });
                            }

                            if (columnIndex == 3 && rowIndex == 1 && child instanceof Label) {
                                Label innerLabel = (Label) child;
                                ((Label) innerLabel).setText(movie.getMpaaRating().toString());
                                innerLabel.setOnMouseClicked(event -> {
                                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                                    final ClipboardContent content = new ClipboardContent();
                                    content.putString(innerLabel.getText());
                                    clipboard.setContent(content);
                                });
                            }

                            if (columnIndex == 4 && rowIndex == 1 && child instanceof Label) {
                                Label innerLabel = (Label) child;
                                ((Label) innerLabel).setText(movie.getCreationDate()
                                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

                                innerLabel.setOnMouseClicked(event -> {
                                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                                    final ClipboardContent content = new ClipboardContent();
                                    content.putString(innerLabel.getText());
                                    clipboard.setContent(content);
                                });
                            }

                        }

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

                        vBoxM.getChildren().add(vboxCopy);
                        System.out.println("Movie " + movie.getId() + " is done");
                    }

                    System.out.println(anchorPane.getChildren());
                    System.out.println("Returning scene after updating");
                } else {
                    System.out.println("Movies is null, collection is empty, setup empty view");
                    ObservableList<Node> children = vBoxM.getChildren();
                    List<Node> nodesToRemove = new ArrayList<>();

                    for (Node child : children) {
                        if (child != vBox) {
                            nodesToRemove.add(child);
                        }
                    }

                    vBoxM.getChildren().removeAll(nodesToRemove);

                    System.out.println("View was cleared");
                }
            } else {
                System.out.println("GridPane is null, im sry");
            }
            ClientConnectionGUI.updateSceneNow(scene);
            ClientConnectionGUI.clearTextArea();
        });
    }
    // [Movie{id=8, name='name', coordinates=Coordinates{x=2.23, y=4.0},

}