package io.github.email.client.ui.components.gridpane;

import io.github.email.client.ui.components.textfields.CustomTextField;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.util.Email;
import javafx.scene.layout.GridPane;

public class ReceiveEmailGridPane extends GridPane {

    public ReceiveEmailGridPane(Email email) {
        this.setVgap(10);
        this.setHgap(10);
        this.setStyle("-fx-padding: 10;");
        this.buildGridPane(email);
    }

    private void buildGridPane(Email email) {
        EmailTextField[] emailFields = parseEmailAddressesFromEmail(email);

        for (int i = 0; i < emailFields.length; i++) {
            buildRowPane(emailFields[i], i);
        }
    }

    private EmailTextField[] parseEmailAddressesFromEmail(Email email) {

        EmailTextField fromAddress = getFromAddresses(email);
        EmailTextField ccAddresses = getCcAddresses(email);
        EmailTextField bccAddresses = getBccAddresses(email);
        EmailTextField subject = getSubjectEmail(email);

        return new EmailTextField[]{
                fromAddress,
                ccAddresses,
                bccAddresses,
                subject
        };
    }

    private void buildRowPane(CustomTextField field, int row) {
        this.add(field.getLabel(), 0, row);
        this.add(field, 1, row);
    }

    private EmailTextField getFromAddresses(Email email) {
        EmailTextField fromAddresses = new EmailTextField("To:");

        if (email.getFrom() == null) {
            fromAddresses.setVisible(false);
        } else {
            fromAddresses.setText(email.getFrom());
            fromAddresses.setEditable(false);
        }
        return fromAddresses;
    }

    private EmailTextField getCcAddresses(Email email) {
        EmailTextField fromAddresses = new EmailTextField("Cc:");

        if (email.getCc() == null) {
            fromAddresses.setVisible(false);
        } else {
            fromAddresses.setText(email.getCc());
            fromAddresses.setEditable(false);
        }
        return fromAddresses;
    }

    private EmailTextField getBccAddresses(Email email) {
        EmailTextField fromAddresses = new EmailTextField("Bcc:");

        if (email.getBcc() == null) {
            fromAddresses.setVisible(false);
        } else {
            fromAddresses.setText(email.getBcc());
            fromAddresses.setEditable(false);
        }
        return fromAddresses;
    }

    private EmailTextField getSubjectEmail(Email email) {
        EmailTextField fromAddresses = new EmailTextField("Subject:");

        if (email.getSubject() == null) {
            fromAddresses.setVisible(false);
        } else {
            fromAddresses.setText(email.getSubject());
            fromAddresses.setEditable(false);
        }
        return fromAddresses;
    }

}
