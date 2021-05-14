package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXTextField;
import io.github.email.client.service.ConfigService;
import io.github.email.client.ui.components.buttons.SaveSettingsButton;
import io.github.email.client.util.PropertiesLoader;
import io.github.email.client.util.PropertiesLoaderImpl;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

public class SettingsTab extends Tab {
    private final ConfigService configUtil;
    private final PropertiesLoader propertiesLoader;

    public SettingsTab() {
        super();
        this.configUtil = new ConfigService();
        this.propertiesLoader = new PropertiesLoaderImpl(configUtil.getProperties());
        this.setClosable(false);
        this.setText("Settings");
        this.setContent(getSettingsContent());
    }

    private Node getSettingsContent() {
        JFXTextField smtpHost = getSmtpHostTextField();
        JFXTextField smtpPort = getSmtpPortTextField();
        JFXTextField imapHost = getImapHostTextField();
        JFXTextField imapPort = getImapPortTextField();
        JFXTextField username = getUsernameTextField();
        JFXTextField password = getPasswordTextField();

        SaveSettingsButton saveButton = new SaveSettingsButton(
                smtpHost,
                smtpPort,
                imapHost,
                imapPort,
                username,
                password,
                configUtil
        );

        return buildGridPane(smtpHost, smtpPort, imapHost, imapPort, username, password, saveButton);
    }

    private GridPane buildGridPane(
            JFXTextField smtpHost,
            JFXTextField smtpPort,
            JFXTextField imapHost,
            JFXTextField imapPort,
            JFXTextField username,
            JFXTextField password,
            SaveSettingsButton saveButton
    ) {
        GridPane gridPane = new GridPane();

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setLayoutX(30);
        gridPane.setLayoutY(30);

        gridPane.add(new Label("SMTP host:"), 2, 2);
        gridPane.add(smtpHost, 3, 2);

        gridPane.add(new Label("SMTP port:"), 2, 3);
        gridPane.add(smtpPort, 3, 3);

        gridPane.add(new Label("IMAP host:"), 2, 4);
        gridPane.add(imapHost, 3, 4);

        gridPane.add(new Label("IMAP port:"), 2, 5);
        gridPane.add(imapPort, 3, 5);

        gridPane.add(new Label("Username:"), 2, 6);
        gridPane.add(username, 3, 6);

        gridPane.add(new Label("Password:"), 2, 7);
        gridPane.add(password, 3, 7);

        gridPane.add(saveButton, 3, 8);

        return gridPane;
    }

    private JFXTextField getSmtpHostTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        String text = propertiesLoader.getSmtpHost();
        textField.setText(text);
        return textField;
    }

    private JFXTextField getSmtpPortTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        String text = String.valueOf(propertiesLoader.getSmtpPort());
        textField.setText(text);
        return textField;
    }

    private JFXTextField getImapHostTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        String text = propertiesLoader.getImapHost();
        textField.setText(text);
        return textField;
    }

    private JFXTextField getImapPortTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        String text = String.valueOf(propertiesLoader.getImapPort());
        textField.setText(text);
        return textField;
    }

    private JFXTextField getUsernameTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        String text = propertiesLoader.getUser();
        textField.setText(text);
        return textField;
    }

    private JFXTextField getPasswordTextField() {
        JFXTextField textField = new JFXTextField();
        textField.setStyle("-fx-label-float:true;");
        String text = propertiesLoader.getPassword();
        textField.setText(text);
        return textField;
    }
}
