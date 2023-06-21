package client.gui.setups;

import client.gui.controllers.ClientConnectionGUI;
import client.model.Fields;
import client.model.MpaaRating;
import client.modules.HandleUserInput;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SetupModification {
    public static Scene setupModification(Scene scene) {

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
            String[] lines = Setup.basedText.split("\n");
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
                        Thread.sleep(1000);
                        HandleUserInput.SendCommand("show");
                    } catch (NumberFormatException e) {
                        info.setText("Input is not a valid integer. Please enter a valid ID.");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
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
                        Thread.sleep(1000);
                        HandleUserInput.SendCommand("show");
                    } catch (NumberFormatException e) {
                        info.setText("Input is not a valid integer. Please enter a valid ID.");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
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

            mpaaRatingComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                Fields selectedRating = newValue;
                if (selectedRating != null) {
                    String[] lines = Setup.basedText.split("\n");
                    StringBuilder idsOnly = new StringBuilder();
                    for (String line : lines) {
                        if (line.contains(selectedRating.name())) {
                            idsOnly.append(line.trim() + "\n");
                        }
                    }
                    info.setText(idsOnly.toString());
                } else {
                    System.out.println("selectedRating is null");
                }
            });

            btn.setOnAction(evt -> {
                String inputText = input.getText().trim();
                String valueText = value.getText().trim();
                Fields selectedRating = mpaaRatingComboBox.getValue();
                if (inputText == null || inputText.isEmpty()) {
                    info.setText("Input field is empty. Please enter an ID.");
                } else {
                    try {
                        Integer.parseInt(inputText); // if it cannot be converted to an Integer, it will throw an
                                                     // exception
                        // insert here what you want to do if the input is a valid integer
                        info.setText("Your id is ok. Sending request");
                        HandleUserInput.SendCommand(
                                "update" + " " + inputText + " " + selectedRating.name() + " " + valueText);

                        Thread.sleep(1000);
                        HandleUserInput.SendCommand("show");
                    } catch (NumberFormatException e) {
                        info.setText("Input is not a valid integer. Please enter a valid ID.");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });

            System.out.println(ccVbox.getChildren());
        });

        return scene;
    }

}
