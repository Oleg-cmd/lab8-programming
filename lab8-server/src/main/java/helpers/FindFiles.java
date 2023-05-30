package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
        A helper class to search for files in the project directory and its subdirectories.
 */

public class FindFiles {
    private static final Logger logger = LogManager.getLogger(FindFiles.class);

    /**
     * Searches for a file in the project directory and its parent directory.
     *
     * @param fileName the name of the file to search for
     * @return the absolute path of the file if found, otherwise null
     */
public static String findFile(String fileName) {
    String filePath;
    // Check in parent directory
    filePath = "../" + fileName;
    File file = new File(filePath);
    if (file.exists()) {
        return file.getPath();
    }

    // Check in project directory
    filePath = "./" + fileName;
    file = new File(filePath);
    if (file.exists()) {
        return file.getPath();
    }

    // Check in subdirectories of project directory
    String[] directories = new String[] { "src", "lib", "bin" }; // Modify as needed
    for (String directory : directories) {
        filePath = "./" + directory + "/" + fileName;
        file = new File(filePath);
        if (file.exists()) {
            return file.getPath();
        } else {
            File[] nestedFiles = file.listFiles();
            if (nestedFiles != null) {
                for (File nestedFile : nestedFiles) {
                    if (nestedFile.getName().equals(fileName)) {
                        return nestedFile.getPath();
                    }
                }
            }
        }
    }

    // File not found
    return null;
}
    /**
     Searches for multiple files in the project directory and its subdirectories.
     @param fileNames an array of file names to search for
     @return a map containing the file names and their absolute paths if found, otherwise null
     */
    public static Map<String, String> FindFiles(String[] fileNames){
        Map<String, String> filesMap = new LinkedHashMap<>();
        for (String fileName : fileNames) {
            String filePath = findFile(fileName);
            if (filePath != null) {
//                System.out.println("Found " + fileName + " at " + filePath);
                filesMap.put(fileName, filePath); // add to the files map
            } else {
                logger.warn("Could not find " + fileName);
                return null;
            }
        }
//        System.out.println("Files Map: " + filesMap); // print the files map
        return filesMap;
    }

}
