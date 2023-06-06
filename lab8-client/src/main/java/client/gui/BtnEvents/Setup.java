package client.gui.BtnEvents;

import client.modules.HandleUserInput;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Setup {
    public static Scene setup(Scene scene) {
        scene = setupMenu(scene);

        Button show = (Button) scene.lookup("#show");
        show.setOnAction(event -> {
            HandleUserInput.SendCommand("show");
        });

        return scene;
    }

    private static void toggleVisibility(VBox vbox) {
        boolean isVisible = vbox.isVisible();
        vbox.setVisible(!isVisible);
    }

    private static Scene setupMenu(Scene scene) {
        VBox otherX = (VBox) scene.lookup("#otherX");
        VBox initX = (VBox) scene.lookup("#initX");
        VBox modX = (VBox) scene.lookup("#modX");
        VBox genX = (VBox) scene.lookup("#genX");

        Button otherButton = (Button) scene.lookup("#other");
        Button initButton = (Button) scene.lookup("#init");
        Button modButton = (Button) scene.lookup("#mod");
        Button genButton = (Button) scene.lookup("#gen");

        otherButton.setOnAction(event -> toggleVisibility(otherX));
        initButton.setOnAction(event -> toggleVisibility(initX));
        modButton.setOnAction(event -> toggleVisibility(modX));
        genButton.setOnAction(event -> toggleVisibility(genX));

        return scene;
    }
}
