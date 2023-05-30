package modules;

import collectionWorker.*;
import helpers.MainHelper;
import helpers.UserInputHandler;

import java.util.HashMap;

public class CollectionInit {
    public static void Init(){
        HashMap<String, Command> commands = new HashMap<>();

        String[] path = new MainHelper().MainHelper();

        String history = path[0];
        String execute = path[1];
        String collection = path[2];

        commands.put("show", new ShowCommand());
        commands.put("clear", new ClearCommand());
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand(history));
        commands.put("info", new InfoCommand());
        commands.put("print_mpaa", new PrintMpaaCommand());
        commands.put("history", new ShowHistoryCommand(history));
        commands.put("clear_history", new ClearHistoryCommand(history));
        commands.put("add", new AddCommand());
        commands.put("save", new SaveCommand(collection));
        commands.put("quit", new QuitCommand());

        UserInputHandler inputHandler = new UserInputHandler(history, commands, execute);
    }
}
