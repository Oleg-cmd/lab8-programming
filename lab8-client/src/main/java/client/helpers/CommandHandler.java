package client.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import client.gui.ClientConnectionGUI;
import helpers.CommandObject;

public class CommandHandler {
    private static String commandList;

    public CommandHandler(String commandList) {
        CommandHandler.commandList = commandList;
    }

    public static CommandObject handleCommand(String command) throws IOException {
        // Split the command into its components
        System.out.println(command);
        String[] parts = command.trim().split("\\s+");
        // System.out.println(Arrays.toString(parts));
        // Check if the command is valid and has the correct syntax
        if (parts.length == 0 || parts.length >= 5 || !isValidCommand(parts[0])) {
            System.out.println("Invalid command syntax by parts");
            return null;
        }
        if (hasTokens(parts)) {
            if (parts[0].equals("add_max") || parts[0].equals("add_xml") || parts[0].equals("execute_script")) {
                String xmlPath = parts[parts.length - 1];
                String xmlData = Files.readString(Paths.get(xmlPath));
                System.out.println(xmlData);
                return new CommandObject(command, parts, xmlData);
            }

            return InputHandler.toExecute(command, null);
        } else {
            System.out.println("U cant use it without args");
        }
        return null;
    }

    private static boolean hasTokens(String[] tokens) {
        String[] argCommands = new String[] { "add_max", "add_xml", "execute_script", "filter_by_name", "remove_by_id",
                "remove_lower", "update" };
        boolean arguing = false;
        for (String command : argCommands) {
            if (tokens[0].equals(command)) {
                arguing = true;
                break;
            }
        }
        if (!arguing) {
            return true;
        } else {
            if (tokens[0].equals("add_max") && tokens.length == 3) {
                return true;
            }
            if ((tokens[0].equals("add_xml") || tokens[0].equals("execute_script") || tokens[0].equals("filter_by_name")
                    || tokens[0].equals("remove_by_id") || tokens[0].equals("remove_lower")) && tokens.length == 2) {
                return true;
            }
            if (tokens[0].equals("update") && tokens.length == 4) {
                return true;
            }
            return false;
        }
    }

    private static boolean isValidCommand(String command) {
        // Check if the command is one of the valid commands on the server
        // You can implement this however you like, for example using a set of allowed
        // commands
        if (commandList != null) {
            String[] list = commandList.trim().split(", ");
            for (String s : list) {
                if (s.trim().equals(command)) {
                    return true;
                }
            }

            if (command.trim().equals("quit")) {
                System.out.println("Closing app...");
                ClientConnectionGUI.CloseUp();
                System.out.println("Application closed");
                return true;
            }
        } else {
            System.out.println("CommandList is null");
        }

        System.out.println("command is not valid at all");
        return false;
    }
}
