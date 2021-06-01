package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.ui.components.buttons.AddFileButton;
import io.github.email.client.ui.components.buttons.SendEmailButton;
import io.github.email.client.ui.components.gridpane.SendEmailGridPane;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

import java.io.File;

public class SendEmailTab extends Tab {

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
        JFXButton addFileButton = new AddFileButton();
        JFXButton sendButton = new SendEmailButton(
                toAddresses.getText(),
                ccAddresses.getText(),
                bccAddresses.getText(),
                subject.getText(),
                htmlEditor.getHtmlText(),
                new File[0]
        );

        hBox.getChildren().add(addFileButton);
        hBox.getChildren().add(sendButton);

        vBox.getChildren().add(hBox);
        return vBox;
    }
}
