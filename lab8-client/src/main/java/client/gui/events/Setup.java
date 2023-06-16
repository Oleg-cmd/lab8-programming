package client.gui.events;

import java.io.File;

import client.gui.ClientConnectionGUI;
import client.gui.BtnEvents.CreditsBtn;
import client.model.Fields;
import client.model.MpaaRating;
import client.modules.HandleUserInput;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Setup {
    public static Scene setupScene;

    public static String basedText = "\n" +
            "    directorBirthday: LocalDate (e.g. \"1990-05-01\")\n" +
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
        username.setOnMouseClicked(event -> {
            System.out.println("user clickkkkkk");
        });

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

        Button remove = (Button) scene.lookup("#remove");
        remove.setOnAction(event -> {

            ClientConnectionGUI.resetCC();

            VBox ccVbox = (VBox) scene.lookup("#ccVbox");
            System.out.println(ccVbox);
            TextField input = new TextField();
            input.getStyleClass().add("input");
            input.setPrefWidth(ccVbox.getWidth() * 0.5); // Set preferred width as a percentage of the parent's
                                                         // width
            input.setPromptText("ID"); // Set the placeholder text

            Button btn = new Button("Submit");
            btn.getStyleClass().add("btn");

            // Configure VBox alignment and fillHeight
            ccVbox.setAlignment(Pos.TOP_CENTER); // Align contents to the top center
            VBox.setVgrow(ccVbox, Priority.ALWAYS); // Allow VBox to fill available vertical space

            // Add input and button to the VBox
            ccVbox.getChildren().addAll(input, btn);

            VBox infoBox = (VBox) scene.lookup("#infoBox");
            // System.out.println(infoBox);
            TextArea info = (TextArea) infoBox.lookup("#infoArea");

            StringBuilder idsOnly = new StringBuilder();
            String[] lines = basedText.split("\n");
            for (String line : lines) {
                if (line.toLowerCase().contains("id")) {
                    idsOnly.append(line.trim() + "\n");
                }
            }

            info.setText(idsOnly.toString());

            // ваш код
            btn.setOnAction(evt -> {
                String inputText = input.getText();

                if (inputText == null || inputText.isEmpty()) {
                    info.setText("Input field is empty. Please enter an ID.");
                } else {
                    try {
                        Integer.parseInt(inputText); // if it cannot be converted to an Integer, it will throw an
                                                     // exception
                        // insert here what you want to do if the input is a valid integer
                        info.setText("Your id is ok. Sending request");
                        HandleUserInput.SendCommand("remove_by_id" + " " + inputText);
                        HandleUserInput.SendCommand("show");
                    } catch (NumberFormatException e) {
                        info.setText("Input is not a valid integer. Please enter a valid ID.");
                    }
                }
            });
            // ваш код
            System.out.println(ccVbox.getChildren());

        });

        // remove_lower
        Button removeLower = (Button) scene.lookup("#remove_l");
        removeLower.setOnAction(event -> {
            ClientConnectionGUI.resetCC();

            VBox ccVbox = (VBox) scene.lookup("#ccVbox");

            ComboBox<MpaaRating> mpaaRatingComboBox = new ComboBox<>();
            mpaaRatingComboBox.getItems().setAll(MpaaRating.values());
            mpaaRatingComboBox.setPromptText("Choose a rating");
            mpaaRatingComboBox.setStyle(
                    "-fx-text-fill: #ffffff; -fx-background-color: rgb(0,0,0);-fx-prompt-text-fill: #ffffff;"); // Set
            // custom
            // color style
            mpaaRatingComboBox.setPrefWidth(ccVbox.getWidth()); // Set preferred width as the parent's width

            Button btn = new Button("Submit");
            btn.getStyleClass().add("btn");

            // Configure VBox alignment and fillHeight
            ccVbox.setAlignment(Pos.TOP_CENTER); // Align contents to the top center
            VBox.setVgrow(ccVbox, Priority.ALWAYS); // Allow VBox to fill available vertical space

            // Add input, dropdown menu and button to the VBox
            ccVbox.getChildren().addAll(mpaaRatingComboBox, btn);

            VBox infoBox = (VBox) scene.lookup("#infoBox");
            TextArea info = (TextArea) infoBox.lookup("#infoArea");

            btn.setOnAction(evt -> {

                MpaaRating selectedRating = mpaaRatingComboBox.getValue();

                if (selectedRating == null) {
                    info.setText("No rating is selected. Please choose a rating.");
                } else {
                    try {

                        info.setText("Your id is ok. Sending request");
                        HandleUserInput.SendCommand("remove_lower" + " " + selectedRating.name());
                        HandleUserInput.SendCommand("show");
                    } catch (NumberFormatException e) {
                        info.setText("Input is not a valid integer. Please enter a valid ID.");
                    }
                }
            });

            System.out.println(ccVbox.getChildren());
        });

        Button save = (Button) scene.lookup("#save");
        save.setOnAction(event -> {
            HandleUserInput.SendCommand("save");
        });

        Button update = (Button) scene.lookup("#update");
        update.setOnAction(event -> {
            ClientConnectionGUI.resetCC();

            VBox ccVbox = (VBox) scene.lookup("#ccVbox");

            TextField input = new TextField();
            input.getStyleClass().add("input");
            input.setPrefWidth(ccVbox.getWidth() * 0.5); // Set preferred width as a percentage of the parent's
                                                         // width
            input.setPromptText("ID"); // Set the placeholder text

            ComboBox<Fields> mpaaRatingComboBox = new ComboBox<>();
            mpaaRatingComboBox.getItems().setAll(Fields.values());
            mpaaRatingComboBox.setPromptText("Choose a field");
            mpaaRatingComboBox.setStyle(
                    "-fx-text-fill: #ffffff; -fx-background-color: rgb(0,0,0);-fx-prompt-text-fill: #ffffff;"); // Set
            // custom
            // color style
            mpaaRatingComboBox.setPrefWidth(ccVbox.getWidth()); // Set preferred width as the parent's width

            TextField value = new TextField();
            value.getStyleClass().add("input");
            value.setPrefWidth(ccVbox.getWidth() * 0.5); // Set preferred width as a percentage of the parent's
                                                         // width
            value.setPromptText("value"); // Set the placeholder text

            Button btn = new Button("Submit");
            btn.getStyleClass().add("btn");

            // Configure VBox alignment and fillHeight
            ccVbox.setAlignment(Pos.TOP_CENTER); // Align contents to the top center
            VBox.setVgrow(ccVbox, Priority.ALWAYS); // Allow VBox to fill available vertical space

            // Add input, dropdown menu and button to the VBox
            ccVbox.getChildren().addAll(input, mpaaRatingComboBox, value, btn);

            VBox infoBox = (VBox) scene.lookup("#infoBox");
            TextArea info = (TextArea) infoBox.lookup("#infoArea");

            StringBuilder idsOnly = new StringBuilder();
            String[] lines = basedText.split("\n");
            for (String line : lines) {
                if (line.toLowerCase().contains("id")) {
                    idsOnly.append(line.trim() + "\n");
                }
            }

            info.setText(idsOnly.toString());

            btn.setOnAction(evt -> {
                String inputText = input.getText().trim();
                String valueText = value.getText().trim();

                if (inputText == null || inputText.isEmpty()) {
                    info.setText("Input field is empty. Please enter an ID.");
                } else {
                    try {
                        Fields selectedRating = mpaaRatingComboBox.getValue();
                        Integer.parseInt(inputText); // if it cannot be converted to an Integer, it will throw an
                                                     // exception
                        // insert here what you want to do if the input is a valid integer
                        info.setText("Your id is ok. Sending request");
                        HandleUserInput.SendCommand(
                                "update" + " " + inputText + " " + selectedRating.name() + " " + valueText);
                        HandleUserInput.SendCommand("show");
                    } catch (NumberFormatException e) {
                        info.setText("Input is not a valid integer. Please enter a valid ID.");
                    }
                }
            });

            System.out.println(ccVbox.getChildren());
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
