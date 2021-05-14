package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class SendEmailTab extends Tab {

    public SendEmailTab() {
        this.setClosable(false);
        this.setText("Send email");
        this.setContent(getEmailContent());
    }

    private Node getEmailContent() {
        FlowPane main = new FlowPane();
        VBox vBox = new VBox();

        HTMLEditor htmlEditor = new HTMLEditor();

        JFXButton jfoenixButton = new JFXButton("Send");
        jfoenixButton.getStyleClass().add("button-raised");


        main.getChildren().add(jfoenixButton);

        vBox.getChildren().add(htmlEditor);
        vBox.getChildren().add(jfoenixButton);
        return vBox;
    }

}
