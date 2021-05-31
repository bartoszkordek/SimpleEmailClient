package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.SendApi;
import io.github.email.client.smtp.SmtpClient;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import javafx.scene.Node;
import javafx.scene.control.Label;
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

        EmailTextField toAddresses = new EmailTextField();
        EmailTextField ccAddresses = new EmailTextField();
        EmailTextField bccAddresses = new EmailTextField();
        SubjectEmailTextField subject = new SubjectEmailTextField();

        GridPane gridPane = getEmailAddressesGridPane(toAddresses, ccAddresses, bccAddresses, subject);

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

    private GridPane getEmailAddressesGridPane(
            EmailTextField toAddresses,
            JFXTextField ccAddresses,
            JFXTextField bccAddresses,
            SubjectEmailTextField subject
    ) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setStyle("-fx-padding: 10;");

        int row = 0;

        gridPane.add(new Label("To:"), 0, row);
        gridPane.add(toAddresses, 1, row);
        gridPane.add(toAddresses.getLabel(), 2, row);

        gridPane.add(new Label("CC:"), 0, ++row);
        gridPane.add(ccAddresses, 1, row);

        gridPane.add(new Label("Bcc:"), 0, ++row);
        gridPane.add(bccAddresses, 1, row);

        gridPane.add(new Label("Subject:"), 0, ++row);
        gridPane.add(subject, 1, row);
        gridPane.add(subject.getLabel(), 2, row);


        return gridPane;
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
