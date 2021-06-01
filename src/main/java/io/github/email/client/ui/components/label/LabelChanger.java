package io.github.email.client.ui.components.label;

import io.github.email.client.ui.components.textfields.CustomTextField;
import javafx.scene.control.Label;

public class LabelChanger {
    private final CustomTextField customTextField;
    private final Label label;

    public LabelChanger(CustomTextField customTextField) {
        this.customTextField = customTextField;
        this.label = customTextField.getErrorLabel();
    }

    public void handleLabelChange() {
        if (label == null) return;
        if (customTextField.isValid()) {
            label.setText("");
        } else {
            String message = customTextField.getMessage();
            label.setText(message);
        }
    }
}
