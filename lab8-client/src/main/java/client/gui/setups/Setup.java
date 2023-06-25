package client.gui.setups;

import java.util.List;
import java.util.stream.Collectors;

import client.gui.BtnEvents.CreditsBtn;
import client.gui.controllers.ObservableResourceFactory;
import client.helpers.MovieManager;
import client.model.Movie;
import client.modules.HandleUserInput;
import javafx.beans.binding.StringBinding;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

    public static Scene setup(Scene scene, ObservableResourceFactory resourceFactory) {
        scene = setupMenu(scene, resourceFactory);
        scene = SetupGeneral.setupGeneral(scene, resourceFactory);
        scene = SetupModification.setupModification(scene, resourceFactory);
        scene = SetupInit.setupInit(scene, resourceFactory);
        scene = SetupOther.setupOther(scene, resourceFactory);
        scene = SetupChart.setupChart(scene);
        scene = LocalText.setupLocalText(scene, resourceFactory);
        // send show
        setupScene = scene;
        return scene;
    }

    private static void toggleVisibility(VBox vbox) {
        boolean isVisible = vbox.isVisible();
        vbox.setVisible(!isVisible);
    }

    private static Scene setupMenu(Scene scene, ObservableResourceFactory resourceFactory) {
        VBox otherX = (VBox) scene.lookup("#otherX");
        VBox initX = (VBox) scene.lookup("#initX");
        VBox modX = (VBox) scene.lookup("#modX");
        VBox genX = (VBox) scene.lookup("#genX");

        Button otherButton = (Button) scene.lookup("#other");
        StringBinding otherLabelBinding = resourceFactory.getStringBinding("other_label");
        otherButton.textProperty().bind(otherLabelBinding);

        Button initButton = (Button) scene.lookup("#init");
        StringBinding initLabelBinding = resourceFactory.getStringBinding("init_label");
        initButton.textProperty().bind(initLabelBinding);

        Button modButton = (Button) scene.lookup("#mod");
        StringBinding modLabelBinding = resourceFactory.getStringBinding("mod_label");
        modButton.textProperty().bind(modLabelBinding);

        Button genButton = (Button) scene.lookup("#gen");
        StringBinding genLabelBinding = resourceFactory.getStringBinding("gen_label");
        genButton.textProperty().bind(genLabelBinding);

        Label username = (Label) scene.lookup("#username");
        username.setText(CreditsBtn.getUsername());
        username.setOnMouseClicked(event -> {
            System.out.println("user clickkkkkk");
        });

        Button logout = (Button) scene.lookup("#logout");
        StringBinding logoutLabelBinding = resourceFactory.getStringBinding("logout_label");
        logout.textProperty().bind(logoutLabelBinding);
        logout.setOnAction(event -> HandleUserInput.SendCommand("logout"));

        VBox addF = (VBox) scene.lookup("#addF");
        TextField input = new TextField();
        input.getStyleClass().add("input");
        StringBinding inputPromptBinding = resourceFactory.getStringBinding("input_prompt");
        input.promptTextProperty().bind(inputPromptBinding);
        addF.getChildren().add(input);

        HBox buttonBox = new HBox();
        buttonBox.getStyleClass().add("button-box");
        addF.getChildren().add(buttonBox);

        Button btn = new Button();
        StringBinding filterButtonLabelBinding = resourceFactory.getStringBinding("filter_button_label");
        btn.textProperty().bind(filterButtonLabelBinding);
        btn.getStyleClass().add("btn");
        buttonBox.getChildren().add(btn);

        Button clear = new Button();
        StringBinding clearButtonLabelBinding = resourceFactory.getStringBinding("clear_button_label");
        clear.textProperty().bind(clearButtonLabelBinding);
        clear.getStyleClass().add("btn");
        buttonBox.getChildren().add(clear);

        clear.setOnAction(e -> {
            HandleUserInput.SendCommand("show");
            input.setText("");
        });

        btn.setOnAction(evt -> {
            String filterText = input.getText();
            if (filterText != null && !filterText.isEmpty()) {
                List<Movie> allMovies = MovieManager.movies;
                List<Movie> filteredMovies = allMovies.stream()
                        .filter(movie -> movie.getName().contains(filterText))
                        .collect(Collectors.toList());

                // Используйте filteredMovies, например обновите отображаемый список
                MovieManager.movies = filteredMovies;
                MovieManager.setupMovies(scene);
            }

        });

        otherButton.setOnAction(event -> toggleVisibility(otherX));
        initButton.setOnAction(event -> toggleVisibility(initX));
        modButton.setOnAction(event -> toggleVisibility(modX));
        genButton.setOnAction(event -> toggleVisibility(genX));

        return scene;
    }

}
