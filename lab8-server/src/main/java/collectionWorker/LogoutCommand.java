package collectionWorker;

import db.ClientSession;
import fileManager.CollectionManager;
import modules.CommandOutput;
import modules.ServerConnection;

public class LogoutCommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(CommandOutput output) {
        ClientSession session = ServerConnection.getSessionByChannel(ServerConnection.clientChannel);
        if (session != null) {
            session.handleLogout();
        }
    }

}
