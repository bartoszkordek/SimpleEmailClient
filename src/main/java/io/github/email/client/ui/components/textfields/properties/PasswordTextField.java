package io.github.email.client.ui.components.textfields.properties;

import io.github.email.client.util.PropertiesLoader;

public class PasswordTextField extends BasicPropertyTextField {

    public PasswordTextField(PropertiesLoader propertiesLoader) {
        super();
        String text = propertiesLoader.getPassword();
        this.setText(text);
        this.getLabel().setText("Password:");
    }
}
