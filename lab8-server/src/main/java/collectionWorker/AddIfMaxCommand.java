package collectionWorker;

import fileManager.CollectionManager;
import helpers.UserInputHandler;
import model.Movie;
import model.MpaaRating;
import modules.CommandOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Command to add a new movie to the collection only if the movie's MPAA rating is greater than or equal to the
 * highest rated movie in the collection.
 */
public class AddIfMaxCommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    UserInputHandler userInputHandler;
    /**
     * A description of this command.
     */
    public static String name = "add_max";
    public static String info = name + " {args} command:\n" +
            "   This command will add new Movie if Rating of Movie that u want to enter is higher or equals some movies in collection\n" +
            "   Note: uses classic Add if it's okey with handler";


    /**
     * Constructs an AddIfMaxCommand with the given collection and user input handler.
     *
     */
    public AddIfMaxCommand() {
    }

    public static void AddMaxArg(String rate, String xml, CommandOutput output, String XMLData, CollectionManager collectionManager){
        try {
            // Sort the movies in the collection by MPAA rating
            Comparator<Movie> mpaaComparator = Comparator.comparingInt(movie -> movie.getMpaaRating().ordinal());
            List<Movie> sortedMovies = new ArrayList<>(collectionManager.getMovies());
            sortedMovies.sort(mpaaComparator);
            // Get the highest rated movie in the collection
            MpaaRating most = sortedMovies.get(0).getMpaaRating();
            // Prompt the user to enter the MPAA rating of the new movie
            try{
                MpaaRating movieRating = MpaaRating.valueOf(rate);
                // If the new movie's rating is greater than or equal to the highest rated movie in the collection, prompt the user to add the new movie
                if (most.ordinal() >= movieRating.ordinal()) {
                    writer.write("OK, you can add the new movie.");
                    output.append("OK, you can add the new movie.");
                    UserInputHandler.toExecute("add_xml " + xml);
                } else {
                    writer.write("I'm sorry, but your rating is too low.");
                    output.append("I'm sorry, but your rating is too low.");
                }
            }catch (IllegalArgumentException err){
                output.append("Illegal format");
                System.out.println("Illegal format");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Executes the command.
     *
     * The method prompts the user to enter the MPAA rating of the new movie to be added. If the rating is greater than
     * or equal to the highest rated movie in the collection, the user is prompted to enter the details of the new movie.
     * Otherwise, a message is printed to the console indicating that the rating is too low.
     */
    @Override
    public void execute(CommandOutput output) {
        try {
            // Sort the movies in the collection by MPAA rating
            Comparator<Movie> mpaaComparator = Comparator.comparingInt(movie -> movie.getMpaaRating().ordinal());
            List<Movie> sortedMovies = new ArrayList<>(collectionManager.getMovies());
            sortedMovies.sort(mpaaComparator);

            // Get the highest rated movie in the collection
            MpaaRating most = sortedMovies.get(0).getMpaaRating();

            // Prompt the user to enter the MPAA rating of the new movie
            writer.write("Enter movie MPAA rating to add: ");
            MpaaRating movieRating;
            try {
                movieRating = MpaaRating.valueOf(reader.readLine().trim());
                // If the new movie's rating is greater than or equal to the highest rated movie in the collection, prompt the user to add the new movie
                if (most.ordinal() >= movieRating.ordinal()) {
                    writer.write("OK, you can add the new movie.");
                    UserInputHandler.toExecute("add");
                } else {
                    writer.write("I'm sorry, but your rating is too low.");
                }
            }catch (IllegalArgumentException err){
                System.out.println("Illegal format");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}