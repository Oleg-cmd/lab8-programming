package client.helpers;

import java.util.Comparator;

import client.gui.setups.Setup;
import client.model.Movie;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SetupFilters {
    private static SortState sortState = new SortState();

    public static void setupOriginal(GridPane original) {
        Label directorLabel = new Label(null, null);
        Label birthdayLabel = new Label(null, null);
        Label heightLabel = new Label(null, null);
        Label eyeColorLabel = new Label(null, null);
        Label directorXLabel = new Label(null, null);
        Label directorYLabel = new Label(null, null);
        Label placeLabel = new Label(null, null);
        Label movieXLabel = new Label(null, null);
        Label movieYLabel = new Label(null, null);
        Label movieNameLabel = (Label) original.lookup("#movieName");
        Label oscarsCountLabel = (Label) original.lookup("#oscarsCount");
        Label goldenPalmCountLabel = (Label) original.lookup("#goldenPalmCount");
        Label taglineLabel = (Label) original.lookup("#tagline");
        Label mpaaRatingLabel = (Label) original.lookup("#mpaaRating");
        Label movieIdLabel = (Label) original.lookup("#movieId");
        Label creationDateLabel = (Label) original.lookup("#creationDate");

        for (Node child : original.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(child);
            Integer rowIndex = GridPane.getRowIndex(child);

            columnIndex = (columnIndex == null) ? 0 : columnIndex;
            rowIndex = (rowIndex == null) ? 0 : rowIndex;

            if (columnIndex == 0 && rowIndex == 0 && child instanceof GridPane) {
                GridPane innerGridPane = (GridPane) child;
                // System.out.println(innerGridPane.getChildren());
                directorLabel = (Label) innerGridPane.lookup("#director");
                // System.out.println(directorLabel);
                birthdayLabel = (Label) innerGridPane.lookup("#birthday");
                heightLabel = (Label) innerGridPane.lookup("#height");
            }

            if (columnIndex == 0 && rowIndex == 1 && child instanceof GridPane) {
                GridPane innerGridPane = (GridPane) child;

                eyeColorLabel = (Label) innerGridPane.lookup("#eyeColor");
                placeLabel = (Label) innerGridPane.lookup("#place");

                for (Node innerChild : innerGridPane.getChildren()) {
                    Integer innerRowIndex = GridPane.getRowIndex(innerChild);
                    innerRowIndex = (innerRowIndex == null) ? 0 : innerRowIndex;
                    if (innerChild instanceof GridPane) {
                        if (innerRowIndex == 1) {
                            GridPane innerInnerGridPane = (GridPane) innerChild;
                            directorXLabel = (Label) innerInnerGridPane.lookup("#directorX");
                            directorYLabel = (Label) innerInnerGridPane.lookup("#directorY");
                        }
                    }
                }
            }

            if (columnIndex == 1 && rowIndex == 0 && child instanceof GridPane) {
                GridPane innerGridPane = (GridPane) child;
                movieXLabel = (Label) innerGridPane.lookup("#movieX");
                movieYLabel = (Label) innerGridPane.lookup("#movieY");
            }
        }

        // MovieManager.movies

        directorLabel.setOnMouseClicked(event -> {
            System.out.println("directorLabel" + " was clicked");
            Comparator<Movie> directorComparator = Comparator.comparing(movie -> movie.getDirector().getName());

            if (sortState.isDirectorSortAscending()) {
                MovieManager.movies.sort(directorComparator);
            } else {
                MovieManager.movies.sort(directorComparator.reversed());
            }

            sortState.toggleDirectorSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        birthdayLabel.setOnMouseClicked(event -> {
            System.out.println("birthdayLabel" + " was clicked");
            Comparator<Movie> birthdayComparator = Comparator.comparing(movie -> movie.getDirector().getBirthday());

            if (sortState.isBirthdaySortAscending()) {
                MovieManager.movies.sort(birthdayComparator);
            } else {
                MovieManager.movies.sort(birthdayComparator.reversed());
            }

            sortState.toggleBirthdaySortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });
        heightLabel.setOnMouseClicked(event -> {
            System.out.println("heightLabel" + " was clicked");
            Comparator<Movie> heightComparator = Comparator.comparingDouble(movie -> movie.getDirector().getHeight());

            if (sortState.isHeightSortAscending()) {
                MovieManager.movies.sort(heightComparator);
            } else {
                MovieManager.movies.sort(heightComparator.reversed());
            }

            sortState.toggleHeightSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        eyeColorLabel.setOnMouseClicked(event -> {
            System.out.println("eyeColorLabel" + " was clicked");
            Comparator<Movie> eyeColorComparator = Comparator
                    .comparing(movie -> movie.getDirector().getEyeColor().ordinal());

            if (sortState.isEyeColorSortAscending()) {
                MovieManager.movies.sort(eyeColorComparator);
            } else {
                MovieManager.movies.sort(eyeColorComparator.reversed());
            }

            sortState.toggleEyeColorSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        directorXLabel.setOnMouseClicked(event -> {
            System.out.println("directorXLabel" + " was clicked");
            Comparator<Movie> directorXComparator = Comparator
                    .comparingDouble(movie -> movie.getDirector().getLocation().getX());

            if (sortState.isDirectorXSortAscending()) {
                MovieManager.movies.sort(directorXComparator);
            } else {
                MovieManager.movies.sort(directorXComparator.reversed());
            }

            sortState.toggleDirectorXSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        directorYLabel.setOnMouseClicked(event -> {
            System.out.println("directorYLabel" + " was clicked");
            Comparator<Movie> directorYComparator = Comparator
                    .comparingDouble(movie -> movie.getDirector().getLocation().getY());

            if (sortState.isDirectorYSortAscending()) {
                MovieManager.movies.sort(directorYComparator);
            } else {
                MovieManager.movies.sort(directorYComparator.reversed());
            }

            sortState.toggleDirectorYSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        placeLabel.setOnMouseClicked(event -> {
            System.out.println("placeLabel" + " was clicked");
            Comparator<Movie> placeComparator = Comparator
                    .comparing(movie -> movie.getDirector().getLocation().getName());

            if (sortState.isPlaceSortAscending()) {
                MovieManager.movies.sort(placeComparator);
            } else {
                MovieManager.movies.sort(placeComparator.reversed());
            }

            sortState.togglePlaceSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        movieXLabel.setOnMouseClicked(event -> {
            System.out.println("movieXLabel" + " was clicked");
            Comparator<Movie> movieXComparator = Comparator.comparingDouble(movie -> movie.getCoordinates().getX());

            if (sortState.isMovieXSortAscending()) {
                MovieManager.movies.sort(movieXComparator);
            } else {
                MovieManager.movies.sort(movieXComparator.reversed());
            }

            sortState.toggleMovieXSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        movieYLabel.setOnMouseClicked(event -> {
            System.out.println("movieYLabel" + " was clicked");
            Comparator<Movie> movieYComparator = Comparator.comparingDouble(movie -> movie.getCoordinates().getY());

            if (sortState.isMovieYSortAscending()) {
                MovieManager.movies.sort(movieYComparator);
            } else {
                MovieManager.movies.sort(movieYComparator.reversed());
            }

            sortState.toggleMovieYSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        movieNameLabel.setOnMouseClicked(event -> {
            System.out.println(movieNameLabel.toString() + " was clicked");
            Comparator<Movie> nameComparator = Comparator.comparing(movie -> movie.getName().length());

            if (sortState.isNameSortAscending()) {
                MovieManager.movies.sort(nameComparator);
            } else {
                MovieManager.movies.sort(nameComparator.reversed());
            }

            sortState.toggleNameSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        oscarsCountLabel.setOnMouseClicked(event -> {
            System.out.println(oscarsCountLabel.toString() + " was clicked");
            Comparator<Movie> oscarsCountComparator = Comparator.comparingInt(movie -> movie.getOscarsCount());

            if (sortState.isOscarsCountSortAscending()) {
                MovieManager.movies.sort(oscarsCountComparator);
            } else {
                MovieManager.movies.sort(oscarsCountComparator.reversed());
            }

            sortState.toggleOscarsCountSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        goldenPalmCountLabel.setOnMouseClicked(event -> {
            System.out.println(goldenPalmCountLabel.toString() + " was clicked");
            Comparator<Movie> goldenPalmCountComparator = Comparator.comparingInt(movie -> movie.getGoldenPalmCount());

            if (sortState.isGoldenPalmCountSortAscending()) {
                MovieManager.movies.sort(goldenPalmCountComparator);
            } else {
                MovieManager.movies.sort(goldenPalmCountComparator.reversed());
            }

            sortState.toggleGoldenPalmCountSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        taglineLabel.setOnMouseClicked(event -> {
            System.out.println(taglineLabel.toString() + " was clicked");
            Comparator<Movie> taglineComparator = Comparator.comparingInt(movie -> movie.getTagline().length());

            if (sortState.isTaglineSortAscending()) {
                MovieManager.movies.sort(taglineComparator);
            } else {
                MovieManager.movies.sort(taglineComparator.reversed());
            }

            sortState.toggleTaglineSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        mpaaRatingLabel.setOnMouseClicked(event -> {
            System.out.println(mpaaRatingLabel.toString() + " was clicked");
            Comparator<Movie> mpaaRatingComparator = Comparator.comparing(movie -> movie.getMpaaRating().ordinal());

            if (sortState.isMpaaRatingSortAscending()) {
                MovieManager.movies.sort(mpaaRatingComparator);
            } else {
                MovieManager.movies.sort(mpaaRatingComparator.reversed());
            }

            sortState.toggleMpaaRatingSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        movieIdLabel.setOnMouseClicked(event -> {
            System.out.println(movieIdLabel.toString() + " was clicked");
            Comparator<Movie> movieIdComparator = Comparator.comparingInt(movie -> movie.getId());

            if (sortState.isMovieIdSortAscending()) {
                MovieManager.movies.sort(movieIdComparator);
            } else {
                MovieManager.movies.sort(movieIdComparator.reversed());
            }

            sortState.toggleMovieIdSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

        creationDateLabel.setOnMouseClicked(event -> {
            System.out.println(creationDateLabel.toString() + " was clicked");
            Comparator<Movie> creationDateComparator = Comparator.comparing(movie -> movie.getCreationDate());

            if (sortState.isCreationDateSortAscending()) {
                MovieManager.movies.sort(creationDateComparator);
            } else {
                MovieManager.movies.sort(creationDateComparator.reversed());
            }

            sortState.toggleCreationDateSortOrder();

            // Ваш код обновления UI или других действий после сортировки
            MovieManager.setupMovies(Setup.setupScene);
        });

    }

}
