package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.exceptions.InvalidEmailException;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.SendApi;
import io.github.email.client.smtp.SmtpClient;
import io.github.email.client.ui.components.textfields.CustomTextField;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import io.github.email.client.ui.stages.ResponseDialogStage;
import io.github.email.client.validation.EmailParser;
import io.github.email.client.validation.EmailParserImpl;
import javafx.scene.control.ProgressBar;
import javafx.scene.web.HTMLEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Properties;

public class SendEmailButton extends JFXButton {
    private final ConfigService configUtil = new ConfigService();
    private final SendApi smtpClient = new SmtpClient();
    private final Logger logger = LoggerFactory.getLogger(SendEmailButton.class);

    public SendEmailButton(
            EmailTextField toAddresses,
            EmailTextField ccAddresses,
            EmailTextField bccAddresses,
            SubjectEmailTextField subject,
            HTMLEditor htmlEditor,
            CustomTextField attachments,
            ProgressBar progressBar
    ) {
        super("Send");
        this.getStyleClass().add("button-raised");
        this.setOnMouseClicked(event ->
                handleSendClick(toAddresses, ccAddresses, bccAddresses, subject, htmlEditor, attachments, progressBar)
        );
    }

    private void handleSendClick(
            EmailTextField toAddresses,
            EmailTextField ccAddresses,
            EmailTextField bccAddresses,
            SubjectEmailTextField subjectText,
            HTMLEditor htmlEditor,
            CustomTextField attachmentsField,
            ProgressBar progressBar
    ) {
        logger.info("Sending email....");
        progressBar.setVisible(true);

        try {
            EmailParser emailParser = new EmailParserImpl();
            String[] toEmails = emailParser.parseEmails(toAddresses.getText());
            String[] ccEmails = emailParser.parseEmails(ccAddresses.getText());
            String[] bccEmails = emailParser.parseEmails(bccAddresses.getText());
            String[] attachments = attachmentsField.getText().split(";");
            String subject = subjectText.getText();
            if (toEmails.length == 0) {
                throw new InvalidEmailException("You should provide at least one email");
            }
            if (subject.isBlank()) {
                throw new InvalidEmailException("Subject cannot be empty");
            }
            File[] files = Arrays.stream(attachments)
                    .map(File::new)
                    .toArray(File[]::new);

            this.sendEmail(toEmails, ccEmails, bccEmails, subject, htmlEditor.getHtmlText(), files, progressBar);

            logger.info("The e-mail has been sent successfully");
        } catch (Exception e) {
            logger.error("Error while sending the e-mail: " + e.getMessage());
            e.printStackTrace();
            ResponseDialogStage responseDialog = new ResponseDialogStage("Error while sending the e-mail: " + e.getMessage());
            responseDialog.show();
        }
    }

    private void sendEmail(
            String[] toEmails,
            String[] ccEmails,
            String[] bccEmails,
            String subject,
            String htmlContent,
            File[] attachFiles,
            ProgressBar progressBar
    ) {
        Thread sendEmail = new Thread(() -> {
            Properties configProperties = configUtil.getProperties();
            smtpClient.sendEmail(
                    configProperties,
                    toEmails,
                    ccEmails,
                    bccEmails,
                    subject,
                    htmlContent,
                    attachFiles,
                    progressBar
            );
        });
        sendEmail.start();
    }
}
