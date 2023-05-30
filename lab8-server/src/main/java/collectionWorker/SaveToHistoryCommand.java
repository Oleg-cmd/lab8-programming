package collectionWorker;

import fileManager.CollectionManager;
import modules.CommandOutput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 This class represents a command to save a command to a history file.
 The command is written to the history file as a single line of text.
 */
public class SaveToHistoryCommand implements Command {

    public void setCollectionManager(CollectionManager collectionManager) {
    }
    private final String command;
    private final String historyFilePath;

    /**
     * Constructs a new SaveToHistoryCommand object with the given command string and history file path.
     *
     * @param command The command to be saved to the history file.
     * @param historyFilePath The path to the history file.
     */

    public SaveToHistoryCommand(String command, String historyFilePath) {
        this.command = command;
        this.historyFilePath = historyFilePath;
    }

    /**
     * Executes the command to save the command to the history file.
     */

    @Override
    public void execute(CommandOutput output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFilePath, true))) {
            writer.write(command + "\n");
//            System.out.println("Command history saved to " + historyFilePath);
        } catch (IOException e) {
            System.err.println("Failed to save command history: " + e.getMessage());
        }
    }
}