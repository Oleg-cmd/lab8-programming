package client.gui;

import client.gui.BtnEvents.AuthBtn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import client.modules.ClientLogic;

import java.io.IOException;
import java.net.URL;

public class ClientConnectionGUI extends Application {
    private static Stage mainStage; // Статическая переменная для хранения ссылки на Stage
    private static Thread clientThread; // Хранение ссылки на поток ClientLogic

    // Создаем экземпляр EventManager
    public static EventManager eventManager = new EventManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage; // Сохранение ссылки на Stage в статической переменной
        // Загрузка файла FXML
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fx/screens/loading.fxml");
        loader.setLocation(xmlUrl);
        Parent loadingScreen = loader.load();
        // Установка сцены на основной Stage
        primaryStage.setTitle("GUI");
        // Отображение GUI
        showLoadingWindow();

        // Запуск логики клиента в отдельном потоке
        clientThread = new Thread(() -> new ClientLogic().run());
        clientThread.start();

        primaryStage.setOnCloseRequest(e -> {
            // Остановка потока ClientLogic при закрытии приложения
            clientThread.interrupt();
            Platform.exit();
            System.exit(0);
        });
    }

    public static void showLoadingWindow() {
        Platform.runLater(() -> {
            System.out.println("showLoadingWindow() called");
            try {
                FXMLLoader loader = new FXMLLoader();
                URL xmlUrl = ClientConnectionGUI.class.getResource("/fx/screens/loading.fxml");
                loader.setLocation(xmlUrl);
                Parent rootLayout = loader.load();
                Scene scene = new Scene(rootLayout);
                mainStage.setScene(scene);
                mainStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public static void showAuthWindow() {
        Platform.runLater(() -> {
            System.out.println("showAuthWindow() called");
            if (mainStage != null) {
                try {
                    System.out.println("creating...");
                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl = ClientConnectionGUI.class.getResource("/fx/screens/auth.fxml");
                    loader.setLocation(xmlUrl);
                    System.out.println("creating...");
                    Parent rootLayout = loader.load();
                    Scene scene = new Scene(rootLayout);
                    System.out.println("creating...");
                    mainStage.setScene(scene);
                    mainStage.show();
                    System.out.println("creating...");
                    // Создаем экземпляр ButtonEventManager и передаем ему eventManager
                    AuthBtn authBtn = new AuthBtn();
                    System.out.println("created authbtn");
                    // Вызываем метод bindEvents() для привязки ивентов к кнопкам
                    authBtn.bindEvents(scene);
                    System.out.println("binded");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("mainStage is null");
            }
        });

    }

}
