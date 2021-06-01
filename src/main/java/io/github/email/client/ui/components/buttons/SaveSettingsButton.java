package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.github.email.client.service.ConfigService;
import io.github.email.client.ui.stages.ResponseDialogStage;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SaveSettingsButton extends JFXButton {
    private final ConfigService configUtil;
    private final JFXTextField imapHost;
    private final JFXTextField smtpHost;
    private final JFXTextField smtpPort;
    private final JFXTextField imapPort;
    private final JFXTextField username;
    private final JFXTextField password;
    private final Logger logger = LoggerFactory.getLogger(SaveSettingsButton.class);

    public SaveSettingsButton(
            JFXTextField smtpHost,
            JFXTextField smtpPort,
            JFXTextField imapHost,
            JFXTextField imapPort,
            JFXTextField username,
            JFXTextField password,
            ConfigService configService
    ) {
        super();
        this.setText("Save settings");
        this.getStyleClass().add("button-raised");
        this.setOnMouseClicked(this::buttonSaveActionPerformed);
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.imapHost = imapHost;
        this.imapPort = imapPort;
        this.username = username;
        this.password = password;
        this.configUtil = configService;
    }

    private void buttonSaveActionPerformed(MouseEvent event) {
        try {
            configUtil.saveProperties(
                    smtpHost.getText(),
                    imapHost.getText(),
                    smtpPort.getText(),
                    imapPort.getText(),
                    username.getText(),
                    password.getText()
            );
            logger.info("Settings were saved successfully.");

            ResponseDialogStage response = new ResponseDialogStage("Settings were saved successfully.");
            response.show();
        } catch (IOException e) {
            ResponseDialogStage response = new ResponseDialogStage("Error saving settings.");
            response.show();
        }
    }
}
