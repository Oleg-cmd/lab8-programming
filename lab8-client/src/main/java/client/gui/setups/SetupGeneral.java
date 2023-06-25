package client.gui.setups;

import client.modules.HandleUserInput;
import client.gui.controllers.ObservableResourceFactory;
import javafx.beans.binding.StringBinding;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class SetupGeneral {
    public static Scene setupGeneral(Scene scene, ObservableResourceFactory resourceFactory) {

        Button show = (Button) scene.lookup("#show");
        StringBinding showButtonLabelBinding = resourceFactory.getStringBinding("show_button_label");
        show.textProperty().bind(showButtonLabelBinding);
        show.setOnAction(event -> {
            HandleUserInput.SendCommand("show");
        });

        Button help = (Button) scene.lookup("#help");
        StringBinding helpButtonLabelBinding = resourceFactory.getStringBinding("help_button_label");
        help.textProperty().bind(helpButtonLabelBinding);
        help.setOnAction(event -> {
            HandleUserInput.SendCommand("help");
        });

        Button info = (Button) scene.lookup("#info");
        StringBinding infoButtonLabelBinding = resourceFactory.getStringBinding("info_button_label");
        info.textProperty().bind(infoButtonLabelBinding);
        info.setOnAction(event -> {
            HandleUserInput.SendCommand("info");
        });

        Button history = (Button) scene.lookup("#history");
        StringBinding historyButtonLabelBinding = resourceFactory.getStringBinding("history_button_label");
        history.textProperty().bind(historyButtonLabelBinding);
        history.setOnAction(event -> {
            HandleUserInput.SendCommand("history");
        });

        Button quit = (Button) scene.lookup("#quit");
        StringBinding quitButtonLabelBinding = resourceFactory.getStringBinding("quit_button_label");
        quit.textProperty().bind(quitButtonLabelBinding);
        quit.setOnAction(event -> {
            HandleUserInput.SendCommand("quit");
        });

        return scene;
    }
}
