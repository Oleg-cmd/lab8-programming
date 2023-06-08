package client.gui;

import client.gui.BtnEvents.AuthBtn;
import client.gui.BtnEvents.CreditsBtn;
import client.gui.events.Setup;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import client.modules.ClientLogic;

import java.io.IOException;
import java.net.URL;

public class ClientConnectionGUI extends Application {
    private static Stage mainStage; // Статическая переменная для хранения ссылки на Stage
    private static Thread clientThread; // Хранение ссылки на поток ClientLogic
    private static TextArea logTextArea;

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
            CloseUp();
        });

    }

    public static void CloseUp() {
        clientThread.interrupt();
        Platform.exit();
        System.exit(0);
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
                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl = ClientConnectionGUI.class.getResource("/fx/screens/auth.fxml");
                    loader.setLocation(xmlUrl);
                    Parent rootLayout = loader.load();
                    Scene scene = new Scene(rootLayout);
                    mainStage.setScene(scene);
                    mainStage.show();
                    // Создаем экземпляр ButtonEventManager и передаем ему eventManager
                    AuthBtn authBtn = new AuthBtn();
                    // Вызываем метод bindEvents() для привязки ивентов к кнопкам
                    authBtn.bindEvents(scene);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("mainStage is null");
            }
        });

    }

    public static void showCredits() {
        Platform.runLater(() -> {
            System.out.println("showCredits() called");
            if (mainStage != null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl = ClientConnectionGUI.class.getResource("/fx/screens/credits.fxml");
                    loader.setLocation(xmlUrl);
                    Parent rootLayout = loader.load();
                    Scene scene = new Scene(rootLayout);
                    mainStage.setScene(scene);
                    mainStage.show();
                    CreditsBtn creditsBtn = new CreditsBtn();
                    creditsBtn.bindEvents(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("mainStage is null");
            }
        });

    }

    public static void show404() {
        Platform.runLater(() -> {
            System.out.println("showCredits() called");
            if (mainStage != null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl = ClientConnectionGUI.class.getResource("/fx/screens/404.fxml");
                    loader.setLocation(xmlUrl);
                    Parent rootLayout = loader.load();
                    Scene scene = new Scene(rootLayout);
                    mainStage.setScene(scene);
                    mainStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("mainStage is null");
            }
        });

    }

    public static void updateSceneNow(Scene scene) {
        Platform.runLater(() -> {
            System.out.println("updateSceneNow() called");
            if (mainStage != null) {
                mainStage.setScene(scene);
                mainStage.show();
            } else {
                System.out.println("mainStage is null");
            }
        });

    }

    public static void showMainScreen() {
        Platform.runLater(() -> {
            System.out.println("showMainScreen() called");
            if (mainStage != null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl = ClientConnectionGUI.class.getResource("/fx/screens/main.fxml");
                    loader.setLocation(xmlUrl);
                    Parent rootLayout = loader.load();
                    Scene scene = new Scene(rootLayout);
                    scene = Setup.setup(scene);
                    mainStage.setScene(scene);
                    mainStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("mainStage is null");
            }
        });

    }

}
