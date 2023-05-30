package collectionWorker;

import db.DatabaseManager;
import fileManager.CollectionManager;
import modules.CommandOutput;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 HelpCommand is a class that implements the Command interface.
 This command will give all instructions of usage any function in this app.
 */

public class HelpCommand implements Command {

    public void setCollectionManager(CollectionManager collectionManager) {
    }

    public static String name = "help";
    public static String info = name + " command\n" +
            "   This command will give u all instructions of usage any function in this app\n";

    public static String[] myFuncsName = new String[]{AddCommand.name,AddIfMaxCommand.name, ClearCommand.name, AddXmlCommand.name, ClearHistoryCommand.name, ExecuteCommand.name, ExitCommand.name, FilterByNameCommand.name, HelpCommand.name, InfoCommand.name, PrintMpaaCommand.name, RemoveByIdCommand.name, RemoveLowerCommand.name, SaveCommand.name, ShowCommand.name, ShowHistoryCommand.name, UpdateCommand.name, "quit"};

    public static String[] instructions = new String[]{AddCommand.info, AddIfMaxCommand.info, ClearCommand.info, ClearHistoryCommand.info, ExecuteCommand.info, ExitCommand.info, FilterByNameCommand.info, info, InfoCommand.info, PrintMpaaCommand.info, RemoveByIdCommand.info, RemoveLowerCommand.info, SaveCommand.info, ShowCommand.info, ShowHistoryCommand.info, UpdateCommand.info, AddXmlCommand.info};


    /**
     * Constructs a HelpCommand with a HashMap of commands, a BufferedWriter and a BufferedReader.
     *
     */

    public HelpCommand() {
    }


    /**
     * Executes the command.
     * Displays a list of available commands.
     * If the user types 'more', additional information is displayed for each command.
     */

    @Override
    public void execute(CommandOutput output) {
        try {
            for(String s : instructions){
                    writer.write(s);
                    output.append(s);
            }

            System.out.println(DatabaseManager.hashPassword("my"));

        } catch (IOException e) {
            System.out.println(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
