package client.gui.controllers;

import client.gui.BtnEvents.AuthBtn;
import client.gui.BtnEvents.CreditsBtn;
import client.gui.setups.Setup;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import client.modules.ClientLogic;
import client.modules.HandleUserInput;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientConnectionGUI extends Application {
    private static Stage mainStage; // Статическая переменная для хранения ссылки на Stage
    private static Thread clientThread; // Хранение ссылки на поток ClientLogic
    public static Locale currentLocale;

    private static ObservableResourceFactory resourceFactory = new ObservableResourceFactory();

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

        showLoadingWindow(currentLocale);
        resourceFactory.setResources(ResourceBundle.getBundle("messages/messages", Locale.forLanguageTag("en")));
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

    public static void showLoadingWindow(Locale locale) {
        Platform.runLater(() -> {
            System.out.println("showLoadingWindow() called");
            try {
                FXMLLoader loader = new FXMLLoader();
                URL xmlUrl = ClientConnectionGUI.class.getResource("/fx/screens/loading.fxml");
                loader.setLocation(xmlUrl);
                loader.setResources(resourceFactory.getResources());
                Parent rootLayout = loader.load();
                Scene scene = new Scene(rootLayout);

                scene = createMenuBar(scene);
                mainStage.setScene(scene);
                mainStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void showAuthWindow(Locale locale) {
        Platform.runLater(() -> {
            System.out.println("showAuthWindow() called");
            if (mainStage != null) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl = ClientConnectionGUI.class.getResource("/fx/screens/auth.fxml");
                    loader.setLocation(xmlUrl);
                    loader.setResources(resourceFactory.getResources());
                    Parent rootLayout = loader.load();
                    Scene scene = new Scene(rootLayout);

                    mainStage.setScene(scene);
                    mainStage.show();
                    AuthBtn authBtn = new AuthBtn(resourceFactory);
                    authBtn.bindEvents(scene, locale);
                    scene = createMenuBar(scene);
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
                    CreditsBtn creditsBtn = new CreditsBtn(resourceFactory);
                    creditsBtn.bindEvents(scene);

                    scene = createMenuBar(scene);
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

                    scene = createMenuBar(scene);
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
                createMenuBar(scene);
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
                    scene = Setup.setup(scene, resourceFactory);
                    mainStage.setScene(scene);
                    mainStage.show();

                    HandleUserInput.SendCommand("get_id");
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        System.out.println("damn thread exception in showMainScreen()");
                    }
                    HandleUserInput.SendCommand("show");
                    createMenuBar(scene);

                    Button execute = (Button) scene.lookup("#execute");
                    StringBinding executeLabelBinding = resourceFactory.getStringBinding("execute_button_label");
                    execute.textProperty().bind(executeLabelBinding);

                    FileChooser fileChooser = new FileChooser();
                    execute.setOnAction(event -> {
                        fileChooser.setTitle("Execute Script");
                        File selectedFile = fileChooser.showOpenDialog(mainStage);
                        HandleUserInput.SendCommand("execute_script " + selectedFile.getPath());

                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("mainStage is null");
            }
        });

    }

    private static Scene createMenuBar(Scene scene) {
        System.out.println("Creating menu bar");
        MenuBar menuBar = new MenuBar();
        Menu languageMenu = new Menu("Language");
        menuBar.getStyleClass().add("menu");

        MenuItem englishMenuItem = new MenuItem("English");
        englishMenuItem.setOnAction(event -> {
            currentLocale = new Locale("en");
            resourceFactory.setResources(ResourceBundle.getBundle("messages/messages", currentLocale));
        });

        MenuItem russianMenuItem = new MenuItem("Русский");
        russianMenuItem.setOnAction(event -> {
            currentLocale = new Locale("ru");
            resourceFactory.setResources(ResourceBundle.getBundle("messages/messages", currentLocale));
        });

        MenuItem slovakMenuItem = new MenuItem("Slovenčina");
        slovakMenuItem.setOnAction(event -> {
            currentLocale = new Locale("sk");
            resourceFactory.setResources(ResourceBundle.getBundle("messages/messages", currentLocale));
        });

        MenuItem albanianMenuItem = new MenuItem("Shqip");
        albanianMenuItem.setOnAction(event -> {
            currentLocale = new Locale("sq");
            resourceFactory.setResources(ResourceBundle.getBundle("messages/messages", currentLocale));
        });

        MenuItem spanishMenuItem = new MenuItem("Español");
        spanishMenuItem.setOnAction(event -> {
            currentLocale = new Locale("es", "GT");
            resourceFactory.setResources(ResourceBundle.getBundle("messages/messages", currentLocale));
        });

        languageMenu.getItems().addAll(
                englishMenuItem,
                russianMenuItem,
                slovakMenuItem,
                albanianMenuItem,
                spanishMenuItem);

        menuBar.getMenus().add(languageMenu);
        scene.getStylesheets().add(scene.getClass().getResource("/assets/styles/styles.css").toExternalForm());

        System.out.println("Adding menu bar to root layout");
        AnchorPane rootLayout = (AnchorPane) scene.getRoot();
        rootLayout.getChildren().remove(menuBar); // Удаляем menuBar, если он уже есть
        rootLayout.getChildren().add(menuBar); // Добавляем menuBar последним
        System.out.println("End of menu bar operations");
        return scene;

    }

}
