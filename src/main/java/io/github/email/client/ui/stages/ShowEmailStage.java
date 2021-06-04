package io.github.email.client.ui.stages;

import io.github.email.client.ui.components.gridpane.ReceiveEmailGridPane;
import io.github.email.client.ui.components.icons.SimpleEmailIcon;
import io.github.email.client.util.Email;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ShowEmailStage extends Stage {
    private static final double HEIGHT = 600;
    private static final double WIDTH = 800;
    private final Email email;

    public ShowEmailStage(Email email) {
        super();
        this.email = email;
        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);
        this.setMinHeight(HEIGHT);
        this.setMinWidth(WIDTH);
        this.getIcons().add(SimpleEmailIcon.getIcon());
        this.setTitle("Simple email client - " + email.getSubject());
        this.setScene(getResponseScene());
    }

    private Scene getResponseScene() {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                getGridPane(),
                getWebView()
        );

        Scene scene = new Scene(vBox);
        scene.getStylesheets()
                .add(ResponseDialogStage.class.getResource("/css/components.css").toExternalForm());

        return scene;
    }

    private GridPane getGridPane() {
        return new ReceiveEmailGridPane(email);
    }

    private WebView getWebView() {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        String html = email.getBodyHtml()
                .replaceFirst("contenteditable=\"true\"", "contenteditable=\"false\"");
        webEngine.loadContent(html, "text/html");
        return webView;
    }
}
