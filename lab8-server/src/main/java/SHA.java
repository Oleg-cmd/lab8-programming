import db.DatabaseManager;

import java.security.NoSuchAlgorithmException;

public class SHA {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(DatabaseManager.hashPassword("password"));
    }
}
