package collectionWorker;


import fileManager.CollectionManager;
import fileManager.XmlToJava;
import modules.CommandOutput;
import modules.ServerConnection;

/**
 A command that parses an XML file and adds the movies it contains to a collection.
 */
public class ParseXmlCommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    private final String fileName;
    private final String XMLData;
    /**
     * Constructs a new instance of the {@code ParseXmlCommand} class with the specified XML to Java parser,
     * collection manager, and file name.
     *
     * @param fileName the name of the XML file to parse
     */

    public ParseXmlCommand(String fileName, String XMLData) {
        this.fileName = fileName;
        this.XMLData = XMLData;
    }

    /**
     * Parses the XML file specified by the file name and adds the movies it contains to the collection using
     * the collection manager.
     */

    @Override
    public void execute(CommandOutput output){
        collectionManager.addAllMovies(XmlToJava.parseXml(fileName, output, XMLData));
    }
}