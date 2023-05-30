package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
// a variant why only 1 file or 2 files are not found (some ways)

public class CreateNew {
    private static final Logger logger = LogManager.getLogger(CreateNew.class);

    public static void Create(){
        try {
            // create empty file "history" in parent directory
            File historyFile = new File("../history");
            historyFile.createNewFile();

            // create empty file "script" in parent directory
            File scriptFile = new File("../script");
            scriptFile.createNewFile();

            // create file "collection.xml" in parent directory with specified content
            String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<root>\n" +
                    "</root>";
            File xmlFile = new File("../collection.xml");
            xmlFile.createNewFile();
            FileWriter writer = new FileWriter(xmlFile);
            writer.write(xmlContent);
            writer.close();

            logger.info("Files created successfully.");
        } catch (IOException e) {
            logger.warn("An error occurred while creating files.");
            logger.error(e);
        }
    }
}
