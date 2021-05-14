package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXTextField;
import io.github.email.client.ui.components.buttons.SaveSettinsBtn;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

public class SettingsTab extends Tab {

    public SettingsTab() {
        this.setClosable(false);
        this.setText("Settings");
        this.setContent(getSettingsContent());
    }

    private Node getSettingsContent() {
        GridPane gridPane = new GridPane();

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setLayoutX(30);
        gridPane.setLayoutY(30);

        gridPane.add(new Label("SMTP host:"), 2, 2);
        gridPane.add(getSmtpHostTextField(), 3, 2);

        gridPane.add(new Label("SMTP port:"), 2, 3);
        gridPane.add(getSmtpPortTextField(), 3, 3);

        gridPane.add(new Label("IMAP host:"), 2, 4);
        gridPane.add(getImapHostTextField(), 3, 4);

        gridPane.add(new Label("IMAP port:"), 2, 5);
        gridPane.add(getImapPortTextField(), 3, 5);

        gridPane.add(new Label("Username:"), 2, 6);
        gridPane.add(getUsernameTextField(), 3, 6);

        gridPane.add(new Label("Password:"), 2, 7);
        gridPane.add(getPasswordTextField(), 3, 7);

        gridPane.add(new SaveSettinsBtn(), 3, 8);

        return gridPane;
    }

    private JFXTextField getSmtpHostTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        textField.setPromptText("Type SMTP host");
        return textField;
    }

    private JFXTextField getSmtpPortTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        textField.setPromptText("Type SMTP port");
        return textField;
    }

    private JFXTextField getImapHostTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        textField.setPromptText("Type IMAP host");
        return textField;
    }

    private JFXTextField getImapPortTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        textField.setPromptText("Type IMAP port");
        return textField;
    }

    private JFXTextField getUsernameTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        textField.setPromptText("Type username");
        return textField;
    }

    private JFXTextField getPasswordTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        textField.setPromptText("Type password");
        return textField;
    }
}
