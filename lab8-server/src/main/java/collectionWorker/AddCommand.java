package collectionWorker;

import fileManager.CollectionManager;
import helpers.MethodReturn;
import helpers.Worker;
import model.Movie;
import modules.CommandOutput;
import modules.ServerConnection;
import request.ReadFromClient;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * A command that adds a new movie to the collection.
 */
public class AddCommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * A string containing information about how to use this command.
     */

    public static String name = "add";
    public static String info = name + " command:\n" +
            "   This command can add elem\n" +
            "   Syntax:\n" +
            "       add\n" +
            "   then program will require for u to input to console every field of this class\n";

    public void Adding(CommandOutput output) {
        MethodReturn custom = Worker.Code(null, output);
        HashMap<String, Consumer<String>> setters = custom.setters();
        try {
            output.append("turn-off-the-lights");
            output.append(Worker.inputText);
            output.sendOutput(ServerConnection.clientChannel);
            String values = ReadFromClient.readStringFromClient(ServerConnection.clientChannel);
            assert values != null;
            if (values != null) {
                String[] myValues = values.split("\n");
                int index = 0;
                for (String field : setters.keySet()) {
                    setters.get(field).accept(myValues[index].trim());
                    index++;
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        Movie movie = custom.movie();

        // add check for same id or same title and director
        Movie existingMovie = null;
        for (Movie m : collectionManager.getMovies()) {
            if (movie.getName() != null && m.getName() != null) {
                if (m.getName().replaceAll("\\s", "").equals(movie.getName().replaceAll("\\s", ""))) {
                    existingMovie = m;
                    break;
                }
            } else {
                System.out.println("movie name is null");
            }
        }

        if (existingMovie != null) {
            System.out.println("Movie with same title and director already exists in the collection");
            output.append("Movie with same title and director already exists in the collection");
            movie = null;
        } else {
            movie.setId(collectionManager.getRandomID());
            movie.setCreationDate(ZonedDateTime.now());

            // check if all fields are not null
            if (collectionManager.checkFieldsByNull(movie)) {
                collectionManager.addMovie(movie);
                output.append("Movie added to collection with ID " + movie.getId());
                System.out.println("Movie added to collection with ID " + movie.getId());
            } else {
                output.append(
                        "Something get wrong with movie, some fields start to be null, we will not add it to collection, try again later");
                System.out.println(
                        "Something get wrong with movie, some fields start to be null, we will not add it to collection, try again later");
            }

        }
    }

    @Override
    public void execute(CommandOutput output) {
        Adding(output);
    }

}
