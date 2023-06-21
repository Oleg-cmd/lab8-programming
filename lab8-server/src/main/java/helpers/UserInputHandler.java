package helpers;

import collectionWorker.*;
import db.ClientSession;
import db.UserCollectionManager;
import fileManager.CollectionManager;
import modules.CommandOutput;
import modules.ServerConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import request.HandleClientInput;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A class for handling user input and executing the appropriate command. It
 * uses a HashMap to store all available commands and their corresponding
 * Command objects,
 * and it provides a start() method to continuously read and execute user input
 * until the program is terminated. It also provides a toExecute() method for
 * executing
 * a single command outside the continuous loop.
 */
public class UserInputHandler implements Command {

    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    private static String historyPath = null;
    private static String execute = null;
    private static HashMap<String, Command> commands;
    private static String localInst = "";

    private static final Logger logger = LogManager.getLogger(UserInputHandler.class);

    /**
     * Constructs a UserInputHandler with a BufferedReader, a BufferedWriter, and a
     * CollectionManager, and initializes the HashMap with all available commands.
     *
     */
    public UserInputHandler(String history, HashMap<String, Command> commands, String execute) {
        historyPath = history;
        UserInputHandler.execute = execute;
        UserInputHandler.commands = commands;
    }

    /**
     * Starts the continuous loop of reading and executing user input until the
     * program is terminated.
     */

    /**
     * Executes a single command specified by the input string.
     * 
     * @param instruction the string that specifies the command to be executed
     * @throws IOException if there is an error reading or writing to the file
     */
    public static void toExecute(String instruction) throws IOException {
        toExecute(instruction, null, null);
    }

    public static void toExecute(String instruction, String[] tokens, String xmlData) throws IOException {

        CollectionManager collectionManager = UserCollectionManager
                .getCollection(ServerConnection.getUserIdForSession(ServerConnection.clientChannel));
        logger.info("user id + " + ServerConnection.getUserIdForSession(ServerConnection.clientChannel));

        if (tokens == null) {
            tokens = instruction.trim().split("\\s+");
        }
        localInst = "";
        if (instruction == null) {
            for (String line : tokens) {
                localInst += (line + " ");
            }
        }
        logger.info(Arrays.toString(tokens));
        String commandName = tokens[0];
        logger.info("Get Command Name");
        Command command = commands.get(commandName);

        logger.info("Work with tokens");
        logger.info(" tokens lenght + " + tokens.length);

        if (tokens.length > 1) {
            logger.info("Working with tokens command");
            HashMap<String, Runnable> commands = new HashMap<>();
            String[] finalTokens = tokens;
            commands.put("add_xml", () -> {
                CommandOutput output = new CommandOutput();
                AddXmlCommand.AddingXML(finalTokens[1], output, xmlData);
                output.sendOutput(ServerConnection.clientChannel);
            });
            commands.put("update", () -> {
                CommandOutput output = new CommandOutput();
                if (finalTokens.length > 3) {
                    UpdateCommand.UpdatingArgs(localInst, output, collectionManager);
                } else {
                    output.append("not enough args");
                    logger.warn("not enough args");
                }
                output.sendOutput(ServerConnection.clientChannel);
            });
            commands.put("add_max", () -> {
                CommandOutput output = new CommandOutput();
                AddIfMaxCommand.AddMaxArg(finalTokens[1], finalTokens[2], output, xmlData, collectionManager);
                output.sendOutput(ServerConnection.clientChannel);
            });
            commands.put("filter_by_name", () -> {
                CommandOutput output = new CommandOutput();
                FilterByNameCommand.FilterByArg(finalTokens[1], output, collectionManager);
                output.sendOutput(ServerConnection.clientChannel);
            });
            commands.put("remove_by_id", () -> {
                CommandOutput output = new CommandOutput();
                logger.info("im here");
                RemoveByIdCommand.RemoveByArg(finalTokens[1], output, collectionManager);
                output.sendOutput(ServerConnection.clientChannel);
            });
            commands.put("remove_lower", () -> {
                CommandOutput output = new CommandOutput();
                RemoveLowerCommand.RemoveArg(finalTokens[1], output, collectionManager);
                output.sendOutput(ServerConnection.clientChannel);
            });
            commands.put("execute_script", () -> {
                CommandOutput output = new CommandOutput();
                new ExecuteCommand(finalTokens[1], xmlData).execute(output);
                output.sendOutput(ServerConnection.clientChannel);
            });

            String argCommandName = tokens[0];
            Runnable runnable = commands.get(argCommandName);
            CustomRunnable argCommand = new RunnableAdapter(runnable);
            logger.info("Setting up collectionManager for current User");
            argCommand.setCollectionManager(collectionManager);
            logger.info("Running command: " + argCommandName);
            argCommand.run();
        } else if (command == null) {
            logger.warn("Unknown command: " + commandName);
        } else {
            logger.info("Working with non-tokens command");
            SaveToHistoryCommand history = new SaveToHistoryCommand(commandName, historyPath);
            CommandOutput output = new CommandOutput();
            logger.info("Setting up collectionManager for current User");
            command.setCollectionManager(collectionManager);
            history.execute(output);
            command.execute(output);
            output.sendOutput(ServerConnection.clientChannel);
        }

        writer.flush();
    }

    @Override
    public void execute(CommandOutput output) {
    }
}
