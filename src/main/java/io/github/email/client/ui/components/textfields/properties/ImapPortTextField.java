package io.github.email.client.ui.components.textfields.properties;

import io.github.email.client.util.PropertiesLoader;

public class ImapPortTextField extends BasicPropertyTextField {

    public ImapPortTextField(PropertiesLoader propertiesLoader) {
        super();
        String text = String.valueOf(propertiesLoader.getImapPort());
        this.setText(text);
        this.getLabel().setText("IMAP port:");
    }
}
