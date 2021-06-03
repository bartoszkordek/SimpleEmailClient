package io.github.email.client.ui.stages;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShowEmailStage extends Stage {

    public  ShowEmailStage() {
        super();
        this.initModality(Modality.APPLICATION_MODAL);
        this.setScene(getResponseScene());
    }

    private Scene getResponseScene() {
        JFXButton button = new JFXButton();
        button.getStyleClass().add("button-raised");
        button.setText("OK");
        button.setOnMouseClicked(event -> this.close());

        Label label = new Label("Here should be displayed the email");

        VBox vBox = new VBox();
        vBox.getChildren().add(label);
        vBox.getChildren().add(button);

        Scene scene = new Scene(vBox, 173, 100);
        scene.getStylesheets()
                .add(ResponseDialogStage.class.getResource("/css/components.css").toExternalForm());

        return scene;
    }
}
