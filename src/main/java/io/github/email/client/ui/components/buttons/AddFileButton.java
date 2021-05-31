package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;

public class AddFileButton extends JFXButton {

    public AddFileButton() {
        super("Add file");
        this.getStyleClass().add("button-raised");
        this.setDisabled(true);
        this.setDisable(true);
    }
}
