package client.gui.setups;

import client.gui.BtnEvents.CreditsBtn;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Setup {
    public static Scene setupScene;

    public static String basedText = "    directorBirthday: LocalDate (e.g. \"1990-05-01\")\n" +
            "    oscarsCount: Integer (e.g. \"5\")\n" +
            "    goldenPalmCount: Integer (e.g. \"3\")\n" +
            "    directorName: String (e.g. \"Frank Darabont\")\n" +
            "    directorHeight: Integer (e.g. \"183\")\n" +
            "    name: String (e.g. \"The Shawshank Redemption\")\n" +
            "    coordinates: Float (e.g. \"45.123 67.890\")\n" +
            "    tagline: String (e.g. \"Fear can hold you prisoner. Hope can set you free.\")\n" +
            "    directorEyeColor: String (e.g. \"BLUE\")\n" +
            "    location: Double, String (e.g. \"45.123 67.890 MyPLace\")\n" +
            "    mpaaRating: String (e.g. \"PG_13\")\n" +
            "    id: Integer (e.g \"5\")\n";

    public static Scene setup(Scene scene) {
        scene = setupMenu(scene);
        scene = SetupGeneral.setupGeneral(scene);
        scene = SetupModification.setupModification(scene);
        scene = SetupInit.setupInit(scene);
        scene = SetupOther.setupOther(scene);

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
        username.setOnMouseClicked(event -> {
            System.out.println("user clickkkkkk");
        });

        otherButton.setOnAction(event -> toggleVisibility(otherX));
        initButton.setOnAction(event -> toggleVisibility(initX));
        modButton.setOnAction(event -> toggleVisibility(modX));
        genButton.setOnAction(event -> toggleVisibility(genX));

        return scene;
    }

}
