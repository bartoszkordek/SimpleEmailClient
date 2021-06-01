package io.github.email.client.ui.components.tabs;

import io.github.email.client.service.ConfigService;
import io.github.email.client.ui.components.buttons.SaveSettingsButton;
import io.github.email.client.ui.components.gridpane.SettingsGridPane;
import io.github.email.client.ui.components.textfields.properties.*;
import io.github.email.client.util.PropertiesLoader;
import io.github.email.client.util.PropertiesLoaderImpl;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

import static io.github.email.client.ui.components.background.CustomBackground.getBackground;

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
        BasicPropertyTextField smtpHost = new SmtpHostTextField(propertiesLoader);
        BasicPropertyTextField smtpPort = new SmtpPortTextField(propertiesLoader);
        BasicPropertyTextField imapHost = new ImapHostTextField(propertiesLoader);
        BasicPropertyTextField imapPort = new ImapPortTextField(propertiesLoader);
        BasicPropertyTextField username = new UsernameTextField(propertiesLoader);
        BasicPropertyTextField password = new PasswordTextField(propertiesLoader);

        SaveSettingsButton saveButton = new SaveSettingsButton(
                smtpHost,
                smtpPort,
                imapHost,
                imapPort,
                username,
                password,
                configUtil
        );

        GridPane settingsPane = new SettingsGridPane(
                new BasicPropertyTextField[]{
                        smtpHost,
                        smtpPort,
                        imapHost,
                        imapPort,
                        username,
                        password
                },
                saveButton
        );

        settingsPane.setBackground(getBackground("images/background/bg1.jpg"));

        return settingsPane;
    }
}
