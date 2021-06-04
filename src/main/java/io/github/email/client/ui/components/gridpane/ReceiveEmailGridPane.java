package io.github.email.client.ui.components.gridpane;

import io.github.email.client.imap.Attachment;
import io.github.email.client.ui.components.textfields.CustomTextField;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import io.github.email.client.util.Email;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.github.email.client.ui.components.textfields.EmailTextFieldFactory.getEmailTextField;
import static io.github.email.client.ui.components.textfields.EmailTextFieldFactory.getSubjectEmailTextField;

public class ReceiveEmailGridPane extends GridPane {
	private static final int BUFFER_SIZE = 4096;

    public ReceiveEmailGridPane(Email email) {
        this.setVgap(10);
        this.setHgap(10);
        this.setStyle("-fx-padding: 10;");
        this.buildGridPane(email);
    }

    private void buildGridPane(Email email) {
        CustomTextField[] emailFields = parseEmailAddressesFromEmail(email);
        int row;
        for (row = 0; row < emailFields.length; row++) {
            buildRowPane(emailFields[row], row);
        }
        for (Attachment attachment : email.getAttachments()) {
            Button button = new Button(attachment.getFileName());
            button.setOnMouseClicked((event) -> downloadAttachment(attachment));
            this.add(button, 0, row++);
        }
    }

    private void downloadAttachment(Attachment attachment) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(Window.getWindows().get(0));
        String fileName = attachment.getFileName();
        try (FileOutputStream outputStream = new FileOutputStream(dir.getAbsolutePath() + "/" + fileName)) {
            InputStream inputStream = new ByteArrayInputStream(attachment.getContent());
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
