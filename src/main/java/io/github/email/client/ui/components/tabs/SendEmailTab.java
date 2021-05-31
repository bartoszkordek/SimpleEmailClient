package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.SendApi;
import io.github.email.client.smtp.SmtpClient;
import io.github.email.client.ui.components.gridpane.SendEmailGridPane;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

import java.io.File;
import java.util.Properties;

public class SendEmailTab extends Tab {
    private final ConfigService configUtil = new ConfigService();
    private final SendApi smtpClient = new SmtpClient(true);

    public SendEmailTab() {
        this.setClosable(false);
        this.setText("Send email");
        this.setContent(getEmailTabContent());
    }

    private Node getEmailTabContent() {
        VBox vBox = new VBox();

        HTMLEditor htmlEditor = new HTMLEditor();

        EmailTextField toAddresses = new EmailTextField("To:");
        EmailTextField ccAddresses = new EmailTextField("CC:");
        EmailTextField bccAddresses = new EmailTextField("Bcc:");
        SubjectEmailTextField subject = new SubjectEmailTextField();

        GridPane gridPane = new SendEmailGridPane(
                new EmailTextField[]{toAddresses, ccAddresses, bccAddresses},
                subject
        );

        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(htmlEditor);

        HBox hBox = new HBox();
        JFXButton addFileButton = getAddFileButton();
        JFXButton sendButton = getSendButton(htmlEditor);

        hBox.getChildren().add(addFileButton);
        hBox.getChildren().add(sendButton);

        vBox.getChildren().add(hBox);
        return vBox;
    }

    private JFXButton getAddFileButton() {
        JFXButton addFileButton = new JFXButton("Add file");
        addFileButton.getStyleClass().add("button-raised");

        return addFileButton;
    }

    private JFXButton getSendButton(HTMLEditor htmlEditor) {
        JFXButton sendButton = new JFXButton("Send");
        sendButton.getStyleClass().add("button-raised");
        sendButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("button clicked");
//            String htmlMessage = htmlEditor.getHtmlText();
//            this.sendEmail(htmlMessage);
        });

        return sendButton;
    }

    private void sendEmail(String htmlMessage) {
        String[] toAddresses = new String[]{"aghproject2020@gmail.com"};
        String[] ccAddresses = null;
        String[] bccAddresses = null;
        String subject = "Test html";
        File[] attachFiles = null;

        try {
            Properties configProperties = configUtil.getProperties();
            smtpClient.sendEmail(
                    configProperties,
                    toAddresses,
                    ccAddresses,
                    bccAddresses,
                    subject,
                    htmlMessage,
                    attachFiles
            );

            System.out.println("The e-mail has been sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while sending the e-mail: " + e.getMessage());
        }
    }

}
