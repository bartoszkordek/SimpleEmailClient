package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class SendEmailTab extends Tab {

    public SendEmailTab() {
        this.setClosable(false);
        this.setText("Send email");
        this.setContent(getEmailContent());
    }

    private Node getEmailContent() {
        VBox vBox = new VBox();

        HTMLEditor htmlEditor = new HTMLEditor();

        JFXButton addFileButton = new JFXButton("Add file");
        addFileButton.getStyleClass().add("button-raised");

        JFXButton sendButton = new JFXButton("Send");
        sendButton.getStyleClass().add("button-raised");

        vBox.getChildren().add(htmlEditor);

        HBox hBox = new HBox();
        hBox.getChildren().add(addFileButton);
        hBox.getChildren().add(sendButton);

        vBox.getChildren().add(hBox);
        return vBox;
    }

}
