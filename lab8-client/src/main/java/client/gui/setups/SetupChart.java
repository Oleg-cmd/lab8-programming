package client.gui.setups;

import java.util.List;

import client.helpers.MovieManager;
import client.model.Movie;
import client.modules.HandleUserInput;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class SetupChart {
    private static double lastX = 0;
    private static double lastY = 0;
    private static final double PAN_SPEED = 0.02;
    private static final double ZOOM_FACTOR = 0.1;
    private static XYChart.Series<Number, Number> seriesCurrentUser = new XYChart.Series<>();
    private static XYChart.Series<Number, Number> seriesOtherUsers = new XYChart.Series<>();
    private static ScatterChart<Number, Number> scatterChart;

    public static Scene setupChart(Scene scene) {

        System.out.println("Creating chart under");
        ScrollPane scrollPane = (ScrollPane) scene.lookup("#scrollpane");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        scatterChart = new ScatterChart<>(xAxis, yAxis);

        scatterChart.setLayoutX(scrollPane.getLayoutX()); // Измените oldVBox на реальное имя старого VBox
        scatterChart.setLayoutY(scrollPane.getLayoutY());
        scatterChart.setPrefSize(scrollPane.getPrefWidth(), scrollPane.getPrefHeight());
        scatterChart.setStyle("-fx-background-color: #FFFFFF;");
        scatterChart.setVisible(false);
        scatterChart.setLegendVisible(false);
        // Предположим, что AnchorPane является корневым элементом вашей сцены
        AnchorPane rootPane = (AnchorPane) scene.getRoot();
        rootPane.getChildren().add(scatterChart);

        System.out.println("End of adding chart");

        Button visual = (Button) scene.lookup("#visual");
        visual.setOnAction(event -> {
            if (!scrollPane.isVisible()) {
                HandleUserInput.SendCommand("show");
            } else if (!scatterChart.isVisible()) {
                HandleUserInput.SendCommand("get_all");
            }
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                System.out.println(e);
            }

            scatterChart.getData().clear();

            seriesCurrentUser = new XYChart.Series<>();
            seriesCurrentUser.setName("Current user");
            seriesOtherUsers = new XYChart.Series<>();
            seriesOtherUsers.setName("Other users");

            List<Movie> allMovies = MovieManager.Allmovies;
            List<Movie> movies = MovieManager.movies;
            System.out.println("Movies size while adding to chart: " + allMovies.size());

            for (Movie movie : allMovies) {
                boolean isCurrentUserMovie = false;
                for (Movie userMovie : movies) {
                    if (movie.getId().equals(userMovie.getId())) {
                        isCurrentUserMovie = true;
                        break;
                    }
                }

                XYChart.Data<Number, Number> data = new XYChart.Data<>(movie.getCoordinates().getX(),
                        movie.getCoordinates().getY());

                seriesCurrentUser.getData().add(data);
                addDataNodeAnimation(data, movie, isCurrentUserMovie);

            }

            scatterChart.getData().addAll(seriesCurrentUser, seriesOtherUsers);

            // Переключаем видимость
            scrollPane.setVisible(!scrollPane.isVisible());
            scatterChart.setVisible(!scatterChart.isVisible());
        });

        scatterChart.setOnMouseDragged(event -> {
            double deltaX = (event.getX() - lastX) * PAN_SPEED;
            double deltaY = (event.getY() - lastY) * PAN_SPEED;

            double newXLower = xAxis.getLowerBound() - deltaX;
            double newXUpper = xAxis.getUpperBound() - deltaX;

            double newYLower = yAxis.getLowerBound() - deltaY;
            double newYUpper = yAxis.getUpperBound() - deltaY;

            xAxis.setLowerBound(newXLower);
            xAxis.setUpperBound(newXUpper);

            yAxis.setLowerBound(newYLower);
            yAxis.setUpperBound(newYUpper);

            lastX = event.getX();
            lastY = event.getY();
            event.consume();
        });

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        zoomChart(1 + ZOOM_FACTOR);
                        break;
                    case DOWN:
                        zoomChart(1 - ZOOM_FACTOR);
                        break;
                    default:
                        break;
                }
            }
        });

        return scene;
    }

    private static void addDataNodeAnimation(XYChart.Data<Number, Number> data, Movie movie, boolean isCurrentUser) {
        Node tempNode;
        if (isCurrentUser) {
            String imageUrl = "/assets/images/clapperboard.png";
            ImageView currentUserImageView = new ImageView(new Image(imageUrl));
            currentUserImageView.setFitWidth(32);
            currentUserImageView.setFitHeight(32);
            currentUserImageView.setPreserveRatio(true);
            tempNode = currentUserImageView;
            tempNode.getStyleClass().add("series0");
        } else {
            tempNode = new Circle(10);
            tempNode.getStyleClass().add("series1");
        }

        final Node node = tempNode;

        RotateTransition rt = new RotateTransition(Duration.millis(200), node);
        rt.setByAngle(20);
        rt.setAutoReverse(true);
        rt.setCycleCount(2);
        rt.statusProperty().addListener((obs, oldStatus, newStatus) -> {
            if (newStatus == Animation.Status.STOPPED) {
                node.setRotate(0);
            }
        });

        Label tooltipText = new Label(movie.toString());
        tooltipText.setWrapText(true);
        tooltipText.setMaxWidth(200);
        tooltipText.setStyle("-fx-font-size: 14px; -fx-max-width: 350px;");

        Tooltip tooltip = new Tooltip();
        tooltip.setGraphic(tooltipText);
        Tooltip.install(node, tooltip);

        node.setOnMouseEntered(event -> {
            rt.playFromStart();
        });
        node.setOnMouseExited(event -> rt.stop());

        node.setOnMouseClicked(e -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(movie.toString());
            clipboard.setContent(content);
        });

        data.setNode(node);
    }

    private static void zoomChart(double scaleFactor) {
        NumberAxis xAxis = (NumberAxis) scatterChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) scatterChart.getYAxis();

        double currentXLower = xAxis.getLowerBound();
        double currentXUpper = xAxis.getUpperBound();
        double currentYLower = yAxis.getLowerBound();
        double currentYUpper = yAxis.getUpperBound();

        double newRangeX = (currentXUpper - currentXLower) * scaleFactor;
        double newRangeY = (currentYUpper - currentYLower) * scaleFactor;

        double newLowerX = currentXLower - (newRangeX - (currentXUpper - currentXLower)) / 2;
        double newUpperX = currentXUpper + (newRangeX - (currentXUpper - currentXLower)) / 2;
        double newLowerY = currentYLower - (newRangeY - (currentYUpper - currentYLower)) / 2;
        double newUpperY = currentYUpper + (newRangeY - (currentYUpper - currentYLower)) / 2;

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(newLowerX);
        xAxis.setUpperBound(newUpperX);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(newLowerY);
        yAxis.setUpperBound(newUpperY);
    }
}
