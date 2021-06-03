package io.github.email.client.ui.components.gridpane;

import io.github.email.client.ui.components.textfields.CustomTextField;
import io.github.email.client.ui.components.textfields.ReceivedEmailTextField;
import javafx.scene.layout.GridPane;

public class ReceiveEmailGridPane extends GridPane {

    public ReceiveEmailGridPane(ReceivedEmailTextField[] emailFields) {
        this.setVgap(10);
        this.setHgap(10);
        this.setStyle("-fx-padding: 10;");
        this.buildGridPane(emailFields);
    }

    private void buildGridPane(ReceivedEmailTextField[] emailFields) {
        for (int i = 0; i < emailFields.length; i++) {
            buildRowPane(emailFields[i], i);
        }
        CustomTextField subjectField = new CustomTextField();
        buildRowPane(subjectField, emailFields.length);
    }

    private void buildRowPane(CustomTextField field, int row) {
        this.add(field.getLabel(), 0, row);
    }
}
