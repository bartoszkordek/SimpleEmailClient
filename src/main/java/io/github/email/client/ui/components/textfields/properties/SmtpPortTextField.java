package io.github.email.client.ui.components.textfields.properties;

import io.github.email.client.util.PropertiesLoader;

public class SmtpPortTextField extends BasicPropertyTextField {

    public SmtpPortTextField(PropertiesLoader propertiesLoader) {
        super();
        String text = String.valueOf(propertiesLoader.getSmtpPort());
        this.setText(text);
        this.getLabel().setText("SMTP port:");
    }
}
