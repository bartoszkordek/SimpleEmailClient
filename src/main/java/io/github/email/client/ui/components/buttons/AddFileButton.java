package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.ui.components.textfields.CustomTextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddFileButton extends JFXButton {

    public AddFileButton(Stage primaryStage, CustomTextField attachments) {
        super("Add file");
        this.getStyleClass().add("button-raised");
        this.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose file");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (attachments.getText().isBlank()) {
                attachments.setText(file.getAbsolutePath());
            } else {
                attachments.setText(attachments.getText() + ";" + file.getAbsolutePath());
            }
        });
    }
}
