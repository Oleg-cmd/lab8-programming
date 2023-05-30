package collectionWorker;

import db.DatabaseConnection;
import fileManager.CollectionManager;
import modules.CommandOutput;
import modules.CommandOutputDecorator;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 The InfoCommand class implements the Command interface and represents the "info" command that prints
 information about the collection, such as the type, size, and path of the collection file, and the number
 of models in the collection.
 */
public class InfoCommand implements Command {
    private CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public static String name = "info";
    public static String info = name + " command:\n" +
            "   This command will print all info about collection (type, size, etc)\n";


    /**
     * Creates a new instance of the InfoCommand class with the specified HashSet of models.
     */
    public InfoCommand() {
    }


    /**
     * Executes the "info" command by printing information about the collection, such as the type, size, and path of the
     * collection file, and the number of models in the collection.
     */

    @Override
    public void execute(CommandOutput output){
        String s = getInfo();
        InfoDecorator decorator = new InfoDecorator(output);
        decorator.append(s);
        try{
            writer.write(s);
        }catch (IOException er){
            System.out.println("io exc cathed in info");
        }
    }

    public String getInfo() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Получение количества записей в таблице movies
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM movies");
            resultSet.next();
            int movieCount = resultSet.getInt(1);

            ResultSet usersSet = statement.executeQuery("SELECT COUNT(*) FROM users");
            usersSet.next();
            int usersCount = usersSet.getInt(1);

            return " Type of myCollection: Database \n " +
                    " Type of models in myCollection: Movie \n " +
                    " Number of models in all collection: " + movieCount + "\n" +
                    " Count of users in Database (for application only): " + usersCount + "\n";

        } catch (SQLException e) {
           System.out.println("Unable to retrieve collection information from the database: " + e.getMessage());
            return null; // или верните сообщение об ошибке, в зависимости от вашего случая
        }
    }


    public static class InfoDecorator extends CommandOutputDecorator {
        public InfoDecorator(CommandOutput output) {
            super(output);
        }

        @Override
        public void append(String s) {
            String decoratedOutput = "=== Collection Info ===\n" + s + "======================\n";
            super.output.append(decoratedOutput);
        }
    }


}


