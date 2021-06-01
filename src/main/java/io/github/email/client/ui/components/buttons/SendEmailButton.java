package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.exceptions.InvalidEmailException;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.SendApi;
import io.github.email.client.smtp.SmtpClient;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import io.github.email.client.ui.stages.InvalidEmailStage;
import io.github.email.client.validation.EmailParser;
import io.github.email.client.validation.EmailParserImpl;
import javafx.scene.web.HTMLEditor;

import java.io.File;
import java.util.Properties;

public class SendEmailButton extends JFXButton {
    private final ConfigService configUtil = new ConfigService();
    private final SendApi smtpClient = new SmtpClient();

    public SendEmailButton(
            EmailTextField toAddresses,
            EmailTextField ccAddresses,
            EmailTextField bccAddresses,
            SubjectEmailTextField subject,
            HTMLEditor htmlEditor,
            File[] attachFiles
    ) {
        super("Send");
        this.getStyleClass().add("button-raised");
        this.setOnMouseClicked(event ->
                handleSendClick(toAddresses, ccAddresses, bccAddresses, subject, htmlEditor, attachFiles)
        );
    }

    private void handleSendClick(
            EmailTextField toAddresses,
            EmailTextField ccAddresses,
            EmailTextField bccAddresses,
            SubjectEmailTextField subject,
            HTMLEditor htmlEditor,
            File[] attachFiles
    ) {
        System.out.println("Sending email....");

        try {
            EmailParser emailParser = new EmailParserImpl();
            String[] toEmails = emailParser.parseEmails(toAddresses.getText());
            String[] ccEmails = emailParser.parseEmails(ccAddresses.getText());
            String[] bccEmails = emailParser.parseEmails(bccAddresses.getText());

            if (toEmails == null) throw new InvalidEmailException("You should provide at least one email");

            this.sendEmail(toEmails, ccEmails, bccEmails, subject.getText(), htmlEditor.getHtmlText(), attachFiles);
        } catch (InvalidEmailException exception) {
            InvalidEmailStage emailStage = new InvalidEmailStage();
            emailStage.show();
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

            System.out.println("The e-mail has been sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while sending the e-mail: " + e.getMessage());
        }
    }
}
