package helpers;

import collectionWorker.Command;
import fileManager.CollectionManager;
import modules.CommandOutput;
import modules.ServerConnection;

import model.*;
import request.ReadFromClient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

public class Worker implements Command {
    private static CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        Worker.collectionManager = collectionManager;
    }
    /**
     * Prompts the user to input information for a new movie, creates the movie, and adds it to the collection.
     *
     * @return
     * @throws IOException If an I/O error occurs.
     */

    public static String inputText = "\n"+
            "    directorBirthday: LocalDate (e.g. \"1990-05-01\")\n" +
            "    oscarsCount: Integer (e.g. \"5\")\n" +
            "    goldenPalmCount: Integer (e.g. \"3\")\n" +
            "    directorName: String (e.g. \"Frank Darabont\")\n" +
            "    directorHeight: Integer (e.g. \"183\")\n" +
            "    name: String (e.g. \"The Shawshank Redemption\")\n" +
            "    coordinates: Float (e.g. \"45.123 67.890\")\n" +
            "    tagline: String (e.g. \"Fear can hold you prisoner. Hope can set you free.\")\n" +
            "    directorEyeColor: String (e.g. \"BLUE\")\n" +
            "    location: Double, String (e.g. \"45.123 67.890 MyPLace\")\n"+
            "    mpaaRating: String (e.g. \"PG_13\")\n";

    public static MethodReturn Code(Movie currentMovie, CommandOutput output) {

        System.out.println("Woker - Code - start");

        Movie movie = Objects.requireNonNullElseGet(currentMovie, Movie::new);

        System.out.println("Woker - Code - 1");
        HashMap<String, Consumer<String>> setters = new HashMap<>();

        System.out.println("Woker - Code - 2");

        setters.put("name", movie::setName);

        System.out.println("Woker - Code - 3");

        setters.put("coordinates", s -> {
            while (true){
                String[] coordinates = s.split(" ");
                if (coordinates.length != 2) {
                    String err = "repeat \n" +
                            "Invalid coordinates format\n" +
                            "write data again: \n";
                    output.append(err);
                    output.sendOutput(ServerConnection.clientChannel);
                    try {
                        s = Objects.requireNonNull(ReadFromClient.readStringFromClient(ServerConnection.clientChannel)).trim();
                    } catch (IOException ex) {
                        System.out.println("IOException in WORKER : coordinates :" + ex);
                    }
                } else {
                    try {
                        float x = Float.parseFloat(coordinates[0]);
                        float y = Float.parseFloat(coordinates[1]);
                        Coordinates myCoordinates = new Coordinates();
                        myCoordinates.setX(x);
                        myCoordinates.setY(y);
                        movie.setCoordinates(myCoordinates);
                        break;
                    } catch (NumberFormatException e) {
                        String err = "repeat \n" +
                                "Invalid coordinates format\n" +
                                "write data again: \n";
                        output.append(err);
                        output.sendOutput(ServerConnection.clientChannel);
                        try {
                            s = Objects.requireNonNull(ReadFromClient.readStringFromClient(ServerConnection.clientChannel)).trim();
                        } catch (IOException ex) {
                            System.out.println("IOException in WORKER : coordinates :" + ex);
                        }
                    }
                }
            }
        });
        System.out.println("Woker - Code - 4");

        Person director = new Person();

        System.out.println("Woker - Code - 5");

        setters.put("oscarsCount", s -> {
            while (true){
                try {
                    movie.setOscarsCount(Integer.parseInt(s));
                    break;
                }catch (NumberFormatException e){
                    String err = "repeat \n" +
                            "Invalid oscarsCount format\n" +
                            "write data again: \n";
                    output.append(err);
                    output.sendOutput(ServerConnection.clientChannel);
                    try {
                        s = Objects.requireNonNull(ReadFromClient.readStringFromClient(ServerConnection.clientChannel)).trim();
                    } catch (IOException ex) {
                        System.out.println("IOException in WORKER : oscarsCount :" + ex);
                    }
                }
            }
        });

        System.out.println("Woker - Code - 6");

        setters.put("goldenPalmCount", s -> {
            while (true){
                try {
                    movie.setGoldenPalmCount(Integer.parseInt(s));
                    break;
                }catch (NumberFormatException e){
                    String err = "repeat \n" +
                            "Invalid goldenPalmCount format\n" +
                            "write data again: \n";
                    output.append(err);
                    output.sendOutput(ServerConnection.clientChannel);
                    try {
                        s = Objects.requireNonNull(ReadFromClient.readStringFromClient(ServerConnection.clientChannel)).trim();
                    } catch (IOException ex) {
                        System.out.println("IOException in WORKER : goldenPalmCount :" + ex);
                    }
                }
            }
        });

        System.out.println("Woker - Code - 7");
        setters.put("tagline", movie::setTagline);

        setters.put("mpaaRating", s -> {
            while (true){
                try {
                    movie.setMpaaRating(MpaaRating.valueOf(s));
                    break;
                }catch (IllegalArgumentException e){
                    String err = "repeat \n" +
                            "Invalid mpaaRating format\n" +
                            "write data again: \n";
                    output.append(err);
                    output.sendOutput(ServerConnection.clientChannel);
                    try {
                        s = Objects.requireNonNull(ReadFromClient.readStringFromClient(ServerConnection.clientChannel)).trim();
                    } catch (IOException ex) {
                        System.out.println("IOException in WORKER : mpaaRating :" + ex);
                    }
                }
            }
        });
        System.out.println("Woker - Code - 8");

        setters.put("directorName", director::setName);
        System.out.println("Woker - Code - 9");
        setters.put("directorHeight", s -> {
            while (true){
                try {
                    director.setHeight(Integer.parseInt(s));
                    break;
                }catch (NumberFormatException e){
                    String err = "repeat \n" +
                            "Invalid directorHeight format\n" +
                            "write data again: \n";
                    output.append(err);
                    output.sendOutput(ServerConnection.clientChannel);
                    try {
                        s = Objects.requireNonNull(ReadFromClient.readStringFromClient(ServerConnection.clientChannel)).trim();
                    } catch (IOException ex) {
                        System.out.println("IOException in WORKER : directorHeight :" + ex);
                    }
                }
            }
        });

        System.out.println("Woker - Code - 10");

        setters.put("directorEyeColor", s -> {
            while (true){
                try {
                    director.setEyeColor(Color.valueOf(s));
                    break;
                }catch (IllegalArgumentException e){
                    String err = "repeat \n" +
                            "Invalid directorEyeColor format\n" +
                            "write data again: \n";
                    output.append(err);
                    output.sendOutput(ServerConnection.clientChannel);
                    try {
                        s = Objects.requireNonNull(ReadFromClient.readStringFromClient(ServerConnection.clientChannel)).trim();
                    } catch (IOException ex) {
                        System.out.println("IOException in WORKER : directorEyeColor :" + ex);
                    }
                }
            }
        });

        System.out.println("Woker - Code - 11");
        setters.put("directorBirthday", s -> {
            while (true){
                try {
                    LocalDate birthday = LocalDate.parse(s);
                    ZoneId zoneId = ZoneId.of("Europe/Moscow"); // Or use your desired time zone
                    ZonedDateTime zonedDateTime = birthday.atStartOfDay(zoneId);
                    director.setBirthday(zonedDateTime);
                    break;
                } catch (Exception e) {
                    String err = "repeat \n" +
                            "Invalid directorBirthday format\n" +
                            "write data again: \n";
                    output.append(err);
                    output.sendOutput(ServerConnection.clientChannel);
                    try {
                        s = Objects.requireNonNull(ReadFromClient.readStringFromClient(ServerConnection.clientChannel)).trim();
                    } catch (IOException ex) {
                        System.out.println("IOException in WORKER : directorBirthday :" + ex);
                    }
                }
            }
        });
        System.out.println("Woker - Code - 12");
        setters.put("location", s -> {
            while (true){
                String[] locationValues = s.split(" ");
                if (locationValues.length != 3) {
                    String err = "repeat \n" +
                            "Invalid location format\n" +
                            "write data again: \n";
                    output.append(err);
                    output.sendOutput(ServerConnection.clientChannel);
                    try {
                        s = Objects.requireNonNull(ReadFromClient.readStringFromClient(ServerConnection.clientChannel)).trim();
                    } catch (IOException ex) {
                        System.out.println("IOException in WORKER : location :" + ex);
                    }
                }else {
                    try {
                        double x = Double.parseDouble(locationValues[0]);
                        double y = Double.parseDouble(locationValues[1]);
                        String name = locationValues[2];
                        Location location = new Location();
                        location.setLocation(x, y, name);
                        director.setLocation(location);
                        break;
                    } catch (NumberFormatException e) {
                        String err = "repeat \n" +
                                "Invalid location format\n" +
                                "write data again: \n";
                        output.append(err);
                        output.sendOutput(ServerConnection.clientChannel);
                        try {
                            s = Objects.requireNonNull(ReadFromClient.readStringFromClient(ServerConnection.clientChannel)).trim();
                        } catch (IOException ex) {
                            System.out.println("IOException in WORKER : location :" + ex);
                        }
                    }
                }
            }
        });
        System.out.println("Woker - Code - 13");

        movie.setCreationDate(ZonedDateTime.now());
        System.out.println("Woker - Code - 14");
        movie.setDirector(director);
        System.out.println("Woker - Code - 15");


        System.out.println("Woker - Code - end");
        return new MethodReturn(setters, movie);
    }


    public void execute(CommandOutput output){}
}
