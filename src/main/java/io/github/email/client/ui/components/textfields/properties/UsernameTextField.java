package io.github.email.client.ui.components.textfields.properties;

import io.github.email.client.util.PropertiesLoader;

public class UsernameTextField extends BasicPropertyTextField {

    public UsernameTextField(PropertiesLoader propertiesLoader) {
        super();
        String text = propertiesLoader.getUser();
        this.setText(text);
        this.getLabel().setText("Username:");
    }
}
