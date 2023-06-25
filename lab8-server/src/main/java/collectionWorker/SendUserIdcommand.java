package collectionWorker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import db.ClientSession;
import db.DatabaseManager;
import fileManager.CollectionManager;
import model.Movie;
import modules.CommandOutput;
import modules.ServerConnection;

public class SendUserIdcommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(CommandOutput output) {
        int UserId = ServerConnection.getUserIdForSession(ServerConnection.clientChannel);
        List<Integer> ids = DatabaseManager.getAllUserIds();

        String secured = "UserId provided only once, be careful with using it \n";
        output.append(secured + UserId + "\n" + ids);
    }
}
