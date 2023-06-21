package client.gui.setups;

import client.gui.controllers.ClientConnectionGUI;
import client.model.Fields;
import client.modules.HandleUserInput;
import client.modules.StringAnalyzer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SetupInit {
    public static String myData = "";

    public static Scene setupInit(Scene scene) {
        Button add = (Button) scene.lookup("#add");
        add.setOnAction(event -> {
            ClientConnectionGUI.resetCC();
            VBox ccVbox = (VBox) scene.lookup("#ccVbox");

            VBox infoBox = (VBox) scene.lookup("#infoBox");
            TextArea info = (TextArea) infoBox.lookup("#infoArea");

            String[] tokens = Setup.basedText.split("\n");
            if (tokens != null && tokens.length > 1) {
                int numRows = (int) Math.ceil(tokens.length / 2.0);
                GridPane gridPane = new GridPane();
                ccVbox.getChildren().add(gridPane);

                for (int i = 0; i < tokens.length; i++) {
                    if (!tokens[i].contains("id")) {
                        TextField input = new TextField();
                        input.getStyleClass().add("input");
                        input.setPromptText(tokens[i]);
                        input.setPrefWidth(scene.getWidth() * 0.5);
                        GridPane.setConstraints(input, i % 2, i / 2);
                        gridPane.getChildren().add(input);
                    }
                }

                Button btn = new Button("Submit");
                btn.getStyleClass().add("btn");
                GridPane.setConstraints(btn, 0, numRows);
                GridPane.setColumnSpan(btn, 2);
                gridPane.getChildren().add(btn);

                info.setText(Setup.basedText);

                btn.setOnAction(evt -> {
                    boolean hasEmptyFields = false;
                    GridPane gridPaneLocal = null;

                    for (Node node : ccVbox.getChildren()) {
                        if (node instanceof GridPane) {
                            gridPaneLocal = (GridPane) node;
                            break;
                        }
                    }

                    if (gridPaneLocal != null) {
                        for (Node node : gridPaneLocal.getChildren()) {
                            if (node instanceof TextField) {
                                TextField textField = (TextField) node;
                                if (textField.getText() == null || textField.getText().isEmpty()) {
                                    hasEmptyFields = true;
                                    break;
                                } else {
                                    myData += textField.getText().trim();
                                    myData += "\n";
                                }
                            }
                        }
                    }

                    if (hasEmptyFields) {
                        info.setText("Please fill in all fields." + "\n\n\n" + Setup.basedText);
                        myData = "";
                    } else {
                        HandleUserInput.SendCommand("add");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        StringAnalyzer.setClientData(myData);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        HandleUserInput.SendCommand("show");
                        info.setText("Done/Success");
                    }
                });
            } else {
                info.setText("I'm sorry, but you can't perform this operation due to no available fields to pass.");
            }
        });

        return scene;
    }

}
