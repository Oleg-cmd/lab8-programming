package collectionWorker;


import fileManager.CollectionManager;
import fileManager.XmlToJava;
import modules.CommandOutput;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 The Command interface represents a command that can be executed by the application.
 */
public interface Command extends Serializable {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(System.out));
    XmlToJava XML_TO_JAVA = new XmlToJava();
    Map<String, Integer> pathCountMap = new HashMap<>();

    void setCollectionManager(CollectionManager collectionManager);
    /**
     * Executes the command.
     */
    void execute(CommandOutput output);


}

