package io.github.email.client.ui.components.textfields.properties;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;

public class BasicPropertyTextField extends JFXTextField {

    private final Label label;
    private final Label errorLabel;

    public BasicPropertyTextField() {
        this.label = new Label();
        this.errorLabel = new Label();
        this.setStyle("-fx-label-float:true;");
    }

    public Label getLabel() {
        return label;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }
}
