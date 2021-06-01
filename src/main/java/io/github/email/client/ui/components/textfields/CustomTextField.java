package io.github.email.client.ui.components.textfields;

import com.jfoenix.controls.JFXTextField;
import io.github.email.client.ui.components.label.LabelChanger;
import javafx.scene.control.Label;

public class CustomTextField extends JFXTextField {
    private final LabelChanger labelChanger;
    private final Label label;
    private final Label errorLabel;
    private String message;
    private boolean valid;

    public CustomTextField() {
        super();
        this.valid = true;
        this.label = new Label();
        this.errorLabel = new Label();
        this.labelChanger = new LabelChanger(this);
        this.setStyle("-fx-label-float:true");
        this.setMinWidth(300);
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

    public Label getErrorLabel() {
        return errorLabel;
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
