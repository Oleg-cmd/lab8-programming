package collectionWorker;

import fileManager.CollectionManager;
import helpers.UserInputHandler;
import modules.CommandOutput;
import modules.ServerConnection;
import request.SendCommandList;

import java.io.IOException;

/**
 This class represents the command to exit the program.
 */

public class ExitCommand implements Command {

    public void setCollectionManager(CollectionManager collectionManager) {
    }
    public static String name = "exit";
    public static String info = name + " command:\n" +
            "   This command will exit from program (no-autosave)\n";
    private final String history;
    /**
     * Executes the command to exit the program.
     * Clears the history and terminates the JVM with status code 0.
     */



    public ExitCommand(String history) {
        this.history = history;
    }
    @Override
    public void execute(CommandOutput output) {
        ClearHistoryCommand clear = new ClearHistoryCommand(history);
        try{
            UserInputHandler.toExecute("save");
        }catch (IOException err){
            System.out.println("idk io exc while trying to save when quiting");
        }

        System.exit(0);
    }
}
