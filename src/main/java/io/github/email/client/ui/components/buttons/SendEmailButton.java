package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.exceptions.InvalidEmailException;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.SendApi;
import io.github.email.client.smtp.SmtpClient;
import io.github.email.client.ui.stages.ResponseDialogStage;
import io.github.email.client.validation.EmailParser;
import io.github.email.client.validation.EmailParserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;

public class SendEmailButton extends JFXButton {
    private final ConfigService configUtil = new ConfigService();
    private final SendApi smtpClient = new SmtpClient();
    private final Logger logger = LoggerFactory.getLogger(SendEmailButton.class);

    public SendEmailButton(
            String toAddresses,
            String ccAddresses,
            String bccAddresses,
            String subject,
            String html,
            File[] attachFiles
    ) {
        super("Send");
        this.getStyleClass().add("button-raised");
        this.setOnMouseClicked(event ->
                handleSendClick(toAddresses, ccAddresses, bccAddresses, subject, html, attachFiles)
        );
    }

    private void handleSendClick(
            String toAddresses,
            String ccAddresses,
            String bccAddresses,
            String subject,
            String html,
            File[] attachFiles
    ) {
        logger.info("Sending email....");

        try {
            EmailParser emailParser = new EmailParserImpl();
            String[] toEmails = emailParser.parseEmails(toAddresses);
            String[] ccEmails = emailParser.parseEmails(ccAddresses);
            String[] bccEmails = emailParser.parseEmails(bccAddresses);

            if (toEmails.length == 0) {
                throw new InvalidEmailException("You should provide at least one email");
            }
            if (subject.isBlank()) {
                throw new InvalidEmailException("Subject cannot be empty");
            }
            this.sendEmail(toEmails, ccEmails, bccEmails, subject, html, attachFiles);
        } catch (InvalidEmailException exception) {
            ResponseDialogStage responseDialog = new ResponseDialogStage(exception.getMessage());
            responseDialog.show();
            exception.printStackTrace();
        }
    }

    private void sendEmail(
            String[] toEmails,
            String[] ccEmails,
            String[] bccEmails,
            String subject,
            String htmlContent,
            File[] attachFiles
    ) {

        try {
            Properties configProperties = configUtil.getProperties();
            smtpClient.sendEmail(
                    configProperties,
                    toEmails,
                    ccEmails,
                    bccEmails,
                    subject,
                    htmlContent,
                    attachFiles
            );
            logger.info("The e-mail has been sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Error while sending the e-mail: " + e.getMessage());
        }
    }
}
