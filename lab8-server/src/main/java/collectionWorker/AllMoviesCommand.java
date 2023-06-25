package collectionWorker;

import java.util.Collection;

import fileManager.CollectionManager;
import model.Movie;
import modules.CommandOutput;

public class AllMoviesCommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(CommandOutput output) {
        Collection<Movie> movies = collectionManager.getAllMovies();
        StringBuilder sb = new StringBuilder();
        ;
        if (movies.isEmpty()) {
            sb.append("show-d\n");
            sb.append("The collection is empty.");
        } else {
            sb.append("show-d\n");
            for (Movie movie : movies) {
                sb.append(movie.toString());
                sb.append("###");
            }
        }

        System.out.println(sb);
        output.append(String.valueOf(sb));
    }
}
