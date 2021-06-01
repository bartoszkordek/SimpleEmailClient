package io.github.email.client.ui.components.textfields.properties;

import io.github.email.client.util.PropertiesLoader;

public class SmtpHostTextField extends BasicPropertyTextField {

    public SmtpHostTextField(PropertiesLoader propertiesLoader) {
        super();
        String text = propertiesLoader.getSmtpHost();
        this.setText(text);
        this.getLabel().setText("SMTP host:");
    }
}
