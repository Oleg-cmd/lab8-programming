package client.gui.setups;

import client.gui.controllers.ObservableResourceFactory;
import client.modules.HandleUserInput;
import javafx.beans.binding.StringBinding;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class SetupOther {
    public static Scene setupOther(Scene scene, ObservableResourceFactory resourceFactory) {

        Button clearHistory = (Button) scene.lookup("#clear_history");
        StringBinding clearHistoryBtnLabel = resourceFactory.getStringBinding("clear_history_button_label");
        clearHistory.textProperty().bind(clearHistoryBtnLabel);
        clearHistory.setOnAction(event -> {
            HandleUserInput.SendCommand("clear_history");
        });
        return scene;
    }
}
