package collectionWorker;

import fileManager.CollectionManager;
import helpers.MethodReturn;
import helpers.Worker;
import model.Movie;
import modules.CommandOutput;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;

import javax.print.attribute.standard.MediaSize.Other;

/**
 * Represents an UpdateCommand which updates an element in a collection by its
 * ID
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

    public static void UpdatingArgs(String tokens, CommandOutput output,
            CollectionManager collectionManager) {
        try {
            System.out.println("Updating by args");
            System.out.println("Args total: " + tokens);
            String[] localTokens = tokens.split("\\s+");
            System.out.println("Tokens length: " + localTokens.length);
            for (int i = 0; i < localTokens.length; i++) {
                System.out.println("Token " + i + ": " + localTokens[i]);
            }
            System.out.println("parse local id");
            int id = Integer.parseInt(localTokens[1]);
            Movie movie = collectionManager.getById(id);
            System.out.println("movie was setupped by id");
            if (movie != null) {
                // System.out.println(movie);
                MethodReturn custom = Worker.Code(movie, output);
                HashMap<String, Consumer<String>> setters = custom.setters();

                System.out.println("worker was setupped");

                if (!setters.containsKey(localTokens[2])) {
                    System.out.println("Field " + localTokens[2] + " is not updatable");
                    output.append("Field " + localTokens[2] + " is not updatable");
                    writer.newLine();
                    writer.flush();
                } else {
                    if (localTokens[3].isEmpty()) {
                        System.out.println(localTokens[3] + " cannot be empty");
                        output.append(localTokens[3] + " cannot be empty");
                    }
                    System.out.println("creating value");
                    String value = "";
                    for (int i = 3; i < localTokens.length; i++) {
                        value += (localTokens[i] + " ");
                    }
                    System.out.println(value);
                    output.append(value);
                    setters.get(localTokens[2]).accept(value.trim());
                    if (collectionManager.checkFieldsByNull(custom.movie())) {
                        movie = custom.movie();
                        System.out.println("Ur field was updated successfully");
                        output.append("Ur field was updated successfully");
                    } else {
                        output.append(
                                "Something get wrong with movie, some fields start to be null, we will not add it to collection, try again later");
                        System.out.println(
                                "Something get wrong with movie, some fields start to be null, we will not add it to collection, try again later");
                    }
                }
            } else {
                System.out.println("bad id provided");
                output.append("bad id provided");
            }
        } catch (IOException e) {
            System.out.println("Some errors: " + e);
            output.append("Some errors while updating");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array out of bounds: " + e);
            output.append("Some errors while updating: tokens length is not enough");
        }

    }

    public void Updating(CommandOutput output) {
        try {
            System.out.print("Please enter the ID of the movie you want to update:");

            int id = Integer.parseInt(reader.readLine().trim());
            Movie movie = collectionManager.getById(id);

            if (movie != null) {
                System.out.println("Enter the field you want to update:");
                MethodReturn custom = Worker.Code(movie, output);
                HashMap<String, Consumer<String>> setters = custom.setters();

                String field = reader.readLine().trim();
                if (!setters.containsKey(field)) {
                    System.out.println("Field " + field + " is not updatable");
                    writer.newLine();
                    writer.flush();
                } else {
                    System.out.println(field + ": ");
                    String value = reader.readLine().trim();
                    if (value.isEmpty()) {
                        throw new IllegalArgumentException(field + " cannot be empty");
                    }
                    setters.get(field).accept(value);
                    if (collectionManager.checkFieldsByNull(custom.movie())) {
                        movie = custom.movie();
                        System.out.println("Ur field was updated successfully");
                        output.append("Ur field was updated successfully");
                    } else {
                        output.append(
                                "Something get wrong with movie, some fields start to be null, we will not add it to collection, try again later");
                        System.out.println(
                                "Something get wrong with movie, some fields start to be null, we will not add it to collection, try again later");
                    }

                }
            } else {
                System.out.println("bad id provided");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Executes the update command by prompting the user to enter the ID of the
     * movie to be updated and the field to update,
     * and then updating the specified field of the movie.
     */

    @Override
    public void execute(CommandOutput output) {
        Updating(output);
    }

}