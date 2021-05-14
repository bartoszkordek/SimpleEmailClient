package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;

public class SendEmailBtn extends JFXButton {

    public SendEmailBtn() {
        super();
        this.setText("Send");
        this.getStyleClass().add("button-raised");
        this.setOnMouseClicked(event -> System.out.println("Sending email...."));
    }
}
