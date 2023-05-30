package client;

import client.gui.ClientConnectionGUI;
import client.helpers.SymbolicPrintStream;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {
    public static void main(String[] args) {
        // Выбор первоначального символа и не только
        SymbolicPrintStream customOut = new SymbolicPrintStream("◖ᵔᴥᵔ◗ ~/", new String[] { "34", "35", "31" },
                System.out);
        System.setOut(customOut);
        launch(ClientConnectionGUI.class);
    }

    @Override
    public void start(Stage primaryStage) {
    }

}
