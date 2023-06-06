package client.helpers;

import java.io.IOException;

import helpers.CommandObject;

public class InputHandler {

    public static CommandObject createCommandObject(String commandName, String[] tokens, String XML) {
        return new CommandObject(commandName, tokens, XML);
    }

    public static CommandObject toExecute(String instruction, String XML) throws IOException {
        String[] tokens = instruction.trim().split("\\s+");
        return createCommandObject(tokens[0], tokens, XML);
    }
}
