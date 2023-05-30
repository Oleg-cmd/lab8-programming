package helpers;

import collectionWorker.ParseXmlCommand;
import modules.CommandOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.Map;

/**
 * A helper class for the main application.
 */
public class MainHelper {
    /**
     * Main method for the MainHelper class.
     * @return An array of Strings representing the file paths for the history, execute, and collection files.
     */
    private static final Logger logger = LogManager.getLogger(MainHelper.class);

    public String[] MainHelper(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String history = null;
        String execute = null;
        String collection = null;

        try{
            while (true){
                writer.write("If u want to use CUSTOM name of files (default: history, script, collection.xml) type y, else type n: ");
                writer.flush();
                String[] fileNames = new String[] { "history", "script", "collection.xml" }; // Modify as needed
                String[] customNames;
                boolean breakPoint = false;

                String fistInput = reader.readLine();
                if(fistInput.equals("n") || fistInput.equals("т") || fistInput.equals("no")){
                    Map<String, String> filesPath = FindFiles.FindFiles(fileNames);
                    if(filesPath == null){
                        logger.error("Wrong data");
                        System.out.println("Do u want to create new files? (y or n)");
                        if(reader.readLine().equals("y")){
                            CreateNew.Create();
                        }
                    }else {
                        history = filesPath.get(fileNames[0]);
                        execute = filesPath.get(fileNames[1]);
                        collection = filesPath.get(fileNames[2]);
                        break;
                    }
                }else{
                    customNames = FileNaming.FileName(fileNames);
                    System.out.println(Arrays.toString(customNames));
                    for(String name : customNames){
                        if (name.equals("") || name.equals(" ")) {
                            breakPoint = true;
                            break;
                        }
                    }
                    if(breakPoint){
                        logger.warn("Wrong data");
                        System.out.println("Do u want to create new files?");
                        if(reader.readLine().equals("y")){
                            CreateNew.Create();
                        }
                    }else{
                        Map<String, String> filesPath = FindFiles.FindFiles(customNames);
                        if(filesPath == null){
                            logger.warn("Wrong data");
                            System.out.println("Do u want to create new files?");
                            if(reader.readLine().equals("y")){
                                CreateNew.Create();
                            }
                        }else {
                            history = filesPath.get(fileNames[0]);
                            execute = filesPath.get(fileNames[1]);
                            collection = filesPath.get(fileNames[2]);
                            break;
                        }
                    }
                }
            }
            CommandOutput output = new CommandOutput();
            logger.info("Collection loaded from database successfuly");
            writer.flush();

        }catch (IOException e){
            logger.error(e);
        }
        return new String[]{history, execute, collection};
    }
}
