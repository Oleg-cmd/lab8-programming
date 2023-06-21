package client.gui.controllers;

import client.gui.BtnEvents.AuthBtn;
import client.gui.BtnEvents.CreditsBtn;
import client.gui.setups.Setup;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import client.modules.ClientLogic;
import client.modules.HandleUserInput;

import java.io.IOException;
import java.net.URL;

public class ClientConnectionGUI extends Application {
    private static Stage mainStage; // Статическая переменная для хранения ссылки на Stage
    private static Thread clientThread; // Хранение ссылки на поток ClientLogic

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage; // Сохранение ссылки на Stage в статической переменной
        // Загрузка файла FXML
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fx/screens/loading.fxml");
        loader.setLocation(xmlUrl);
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

    public static void updateTextArea(String text) {
        Platform.runLater(() -> {
            System.out.println("updateTextArea() called");
            if (mainStage != null) {
                Scene scene = mainStage.getScene();
                VBox ccVbox = (VBox) scene.lookup("#ccVbox");

                // Remove all children from VBox except the button
                Button exact = (Button) scene.lookup("#ccButton");
                ccVbox.getChildren().removeIf(node -> node != exact);

                TextArea area = (TextArea) scene.lookup("#txtarea");
                if (area == null) {
                    // If TextArea doesn't exist, create and add it to the scene
                    area = new TextArea();
                    area.setId("txtarea");
                    VBox.setVgrow(area, Priority.ALWAYS); // Make TextArea grow vertically
                    ccVbox.getChildren().add(area);
                }
                area.setText(text);
                mainStage.setScene(scene);
                mainStage.show();
            } else {
                System.out.println("mainStage is null");
            }
        });
    }

    public static void updateInfoArea(String text) {
        Platform.runLater(() -> {
            System.out.println("updateInfoArea() called");
            if (mainStage != null) {
                Scene scene = mainStage.getScene();
                VBox ccVbox = (VBox) scene.lookup("#infoBox");
                TextArea area = (TextArea) scene.lookup("#infoArea");
                if (area == null) {
                    // If TextArea doesn't exist, create and add it to the scene
                    area = new TextArea();
                    area.setId("infoArea");
                    VBox.setVgrow(area, Priority.ALWAYS); // Make TextArea grow vertically
                    ccVbox.getChildren().add(area);
                }
                area.setText(text);
                mainStage.setScene(scene);
                mainStage.show();
            } else {
                System.out.println("mainStage is null");
            }
        });
    }

    public static void clearTextArea() {
        Platform.runLater(() -> {
            System.out.println("clearTextArea() called");
            if (mainStage != null) {
                Scene scene = mainStage.getScene();
                TextArea area = (TextArea) scene.lookup("#txtarea");
                if (area != null) {
                    area.setText(null);
                }
                mainStage.setScene(scene);
                mainStage.show();
            } else {
                System.out.println("mainStage is null");
            }
        });
    }

    public static Scene resetCC() {
        System.out.println("resetCC() called");
        if (mainStage != null) {
            Scene scene = mainStage.getScene();
            VBox ccVbox = (VBox) scene.lookup("#ccVbox");
            TextArea area = (TextArea) scene.lookup("#txtarea");
            if (area == null) {
                System.out.println("area is null, deleting other things");
                // Remove all children from VBox except the button
                Button exact = (Button) scene.lookup("#ccButton");
                ccVbox.getChildren().removeIf(node -> node != exact);
                System.out.println("deleting success");
            } else {
                System.out.println("deleting area");
                ccVbox.getChildren().remove(area);
                System.out.println("deleting success");
            }
            System.out.println("setScene resetCC()");
            mainStage.setScene(scene);
            mainStage.show();
            return scene;
        } else {
            System.out.println("mainstage is null");
            return Setup.setupScene;
        }

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
                    HandleUserInput.SendCommand("show");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("mainStage is null");
            }
        });

    }

}
