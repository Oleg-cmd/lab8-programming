package client.gui.BtnEvents;

import java.util.Locale;
import java.util.ResourceBundle;

import client.gui.controllers.ObservableResourceFactory;
import client.modules.StringAnalyzer;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AuthBtn {
    private final ObservableResourceFactory resourceFactory;

    public AuthBtn(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
    }

    public void bindEvents(Scene scene, Locale locale) {
        System.out.println("binding...");
        // Получаем ссылку на кнопку из FXML
        Button regButton = (Button) scene.lookup("#reg");

        Label text = (Label) scene.lookup("#authText");

        // Привязываем лямбда-выражение вместо анонимного класса
        StringBinding registerLabelBinding = resourceFactory.getStringBinding("btn_register_label");
        regButton.textProperty().bind(registerLabelBinding);
        regButton.setOnAction((ActionEvent event) -> {
            // Логика работы при нажатии кнопки "Register"
            System.out.println("Register button clicked");
            // Дополнительный код...
            StringAnalyzer.setClientData("1");
        });

        // Получаем ссылку на кнопку из FXML
        Button logButton = (Button) scene.lookup("#log");
        // Привязываем лямбда-выражение к событию нажатия кнопки "Login"
        StringBinding loginLabelBinding = resourceFactory.getStringBinding("btn_login_label");
        logButton.textProperty().bind(loginLabelBinding);
        logButton.setOnAction((ActionEvent event) -> {
            // Логика работы при нажатии кнопки "Login"
            System.out.println("Login button clicked");
            // Дополнительный код...
            StringAnalyzer.setClientData("2");
        });

        StringBinding authTextBinding = resourceFactory.getStringBinding("auth_text");
        text.textProperty().bind(authTextBinding);

        System.out.println("end binding...");
    }
}
