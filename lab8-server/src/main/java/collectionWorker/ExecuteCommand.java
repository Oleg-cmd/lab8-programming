package collectionWorker;

import fileManager.CollectionManager;
import helpers.UserInputHandler;
import modules.CommandOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 A class that represents the execute_script command, which executes all the instructions from a given file path.
 */
public class ExecuteCommand implements Command {

    public void setCollectionManager(CollectionManager collectionManager) {
    }
    UserInputHandler userInputHandler;
    public static String name = "execute_script";
    public static String info = name + " {path} command:\n" +
            "   This command will execute all instructions from script\n" +
            "   Note: script should be without errors\n";
    private final String filePath;
    private final String xmlData;

    /**
     * Constructor for ExecuteCommand class.
     *
     * @param filePath the path of the file to be read
     * @param xmlData the provided data (non-xml -> just String of available commands)
     */

    public ExecuteCommand(String filePath, String xmlData) {
        this.filePath = filePath;
        this.xmlData = xmlData;
    }


    /**
     * Reads the commands from the file and executes them.
     */

    @Override
    public void execute(CommandOutput output){
        try{
            System.out.println("Starting executing command");
            List<String> commands = readCommandsFromFile(filePath);
            System.out.println("I've read the file");
            for (String command : commands) {
                System.out.println("Im gona execute it: " + command);
                output.append(command);
                UserInputHandler.toExecute(command);
            }
        } catch (IOException e){
            System.out.println("No such file, try to make it throw provided data");
            String[] commands = xmlData.trim().split("\n");
            System.out.println(Arrays.toString(commands));
            for(String command : commands){
                System.out.println(command);
                try {
                    UserInputHandler.toExecute(command);
                }catch (IOException er){
                    System.out.println("errors  " + er);
                    output.append("some errors while pending executing of commands");
                }

            }
        }

    }

    /**
     * Reads the commands from the file.
     * @param filePath the path of the file to be read
     * @return List of commands from file
     * @throws IOException if an I/O error occurs
     */


    private List<String> readCommandsFromFile(String filePath) throws IOException {
        List<String> commands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                commands.add(line);
            }
        }
        return commands;
    }
}