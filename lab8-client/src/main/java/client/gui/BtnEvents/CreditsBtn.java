package client.gui.BtnEvents;

import javafx.scene.Node;
import client.modules.StringAnalyzer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreditsBtn {
    public CreditsBtn() {

    }

    public void bindEvents(Scene scene) {
        System.out.println("binding...");
        // Получаем ссылку на кнопку из FXML
        Button enterBtn = (Button) scene.lookup("#enter");
        TextField login = (TextField) scene.lookup("#logField");
        PasswordField pass = (PasswordField) scene.lookup("#passField");
        // Привязываем лямбда-выражение вместо анонимного класса
        enterBtn.setOnAction((ActionEvent event) -> {
            // Логика работы при нажатии кнопки "Register"
            System.out.println("Enter button clicked");

            // Проверяем значения login и pass на null и пустую строку
            if (login != null && !login.getText().isEmpty() && pass != null && !pass.getText().isEmpty()) {
                System.out.println(login.getText().trim() + " " + pass.getText().trim());
                StringAnalyzer.setClientData(login.getText().trim() + " " + pass.getText().trim());
            } else {
                // Значения login или pass равны null или пустой строке
                // Добавляем текстовое поле с надписью "fields can't be null" под кнопкой
                Label errorField = new Label("fields can't be null");
                // Установка стилей для errorField
                errorField.setStyle(
                        "-fx-text-fill: #fff; -fx-background-color: rgb(0, 0, 0); -fx-max-width: 200px; -fx-aligment: center;");
                // Добавляем errorField в сцену
                HBox hbox = (HBox) scene.lookup("#creditsBox");
                // Получаем список дочерних элементов VBox
                ObservableList<Node> children = hbox.getChildren();
                // Проверяем наличие errorField в списке дочерних элементов
                boolean errorFieldExists = false;
                for (Node child : children) {
                    if (child instanceof Label) {
                        Label textField = (Label) child;
                        if (textField.getText().equals("fields can't be null")) {
                            errorFieldExists = true;
                            break;
                        }
                    }
                }

                // Если errorField отсутствует, добавляем его в список дочерних элементов
                if (!errorFieldExists) {
                    children.add(errorField);
                }
            }
        });

        System.out.println("end binding...");
    }

}
