package db;

import collectionWorker.Command;
import fileManager.CollectionManager;
import model.Movie;
import modules.CommandOutput;

import java.util.Collection;

public class dbHelperCommand implements Command {
    private static CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        dbHelperCommand.collectionManager = collectionManager;
    }

    public static void preLoad(Collection<Movie> movies, CollectionManager local){
        local.addAllMovies(movies);
    }

    @Override
    public void execute(CommandOutput output) {
    }
}
