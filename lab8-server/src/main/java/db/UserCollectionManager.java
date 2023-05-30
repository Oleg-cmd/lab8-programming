package db;

import fileManager.CollectionManager;

import java.util.HashMap;
import java.util.Map;

public class UserCollectionManager {
    private static Map<Integer, CollectionManager> collectionsMap;


    public UserCollectionManager() {
        collectionsMap = new HashMap<>();
    }

    public static void addCollection(int userId, CollectionManager collection) {
        collectionsMap.put(userId, collection);
    }

    public static CollectionManager getCollection(int userId) {
        return collectionsMap.get(userId);
    }

    public static void deleteCollectionManager(int id) {
        collectionsMap.remove(id);
    }

    // Другие методы, которые могут потребоваться для работы с коллекциями пользователей
}
