package helpers;

import collectionWorker.CustomRunnable;
import fileManager.CollectionManager;

public class RunnableAdapter implements CustomRunnable {
    private Runnable runnable;
    private CollectionManager collectionManager;

    public RunnableAdapter(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void run() {
        runnable.run();
    }
}