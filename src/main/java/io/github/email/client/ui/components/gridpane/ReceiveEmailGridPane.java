package io.github.email.client.ui.components.gridpane;

import io.github.email.client.imap.Attachment;
import io.github.email.client.ui.components.textfields.CustomTextField;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import io.github.email.client.ui.stages.ResponseDialogStage;
import io.github.email.client.util.Email;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;

import static io.github.email.client.ui.components.textfields.EmailTextFieldFactory.getEmailTextField;
import static io.github.email.client.ui.components.textfields.EmailTextFieldFactory.getSubjectEmailTextField;

public class ReceiveEmailGridPane extends GridPane {
    private static final int BUFFER_SIZE = 4096;

    public ReceiveEmailGridPane(Email email, Stage stage) {
        this.setVgap(10);
        this.setHgap(10);
        this.setStyle("-fx-padding: 10;");
        this.buildGridPane(email, stage);
    }

    private void buildGridPane(Email email, Stage stage) {
        CustomTextField[] emailFields = parseEmailAddressesFromEmail(email);
        int row;
        for (row = 0; row < emailFields.length; row++) {
            buildRowPane(emailFields[row], row);
        }
        int col = 0;
        for (Attachment attachment : email.getAttachments()) {
            Button button = new Button(attachment.getFileName());
            button.setOnMouseClicked((event) -> downloadAttachment(attachment, stage));
            // allow 2 attachments per row
            this.add(button, col % 2, row + (col / 2));
            col++;
        }
    }

    private void downloadAttachment(Attachment attachment, Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Download");
        File dir = directoryChooser.showDialog(stage);
        String fileName = attachment.getFileName();
        try (FileOutputStream outputStream = new FileOutputStream(dir.getAbsolutePath() + "/" + fileName)) {
            InputStream inputStream = new ByteArrayInputStream(attachment.getContent());
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            ResponseDialogStage responseDialog = new ResponseDialogStage("Attachment " + fileName + " downloaded successfully.");
            responseDialog.setTitle("Downloading attachment");
            responseDialog.show();
        } catch (IOException e) {
            e.printStackTrace();
            ResponseDialogStage responseDialog = new ResponseDialogStage("Error while downloading " + fileName + " attachment: " + e.getMessage());
            responseDialog.setTitle("Downloading attachment");
            responseDialog.show();
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
