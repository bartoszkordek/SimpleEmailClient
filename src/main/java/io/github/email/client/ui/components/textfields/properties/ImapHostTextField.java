package io.github.email.client.ui.components.textfields.properties;

import io.github.email.client.util.PropertiesLoader;

public class ImapHostTextField extends BasicPropertyTextField {

    public ImapHostTextField(PropertiesLoader propertiesLoader) {
        super();
        String text = propertiesLoader.getImapHost();
        this.setText(text);
        this.getLabel().setText("IMAP host:");
    }
}
