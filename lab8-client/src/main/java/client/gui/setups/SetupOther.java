package client.gui.setups;

import client.modules.HandleUserInput;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class SetupOther {
    public static Scene setupOther(Scene scene) {

        Button clearHistory = (Button) scene.lookup("#clear_history");
        clearHistory.setOnAction(event -> {
            HandleUserInput.SendCommand("clear_history");
        });
        return scene;
    }
}
