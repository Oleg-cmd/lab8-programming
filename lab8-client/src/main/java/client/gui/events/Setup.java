package client.gui.events;

import client.gui.BtnEvents.CreditsBtn;
import client.modules.HandleUserInput;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Setup {
    public static Scene setupScene;

    public static Scene setup(Scene scene) {
        scene = setupMenu(scene);
        scene = setupGeneral(scene);
        scene = setupModification(scene);
        scene = setupInit(scene);

        // send show
        setupScene = scene;
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

        Label username = (Label) scene.lookup("#username");
        username.setText(CreditsBtn.getUsername());

        otherButton.setOnAction(event -> toggleVisibility(otherX));
        initButton.setOnAction(event -> toggleVisibility(initX));
        modButton.setOnAction(event -> toggleVisibility(modX));
        genButton.setOnAction(event -> toggleVisibility(genX));

        return scene;
    }

    private static Scene setupGeneral(Scene scene) {

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

    private static Scene setupModification(Scene scene) {

        Button clear = (Button) scene.lookup("#clear");
        clear.setOnAction(event -> {
            HandleUserInput.SendCommand("clear");
        });

        // needs work with args
        // Button remove = (Button) scene.lookup("#remove");
        // clear.setOnAction(event -> {
        // HandleUserInput.SendCommand("clear");
        // });

        // remove_lower

        Button save = (Button) scene.lookup("#save");
        save.setOnAction(event -> {
            HandleUserInput.SendCommand("save");
        });

        // update

        return scene;
    }

    private static Scene setupInit(Scene scene) {
        // needs args
        // Button add = (Button) scene.lookup("#add");
        // add.setOnAction(event -> {
        // HandleUserInput.SendCommand("add");
        // });

        // Button add_max = (Button) scene.lookup("#add_max");
        // add_max.setOnAction(event -> {
        // HandleUserInput.SendCommand("add_max");
        // });

        return scene;
    }

}
