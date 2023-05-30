package collectionWorker;

import db.ClientSession;
import db.DatabaseConnection;
import db.DatabaseManager;
import fileManager.CollectionManager;
import fileManager.XmlConverter;
import modules.CommandOutput;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 A command that saves a collection of movies to an XML file.
 */
public class SaveCommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public static String name = "save";
    public static String info = name + " command:\n" +
            "   This command will save your collection to xml file\n";
    private final String fileName;

    /**
     * Creates a new SaveCommand with the specified file name and movie collection.
     *
     * @param fileName the name of the file to which the collection should be saved
     */

    public SaveCommand(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Saves the collection of movies to an XML file with the specified file name.
     */
    @Override
    public void execute(CommandOutput output){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//            XmlConverter.convertToXml(collectionManager.getMovies(), writer);

            // save to db
            ClientSession.saveObjects(collectionManager.getMovies());

            System.out.println("Movies saved to databse");
            output.append("Movies saved to databse");
        } catch (IOException e) {
            System.err.println("Error saving movies to database: " + e.getMessage());
            output.append("Error saving movies to database: " + e.getMessage());
        }
    }
}
