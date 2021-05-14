package io.github.email.client.ui.tabs;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;

public class SendEmailTab extends Tab {

    public SendEmailTab() {
        this.setClosable(false);
        this.setText("Send email");
        this.setContent(getEmailContent());
    }

    private Node getEmailContent() {
        FlowPane main = new FlowPane();

        JFXButton jfoenixButton = new JFXButton("Send");
        jfoenixButton.getStyleClass().add("button-raised");

        main.getChildren().add(jfoenixButton);
        return main;
    }

}
