package collectionWorker;


import fileManager.CollectionManager;
import modules.CommandOutput;
import modules.ServerConnection;

import java.io.IOException;


public class QuitCommand implements Command{
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(CommandOutput output) {
        try{
            ServerConnection.closeSession(ServerConnection.clientChannel);
        }catch (IOException e){
            output.append("Error while closing session");
            System.out.println("Error while closing session");
        }
    }
}
