package collectionWorker;

import fileManager.CollectionManager;
import model.Movie;
import modules.CommandOutput;

import java.io.IOException;
import java.util.HashSet;

/**
 * The ShowCommand class implements the Command interface and represents the
 * "show" command.
 * This command is used to display all the movies in the collection in a string
 * format.
 */

public class ShowCommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public static String name = "show";
    public static String info = name + " command:\n" +
            "   This command will show u all your collection in string format\n";

    /**
     * Executes the "show" command by getting all the movies in the collection and
     * displaying them in a string format.
     */

    @Override
    public void execute(CommandOutput output) {
        HashSet<Movie> movies = collectionManager.getMovies();
        StringBuilder sb = new StringBuilder();
        ;
        if (movies.isEmpty()) {
            sb.append("show-c\n");
            sb.append("The collection is empty.");
        } else {
            sb.append("show-c\n");
            for (Movie movie : movies) {
                sb.append(movie.toString());
                sb.append("###");
            }
        }

        System.out.println(sb);
        output.append(String.valueOf(sb));
    }
}