package client.gui.setups;

import java.io.File;

import client.gui.controllers.ObservableResourceFactory;
import javafx.beans.binding.StringBinding;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

public class LocalText {
    public static Scene setupLocalText(Scene scene, ObservableResourceFactory resourceFactory) {
        // addictional (text - Addictional Filters)
        Button addictional = (Button) scene.lookup("#addictional");
        StringBinding addictionalLabelBinding = resourceFactory.getStringBinding("addictional_button_label");
        addictional.textProperty().bind(addictionalLabelBinding);

        // ccButton (text - Control Centre)
        Button ccButton = (Button) scene.lookup("#ccButton");
        StringBinding ccButtonLabelBinding = resourceFactory.getStringBinding("ccButton_button_label");
        ccButton.textProperty().bind(ccButtonLabelBinding);

        // models (text - Models)
        Label models = (Label) scene.lookup("#models");
        StringBinding modelsLabelBinding = resourceFactory.getStringBinding("models_button_label");
        models.textProperty().bind(modelsLabelBinding);

        Label FilterByName = (Label) scene.lookup("#FilterByName");
        StringBinding filterByNameLabelBinding = resourceFactory.getStringBinding("filter_by_name_label");
        FilterByName.textProperty().bind(filterByNameLabelBinding);

        // infoLocal (text - Info)
        Button infoLocal = (Button) scene.lookup("#infoLocal");
        StringBinding infoLocalLabelBinding = resourceFactory.getStringBinding("infoLocal_button_label");
        infoLocal.textProperty().bind(infoLocalLabelBinding);

        // visual (text - Visualization)
        Button visual = (Button) scene.lookup("#visual");
        StringBinding visualLabelBinding = resourceFactory.getStringBinding("visual_button_label");
        visual.textProperty().bind(visualLabelBinding);

        return scene;
    }

}
