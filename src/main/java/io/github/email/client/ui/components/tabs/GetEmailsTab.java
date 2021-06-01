package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.ui.components.background.CustomBackground;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;

public class GetEmailsTab extends Tab {

    public GetEmailsTab() {
        this.setClosable(false);
        this.setText("Get emails");
        this.setContent(getEmailsContent());
    }

    private Node getEmailsContent() {
        FlowPane main = new FlowPane();

        JFXButton jfoenixButton = new JFXButton("Get emails");
        jfoenixButton.getStyleClass().add("button-raised");

        CustomBackground customBackground =
                new CustomBackground("images/background/bg4.jpg");
        main.setBackground(customBackground.getBackground());
        main.getChildren().add(jfoenixButton);

        return main;
    }
}
