package collectionWorker;


import fileManager.CollectionManager;
import helpers.CommandObject;
import modules.CommandOutput;

public class AddXmlCommand implements Command {

    public void setCollectionManager(CollectionManager collectionManager) {}
    public static String name = "add_xml";

    public static String info = name + " {args} command:\n" +
            "   This command will add new Movie from XML file (or many movies) and add it to collection\n";

    public static void AddingXML(String XMLPath, CommandOutput output, String XMLData){
//      just adding to collection some models, no checking of fields
        ParseXmlCommand parseXmlCommand = new ParseXmlCommand(XMLPath, XMLData);
        parseXmlCommand.execute(output);
    }

    @Override
    public void execute(CommandOutput output) {
    }
}
