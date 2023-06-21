package client.gui.setups;

import client.modules.HandleUserInput;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class SetupGeneral {
    public static Scene setupGeneral(Scene scene) {

        Button show = (Button) scene.lookup("#show");
        show.setOnAction(event -> {
            HandleUserInput.SendCommand("show");
        });

        Button help = (Button) scene.lookup("#help");
        help.setOnAction(event -> {
            HandleUserInput.SendCommand("help");
        });

        Button info = (Button) scene.lookup("#info");
        info.setOnAction(event -> {
            HandleUserInput.SendCommand("info");
        });

        Button history = (Button) scene.lookup("#history");
        history.setOnAction(event -> {
            HandleUserInput.SendCommand("history");
        });

        Button quit = (Button) scene.lookup("#quit");
        quit.setOnAction(event -> {
            HandleUserInput.SendCommand("quit");
        });

        return scene;
    }
}
