package io.github.email.client.ui.stages;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResponseDialogStage extends Stage {

    public ResponseDialogStage(String message) {
        super();
        this.initModality(Modality.APPLICATION_MODAL);
        this.setScene(getResponseScene(message));
    }

    private Scene getResponseScene(String message) {
        JFXButton button = new JFXButton();
        button.getStyleClass().add("button-raised");
        button.setText("OK");
        button.setOnMouseClicked(event -> this.close());

        Label label = new Label(message);

        VBox vBox = new VBox();
        vBox.getChildren().add(label);
        vBox.getChildren().add(button);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 400, 100);
        scene.getStylesheets()
                .add(ResponseDialogStage.class.getResource("/css/components.css").toExternalForm());

        return scene;
    }

}
