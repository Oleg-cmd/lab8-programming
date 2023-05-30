package collectionWorker;

import fileManager.CollectionManager;

public interface CustomRunnable extends Runnable {
    void setCollectionManager(CollectionManager collectionManager);
}
