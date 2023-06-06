package client.gui.BtnEvents;

import client.modules.StringAnalyzer;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class AuthBtn {
    public AuthBtn() {
    }

    public void bindEvents(Scene scene) {
        System.out.println("binding...");
        // Получаем ссылку на кнопку из FXML
        Button regButton = (Button) scene.lookup("#reg");
        // Привязываем лямбда-выражение вместо анонимного класса
        regButton.setOnAction((ActionEvent event) -> {
            // Логика работы при нажатии кнопки "Register"
            System.out.println("Register button clicked");
            // Дополнительный код...
            StringAnalyzer.setClientData("1");
        });

        // Получаем ссылку на кнопку из FXML
        Button logButton = (Button) scene.lookup("#log");
        // Привязываем лямбда-выражение к событию нажатия кнопки "Login"
        logButton.setOnAction((ActionEvent event) -> {
            // Логика работы при нажатии кнопки "Login"
            System.out.println("Login button clicked");
            // Дополнительный код...
            StringAnalyzer.setClientData("2");
        });

        System.out.println("end binding...");
    }
}
