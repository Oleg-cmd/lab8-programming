package collectionWorker;

import fileManager.CollectionManager;
import helpers.MethodReturn;
import helpers.Worker;
import model.Movie;
import modules.CommandOutput;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 Represents an UpdateCommand which updates an element in a collection by its ID
 */
public class UpdateCommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    public static String name = "update";
    public static String info = name + " id field value command:\n" +
            "   This command will update an element by it's id\n";

    /**
     * Constructs an UpdateCommand object with the given parameters.
     */

    public UpdateCommand() {
    }

    public static void UpdatingArgs(String idStr, String field, String value, CommandOutput output, CollectionManager collectionManager){
        try{
            int id = Integer.parseInt(idStr);
            Movie movie = collectionManager.getById(id);
            if(movie != null){
                MethodReturn custom = Worker.Code(movie, output);
                HashMap<String, Consumer<String>> setters = custom.setters();
                if (!setters.containsKey(field)) {
                    System.out.println("Field " + field + " is not updatable");
                    output.append("Field " + field + " is not updatable");
                    writer.newLine();
                    writer.flush();
                }else{
                    if (value.isEmpty()) {
                        System.out.println(field + " cannot be empty");
                        output.append(field + " cannot be empty");
                    }
                    setters.get(field).accept(value);
                    movie = custom.movie();
                    System.out.println("Ur field was updated successfully");
                    output.append("Ur field was updated successfully");
                }
            }else {
                System.out.println("bad id provided");
                output.append("bad id provided");
            }
        }catch (IOException e){
            System.out.println("Some errors: " + e);
            output.append("Some errors while updating");
        }

    }

    public void Updating(CommandOutput output){
        try{
            System.out.print("Please enter the ID of the movie you want to update:");

            int id = Integer.parseInt(reader.readLine().trim());
            Movie movie = collectionManager.getById(id);

            if(movie != null){
                System.out.println("Enter the field you want to update:");
                MethodReturn custom = Worker.Code(movie, output);
                HashMap<String, Consumer<String>> setters = custom.setters();

                String field = reader.readLine().trim();
                if (!setters.containsKey(field)) {
                    System.out.println("Field " + field + " is not updatable");
                    writer.newLine();
                    writer.flush();
                }else{
                    System.out.println(field + ": ");
                    String value = reader.readLine().trim();
                    if (value.isEmpty()) {
                        throw new IllegalArgumentException(field + " cannot be empty");
                    }
                    setters.get(field).accept(value);
                    movie = custom.movie();
                    System.out.println("Ur field was updated successfully");
                }
            }else {
                System.out.println("bad id provided");
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }
    /**
     * Executes the update command by prompting the user to enter the ID of the movie to be updated and the field to update,
     * and then updating the specified field of the movie.
     */

    @Override
    public void execute(CommandOutput output) {
        Updating(output);
    }

}