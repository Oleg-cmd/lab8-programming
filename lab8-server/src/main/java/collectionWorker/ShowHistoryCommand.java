package collectionWorker;

import fileManager.CollectionManager;
import modules.CommandOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 This class represents a command to show the last 10 commands that were entered to console. It implements the Command
 interface.
 */
public class ShowHistoryCommand implements Command {

    public void setCollectionManager(CollectionManager collectionManager) {
    }
    public static String name = "history";
    public static String info = name + " command:\n" +
            "   This command will print to console 10 last commands that u entered to console\n" +
            "   note: commands will be without args, history of commands is same for all\n";
    private static final int NUM_LINES_TO_SHOW = 10;
    private final String historyFilePath;

    /**
     * Creates a new ShowHistoryCommand with the given history file path.
     * @param historyFilePath the path to the history file
     */
    public ShowHistoryCommand(String historyFilePath) {
        this.historyFilePath = historyFilePath;
    }

    /**
     * Executes the show history command by reading the last 10 lines from the history file and printing them to the console.
     */

    @Override
    public void execute(CommandOutput output) {
        // Open the history file
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFilePath))) {
            // Read the last NUM_LINES_TO_SHOW lines from the file
            Deque<String> lastLines = new ArrayDeque<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lastLines.addLast(line);
                if (lastLines.size() > NUM_LINES_TO_SHOW) {
                    lastLines.removeFirst();
                }
            }

            // Display the last lines of the history
            System.out.println("Last " + NUM_LINES_TO_SHOW + " commands:");
            output.append("Last " + NUM_LINES_TO_SHOW + " commands:");
            for (String cmd : lastLines) {
                System.out.println(cmd);
                output.append(cmd + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading history file: " + e.getMessage());
        }
    }
}