package io.github.email.client.ui.components.textfields;

import com.jfoenix.controls.JFXTextField;
import io.github.email.client.ui.components.label.LabelChanger;
import javafx.scene.control.Label;

public class CustomTextField extends JFXTextField {
    private final LabelChanger labelChanger;
    private final Label label;
    private String message;
    private boolean valid;

    public CustomTextField() {
        super();
        this.valid = true;
        this.label = new Label();
        this.labelChanger = new LabelChanger(this);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Label getLabel() {
        return label;
    }

    protected void handleLabelChange() {
        this.labelChanger.handleLabelChange();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
