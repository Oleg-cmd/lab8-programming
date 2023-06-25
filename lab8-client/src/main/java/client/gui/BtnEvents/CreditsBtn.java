package client.gui.BtnEvents;

import javafx.scene.Node;
import client.gui.controllers.ObservableResourceFactory;
import client.modules.StringAnalyzer;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreditsBtn {
    private static String username;
    private static String password;
    private final ObservableResourceFactory resourceFactory;

    public CreditsBtn(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
    }

    public static String getUsername() {
        return username.trim();
    }

    public void bindEvents(Scene scene) {
        System.out.println("binding...");
        // Получаем ссылку на кнопку из FXML
        Button enterBtn = (Button) scene.lookup("#enter");
        TextField login = (TextField) scene.lookup("#logField");
        PasswordField pass = (PasswordField) scene.lookup("#passField");

        StringBinding enterLabelBinding = resourceFactory.getStringBinding("enter_label");
        enterBtn.textProperty().bind(enterLabelBinding);

        Label enterWhat = (Label) scene.lookup("#enterWhat");
        StringBinding enterWhatLabelBinding = resourceFactory.getStringBinding("enter_what_label");
        enterWhat.textProperty().bind(enterWhatLabelBinding);

        // Привязываем лямбда-выражение вместо анонимного класса
        enterBtn.setOnAction((ActionEvent event) -> {
            // Логика работы при нажатии кнопки "Register"
            System.out.println("Enter button clicked");

            // Проверяем значения login и pass на null и пустую строку
            if (login != null && !login.getText().isEmpty() && pass != null && !pass.getText().isEmpty()) {

                username = login.getText();
                password = pass.getText();

                StringAnalyzer.setClientData(username.trim() + " " + password.trim());
            } else {
                // Значения login или pass равны null или пустой строке
                // Добавляем текстовое поле с надписью "fields can't be null" под кнопкой
                StringBinding errorLabelBinding = resourceFactory.getStringBinding("error_field_label");
                Label errorField = new Label();
                errorField.textProperty().bind(errorLabelBinding);

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
        // Локализация placeholder для поля "Login"
        StringBinding loginPlaceholderBinding = resourceFactory.getStringBinding("login_placeholder");
        login.promptTextProperty().bind(loginPlaceholderBinding);

        // Локализация placeholder для поля "Password"
        StringBinding passPlaceholderBinding = resourceFactory.getStringBinding("password_placeholder");
        pass.promptTextProperty().bind(passPlaceholderBinding);

        System.out.println("end binding...");
    }

}
