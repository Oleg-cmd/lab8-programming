package helpers;

import collectionWorker.Command;
import collectionWorker.UpdateCommand;
import fileManager.CollectionManager;
import modules.CommandOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
/**
 A helper class for renaming files.
 */
public class FileNaming implements Command {
    private static CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        FileNaming.collectionManager = collectionManager;
    }
    private static final Logger logger = LogManager.getLogger(FileNaming.class);

    public static String[] FileName(String[] fileNames){
        String[] newFileNames = new String[fileNames.length];
//        System.out.println(Arrays.toString(newFileNames));
        if(fileNames.length != 0){
            try{
                for(int i=0; i < fileNames.length; i++){
                    System.out.println("Please, enter the name of file with extention: " + fileNames[i]);
                    newFileNames[i] = reader.readLine().trim();
                }

            }catch (IOException e){
                logger.error(e);
            }
        }
        return newFileNames;
    }

    /**
     * Executes the command to rename the files.
     */
    public void execute(CommandOutput output) {
    }
}
