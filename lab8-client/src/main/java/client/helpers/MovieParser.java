package client.helpers;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.model.Color;
import client.model.Coordinates;
import client.model.Location;
import client.model.Movie;
import client.model.MpaaRating;
import client.model.Person;

public class MovieParser {

    public static List<Movie> parseMovies(String input) {
        List<Movie> movies = new ArrayList<>();

        String moviePattern = "Movie\\{" +
                "id=(\\d+), " +
                "name='(.*?)', " +
                "coordinates=Coordinates\\{x=(.*?), y=(.*?)\\}, " +
                "creationDate=(.*?), " +
                "oscarsCount=(\\d+), " +
                "goldenPalmCount=(\\d+), " +
                "tagline='(.*?)', " +
                "mpaaRating=(.*?), " +
                "director=Director\\{, " +
                "name='(.*?)', " +
                "birthday='(.*?)', " +
                "height='(.*?)', " +
                "eyeColor='(.*?)', " +
                "Location='Location\\{x=(.*?), y=(.*?), name='(.*?)'\\}\\}";

        Pattern pattern = Pattern.compile(moviePattern);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            // Извлекаем значения полей
            int id = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2);
            float x = Float.parseFloat(matcher.group(3));
            float y = Float.parseFloat(matcher.group(4));
            ZonedDateTime creationDate = ZonedDateTime.parse(matcher.group(5));
            int oscarsCount = Integer.parseInt(matcher.group(6));
            int goldenPalmCount = Integer.parseInt(matcher.group(7));
            String tagline = matcher.group(8);
            MpaaRating mpaaRating = MpaaRating.valueOf(matcher.group(9));

            String directorName = matcher.group(10);
            ZonedDateTime birthday = ZonedDateTime.parse(matcher.group(11));
            float height = Float.parseFloat(matcher.group(12));
            String eyeColorString = matcher.group(13);
            Color eyeColor = null;
            if (!eyeColorString.equals("null")) {
                eyeColor = Color.valueOf(eyeColorString);
            }
            double locationX = Double.parseDouble(matcher.group(14));
            double locationY = Double.parseDouble(matcher.group(15));
            String locationName = matcher.group(16);

            Location location = new Location(locationX, locationY, locationName);
            Person director = new Person(directorName, birthday, height, eyeColor, location);

            // Создаем объект Movie и добавляем его в список
            Movie movie = new Movie(id, name, new Coordinates(x, y), creationDate, oscarsCount, goldenPalmCount,
                    tagline, mpaaRating, director);
            movies.add(movie);
        }

        return movies;
    }

    public static void main(String args[]) {
        List<Movie> movies = parseMovies(
                "Movie{id=6, name='the', coordinates=Coordinates{x=45.124, y=54.89}, creationDate=2023-05-15T00:00+03:00[Europe/Moscow], oscarsCount=5, goldenPalmCount=3, tagline='fear', mpaaRating=PG, director=Director{, name='fr', birthday='1990-05-01T00:00+04:00[Europe/Moscow]', height='183.0', eyeColor='null', Location='Location{x=45.124000549316406, y=54.88999938964844, name='fr'}'}}Movie{id=7, name='name', coordinates=Coordinates{x=2.23, y=4.52}, creationDate=2023-02-20T00:00+03:00[Europe/Moscow], oscarsCount=2, goldenPalmCount=2, tagline='here', mpaaRating=PG, director=Director{, name='name', birthday='1980-10-10T00:00+03:00[Europe/Moscow]', height='23.0', eyeColor='null', Location='Location{x=2.2300000190734863, y=4.519999980926514, name='name'}'}}");
        System.out.println(movies);
    }
}
