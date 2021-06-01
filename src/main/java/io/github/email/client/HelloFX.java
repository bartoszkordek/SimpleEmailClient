package io.github.email.client;

import io.github.email.client.ui.components.tabs.GetEmailsTab;
import io.github.email.client.ui.components.tabs.SendEmailTab;
import io.github.email.client.ui.components.tabs.SettingsTab;
import io.github.email.client.ui.scenes.MainScene;
import io.github.email.client.util.ImageLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloFX extends Application {
    private static final double HEIGHT = 720;
    private static final double WIDTH = 1280;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple email client");
        primaryStage.setHeight(HEIGHT);
        primaryStage.setWidth(WIDTH);
        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setMinWidth(WIDTH);

        MainScene mainScene = new MainScene(getMainSceneParent());
        mainScene.getStylesheets().add(
                HelloFX.class.getResource("/css/components.css").toExternalForm()
        );
        primaryStage.setScene(mainScene);
        primaryStage.getIcons().add(getIcon("images/icon/Envelope-icon.png"));

        primaryStage.show();
    }

    private Parent getMainSceneParent() {
        TabPane tabPane = new TabPane();

        Tab tab1 = new GetEmailsTab();
        Tab tab2 = new SendEmailTab();
        Tab tab3 = new SettingsTab();

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        return tabPane;
    }

    private Image getIcon(String icon) {
        ImageLoader imageLoader = new ImageLoader();
        return imageLoader.getImage(icon);
    }

}
