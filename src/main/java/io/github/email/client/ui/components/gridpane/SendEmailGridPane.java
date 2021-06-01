package io.github.email.client.ui.components.gridpane;

import io.github.email.client.ui.components.textfields.CustomTextField;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import javafx.scene.layout.GridPane;

public class SendEmailGridPane extends GridPane {

    public SendEmailGridPane(EmailTextField[] emailFields, SubjectEmailTextField subjectField) {
        this.setVgap(10);
        this.setHgap(10);
        this.setStyle("-fx-padding: 10;");
        this.buildGridPane(emailFields, subjectField);
    }

    private void buildGridPane(EmailTextField[] emailFields, SubjectEmailTextField subjectField) {
        for (int i = 0; i < emailFields.length; i++) {
            buildRowPane(emailFields[i], i);
        }
        buildRowPane(subjectField, emailFields.length);
    }

    private void buildRowPane(CustomTextField field, int row) {
        this.add(field.getLabel(), 0, row);
        this.add(field, 1, row);
        this.add(field.getErrorLabel(), 2, row);
    }
}
