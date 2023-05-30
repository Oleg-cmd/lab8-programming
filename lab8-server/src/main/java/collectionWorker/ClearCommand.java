package collectionWorker;

import fileManager.CollectionManager;
import modules.CommandOutput;

import java.io.IOException;

/**
 * This command clears all movies in the collection.
 */
public class ClearCommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    /**
     * A brief description of the command.
     */
    public static String name = "clear";
    public static String info = name + " command:\n" +
            "   This command will clear all movies in the collection (only on yours of courses).\n" +
            "   Note: there is no auto-save to file.";



    /**
     * Constructs a ClearCommand with a CollectionManager.
     */
    public ClearCommand() {

    }

    /**
     * Executes the command to clear all movies in the collection.
     */
    @Override
    public void execute(CommandOutput output) {
        try{
            if (collectionManager.getMovies().isEmpty()) {
                writer.write("The collection is already empty.");
                output.append("The collection is already empty.");
                return;
            }else {
                collectionManager.clearMovies();
            }

            writer.write("All movies have been successfully removed from the collection.");
            output.append("All movies have been successfully removed from the collection.");
        }catch (IOException e ){
            System.out.println(e);
        }




    }
}