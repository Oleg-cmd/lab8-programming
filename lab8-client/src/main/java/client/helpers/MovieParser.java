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

}
