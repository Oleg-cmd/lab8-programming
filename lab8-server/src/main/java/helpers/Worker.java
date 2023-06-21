package helpers;

import collectionWorker.Command;
import fileManager.CollectionManager;
import modules.CommandOutput;
import modules.ServerConnection;

import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

public class Worker implements Command {
    private static CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        Worker.collectionManager = collectionManager;
    }

    /**
     * Prompts the user to input information for a new movie, creates the movie, and
     * adds it to the collection.
     *
     * @return
     * @throws IOException If an I/O error occurs.
     */

    public static String inputText = "\n" +
            "    directorBirthday: LocalDate (e.g. \"1990-05-01\")\n" +
            "    oscarsCount: Integer (e.g. \"5\")\n" +
            "    goldenPalmCount: Integer (e.g. \"3\")\n" +
            "    directorName: String (e.g. \"Frank Darabont\")\n" +
            "    directorHeight: Integer (e.g. \"183\")\n" +
            "    name: String (e.g. \"The Shawshank Redemption\")\n" +
            "    coordinates: Float (e.g. \"45.123 67.890\")\n" +
            "    tagline: String (e.g. \"Fear can hold you prisoner. Hope can set you free.\")\n" +
            "    directorEyeColor: String (e.g. \"BLUE\")\n" +
            "    location: Double, String (e.g. \"45.123 67.890 MyPLace\")\n" +
            "    mpaaRating: String (e.g. \"PG_13\")\n";

    public static MethodReturn Code(Movie currentMovie, CommandOutput output) {

        System.out.println("Woker - Code - start");

        Movie movie = Objects.requireNonNullElseGet(currentMovie, Movie::new);
        System.out.println(movie);

        HashMap<String, Consumer<String>> setters = new HashMap<>();

        setters.put("name", movie::setName);

        setters.put("coordinates", s -> {

            String[] coordinates = s.split(" ");
            if (coordinates.length != 2) {
                String err = "Invalid coordinates format (outside)\n";
                output.append(err);
                output.sendOutput(ServerConnection.clientChannel);
            } else {
                try {
                    float x = Float.parseFloat(coordinates[0]);
                    float y = Float.parseFloat(coordinates[1]);
                    Coordinates myCoordinates = new Coordinates();
                    myCoordinates.setX(x);
                    myCoordinates.setY(y);
                    movie.setCoordinates(myCoordinates);
                } catch (NumberFormatException e) {
                    String err = "Invalid coordinates format (inside)\n";
                    output.append(err);
                    output.sendOutput(ServerConnection.clientChannel);
                }
            }

        });

        Person director;
        if (movie.getDirector() != null) {
            director = movie.getDirector();
        } else {
            director = new Person();
        }

        setters.put("oscarsCount", s -> {

            try {
                movie.setOscarsCount(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                String err = "Invalid oscarsCount format\n";
                output.append(err);
                output.sendOutput(ServerConnection.clientChannel);
            }

        });

        setters.put("goldenPalmCount", s -> {
            try {
                movie.setGoldenPalmCount(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                String err = "Invalid goldenPalmCount format\n";
                output.append(err);
                output.sendOutput(ServerConnection.clientChannel);
            }
        });

        setters.put("tagline", movie::setTagline);

        setters.put("mpaaRating", s -> {
            try {
                movie.setMpaaRating(MpaaRating.valueOf(s));
            } catch (IllegalArgumentException e) {
                String err = "Invalid mpaaRating format\n";
                output.append(err);
                output.sendOutput(ServerConnection.clientChannel);
            }
        });

        setters.put("directorName", director::setName);

        setters.put("directorHeight", s -> {
            try {
                director.setHeight(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                String err = "Invalid directorHeight format\n";
                output.append(err);
                output.sendOutput(ServerConnection.clientChannel);
            }
        });

        setters.put("directorEyeColor", s -> {
            try {
                director.setEyeColor(Color.valueOf(s));
            } catch (IllegalArgumentException e) {
                String err = "Invalid directorEyeColor format\n";
                output.append(err);
                output.sendOutput(ServerConnection.clientChannel);
            }
        });

        setters.put("directorBirthday", s -> {
            try {
                LocalDate birthday = LocalDate.parse(s);
                ZoneId zoneId = ZoneId.of("Europe/Moscow"); // Or use your desired time zone
                ZonedDateTime zonedDateTime = birthday.atStartOfDay(zoneId);
                director.setBirthday(zonedDateTime);
            } catch (DateTimeParseException e) {
                String err = "Invalid directorBirthday format\n";
                output.append(err);
                output.sendOutput(ServerConnection.clientChannel);
            }
        });

        setters.put("location", s -> {
            String[] locationValues = s.split(" ");
            if (locationValues.length != 3) {
                String err = "Invalid location format (outside)\n";
                output.append(err);
                output.sendOutput(ServerConnection.clientChannel);
            } else {
                try {
                    double x = Double.parseDouble(locationValues[0]);
                    double y = Double.parseDouble(locationValues[1]);
                    String name = locationValues[2];
                    Location location = new Location();
                    location.setLocation(x, y, name);
                    director.setLocation(location);
                } catch (NumberFormatException e) {
                    String err = "Invalid location format (inside)\n";
                    output.append(err);
                    output.sendOutput(ServerConnection.clientChannel);
                }
            }
        });

        movie.setCreationDate(ZonedDateTime.now());
        movie.setDirector(director);

        System.out.println("Woker - Code - end");
        return new MethodReturn(setters, movie);
    }

    public void execute(CommandOutput output) {
    }
}
