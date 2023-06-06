package client.helpers;

import client.model.Coordinates;
import client.model.Location;
import client.model.Movie;
import client.model.MpaaRating;
import client.model.Person;
import javafx.scene.paint.Color;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieParser {

    public static List<Movie> parseMovies(String input) {
        List<Movie> movies = new ArrayList<>();

        // Создаем сканер для разделения строки на подстроки
        Scanner scanner = new Scanner(input);

        // Устанавливаем разделитель между объектами Movie
        scanner.useDelimiter("}Movie\\{");

        // Парсим каждый объект Movie
        while (scanner.hasNext()) {
            String movieString = scanner.next();
            Movie movie = parseMovie(movieString);
            movies.add(movie);
        }

        return movies;
    }

    private static Movie parseMovie(String movieString) {
        Movie movie = new Movie();

        // Создаем сканер для разделения подстроки на поля
        Scanner movieScanner = new Scanner(movieString);

        // Парсим каждое поле и устанавливаем его в объекте Movie
        while (movieScanner.hasNext()) {
            String fieldString = movieScanner.next();
            parseField(movie, fieldString);
        }

        return movie;
    }

    private static void parseField(Movie movie, String fieldString) {
        // Разделяем поле на имя и значение
        String[] fieldParts = fieldString.split("=");

        // Извлекаем имя и значение
        String fieldName = fieldParts[0].trim();
        String fieldValue = fieldParts[1].trim();

        // Устанавливаем значение в соответствующее поле объекта Movie
        switch (fieldName) {
            case "id":
                movie.setId(Integer.parseInt(fieldValue));
                break;
            case "name":
                movie.setName(fieldValue);
                break;
            case "coordinates":
                Coordinates coordinates = parseCoordinates(fieldValue);
                movie.setCoordinates(coordinates);
                break;
            case "creationDate":
                ZonedDateTime creationDate = ZonedDateTime.parse(fieldValue);
                movie.setCreationDate(creationDate);
                break;
            case "oscarsCount":
                movie.setOscarsCount(Integer.parseInt(fieldValue));
                break;
            case "goldenPalmCount":
                movie.setGoldenPalmCount(Integer.parseInt(fieldValue));
                break;
            case "tagline":
                movie.setTagline(fieldValue);
                break;
            case "mpaaRating":
                MpaaRating mpaaRating = MpaaRating.valueOf(fieldValue);
                movie.setMpaaRating(mpaaRating);
                break;
            case "director":
                Person director = parsePerson(fieldValue);
                movie.setDirector(director);
                break;
            default:
                // Обработка неизвестных полей, если необходимо
                break;
        }
    }

    private static Coordinates parseCoordinates(String coordinatesString) {
        // Разделяем строку координат на подстроки
        String[] coordinateParts = coordinatesString.split(",");

        // Извлекаем значения координат
        float x = Float.parseFloat(coordinateParts[0].substring(11));
        float y = Float.parseFloat(coordinateParts[1].substring(2, coordinateParts[1].length() - 1));

        // Создаем объект Coordinates и возвращаем его
        return new Coordinates(x, y);
    }

    private static Person parsePerson(String personString) {
        // Разделяем строку персоны на подстроки
        String[] personParts = personString.split(",");

        // Извлекаем значения полей персоны
        String name = personParts[0].substring(7);
        ZonedDateTime birthday = ZonedDateTime.parse(personParts[1].substring(11));
        Integer height = Integer.parseInt(personParts[2].substring(8));
        Color eyeColor = Color.valueOf(personParts[3].substring(10));
        String location = personParts[4].substring(11, personParts[4].length() - 1);

        // Создаем объект Person и возвращаем его
        // return new Person(name, birthday, height, eyeColor, location);
    }

    public static void main(String[] args) {
        String input = "Movie{id=6, name='the', coordinates=Coordinates{x=45.124, y=54.89}, " +
                "creationDate=2023-05-15T00:00+03:00[Europe/Moscow], oscarsCount=5, goldenPalmCount=3, " +
                "tagline='fear', mpaaRating=PG, director=Director{, name='fr', " +
                "birthday='1990-05-01T00:00+04:00[Europe/Moscow]', height='183.0', " +
                "eyeColor='null', Location='null'}}Movie{id=8, name='name', coordinates=Coordinates{x=2.23, y=4.0}, " +
                "creationDate=2023-03-11T00:00+03:00[Europe/Moscow], oscarsCount=23, goldenPalmCount=23, " +
                "tagline='tag', mpaaRating=R, director=Director{, name='name', " +
                "birthday='1990-05-01T00:00+04:00[Europe/Moscow]', height='23.0', " +
                "eyeColor='null', Location='null'}}Movie{id=7, name='name', coordinates=Coordinates{x=2.23, y=4.52}, " +
                "creationDate=2023-02-20T00:00+03:00[Europe/Moscow], oscarsCount=2, goldenPalmCount=2, " +
                "tagline='here', mpaaRating=PG, director=Director{, name='name', " +
                "birthday='1980-10-10T00:00+03:00[Europe/Moscow]', height='23.0', " +
                "eyeColor='null', Location='null'}}";

        List<Movie> movies = parseMovies(input);

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}
