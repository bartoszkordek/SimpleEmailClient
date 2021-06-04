package io.github.email.client.ui.components.gridpane;

import io.github.email.client.ui.components.textfields.CustomTextField;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import io.github.email.client.util.Email;
import javafx.scene.layout.GridPane;

import static io.github.email.client.ui.components.textfields.EmailTextFieldFactory.getEmailTextField;
import static io.github.email.client.ui.components.textfields.EmailTextFieldFactory.getSubjectEmailTextField;

public class ReceiveEmailGridPane extends GridPane {

    public ReceiveEmailGridPane(Email email) {
        this.setVgap(10);
        this.setHgap(10);
        this.setStyle("-fx-padding: 10;");
        this.buildGridPane(email);
    }

    private void buildGridPane(Email email) {
        CustomTextField[] emailFields = parseEmailAddressesFromEmail(email);

        for (int i = 0; i < emailFields.length; i++) {
            buildRowPane(emailFields[i], i);
        }
    }

    private CustomTextField[] parseEmailAddressesFromEmail(Email email) {

        EmailTextField fromAddress = getEmailTextField("To:", email.getFrom());
        EmailTextField ccAddresses = getEmailTextField("Cc:", email.getCc());
        SubjectEmailTextField subject = getSubjectEmailTextField(email);

        return new CustomTextField[]{
                fromAddress,
                ccAddresses,
                subject
        };
    }

    private void buildRowPane(CustomTextField field, int row) {
        this.add(field.getLabel(), 0, row);
        this.add(field, 1, row);
    }
}
