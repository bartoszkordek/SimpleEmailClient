package io.github.email.client.ui.stages;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgressBarStage extends Stage {
    private ProgressBar progressBar;

    public ProgressBarStage() {
        this.initModality(Modality.APPLICATION_MODAL);
        this.setScene(getProgressBarScene());
    }

    private Scene getProgressBarScene() {
        JFXButton button = new JFXButton();
        button.getStyleClass().add("button-raised");
        button.setText("Cancel");
        button.setOnMouseClicked(event -> this.close());

        Label label = new Label("Settings were saved successfully.");

        progressBar = new ProgressBar(0);

        VBox vBox = new VBox(progressBar);
        vBox.getChildren().add(label);
        vBox.getChildren().add(button);

        Scene scene = new Scene(vBox, 173, 100);
        scene.getStylesheets()
                .add(ResponseDialogStage.class.getResource("/css/components.css").toExternalForm());

        return scene;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
