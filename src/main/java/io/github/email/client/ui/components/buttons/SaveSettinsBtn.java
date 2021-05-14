package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;

public class SaveSettinsBtn extends JFXButton {

    public SaveSettinsBtn() {
        super();
        this.setText("Save settings");
        this.getStyleClass().add("button-raised");
        this.setOnMouseClicked(event -> System.out.println("Saving settings...."));
    }
}
