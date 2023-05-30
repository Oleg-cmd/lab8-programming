package collectionWorker;

import fileManager.CollectionManager;
import modules.CommandOutput;

import java.io.IOException;

/**
 This class represents a command to remove a movie from a collection by its ID.
 */
public class RemoveByIdCommand implements Command {

    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public static String name = "remove_by_id";
    public static String info = name + " {id} command:\n" +
            "   This command will delete model with id that u entered (if this model exist)\n";

    /**
     * Creates a new instance of the command with the given collection manager, reader, and writer.
     */

    public RemoveByIdCommand() {
    }

    public static void RemoveByArg(String idStr, CommandOutput output, CollectionManager collectionManager){
        try{
            try {
                Integer id = Integer.parseInt(idStr);
                boolean removed = collectionManager.removeId(id);
                if (removed) {
                    writer.write("Movie with ID " + id + " has been removed.");
                    output.append("Movie with ID " + id + " has been removed.");
                } else {
                    writer.write("No movie found with ID " + id + ".");
                    output.append("No movie found with ID " + id + ".");
                }
            } catch (NumberFormatException e) {
                writer.write("Invalid input. ID must be an integer.");
                output.append("Invalid input. ID must be an integer.");
            }

            writer.newLine();
            writer.flush();

        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Executes the command.
     *
     * Asks the user to input an ID of a movie to remove, removes the movie from the collection,
     * and outputs a message indicating whether the movie was removed.
     */
    @Override
    public void execute(CommandOutput output) {
        try{
            writer.write("Enter movie ID to remove:");
            writer.newLine();
            writer.flush();

            String input = reader.readLine().trim();
            try {
                Integer id = Integer.parseInt(input);
                boolean removed = collectionManager.removeId(id);
                if (removed) {
                    writer.write("Movie with ID " + id + " has been removed.");
                } else {
                    writer.write("No movie found with ID " + id + ".");
                }
            } catch (NumberFormatException e) {
                writer.write("Invalid input. ID must be an integer.");
            }

            writer.newLine();
            writer.flush();

        }catch (IOException e){
            System.out.println(e);
        }

    }
}