package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.ui.components.buttons.AddFileButton;
import io.github.email.client.ui.components.buttons.SendEmailButton;
import io.github.email.client.ui.components.gridpane.SendEmailGridPane;
import io.github.email.client.ui.components.textfields.CustomTextField;
import io.github.email.client.ui.components.textfields.EmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class SendEmailTab extends Tab {
    private EmailTextField toAddresses;
    private EmailTextField ccAddresses;
    private EmailTextField bccAddresses;
    private CustomTextField attachments;
    private SubjectEmailTextField subject;
    private HTMLEditor htmlEditor;
    private VBox vBox;

    public SendEmailTab(Stage primaryStage) {
        this.setClosable(false);
        this.setText("Send email");
        this.setContent(getEmailTabContent(primaryStage));
    }

    private Node getEmailTabContent(Stage primaryStage) {
        vBox = new VBox();

        htmlEditor = new HTMLEditor();
        ProgressBar progressBar = getProgressBar();

        toAddresses = new EmailTextField("To:");
        ccAddresses = new EmailTextField("CC:");
        bccAddresses = new EmailTextField("Bcc:");
        attachments = new CustomTextField();
        attachments.getLabel().setText("Attachments:");
        subject = new SubjectEmailTextField();

        GridPane gridPane = new SendEmailGridPane(
                new EmailTextField[]{toAddresses, ccAddresses, bccAddresses},
                subject,
                attachments
        );

        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(htmlEditor);

        AddFileButton addFileButton = new AddFileButton(primaryStage, attachments);
        JFXButton sendButton = new SendEmailButton(
                toAddresses,
                ccAddresses,
                bccAddresses,
                subject,
                htmlEditor,
                attachments,
                progressBar,
                vBox
        );

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(addFileButton);
        hBox.getChildren().add(sendButton);
        hBox.getChildren().add(progressBar);

        vBox.getChildren().add(hBox);
        return vBox;
    }

    private ProgressBar getProgressBar() {
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(500);
        progressBar.setVisible(false);
        progressBar.setPadding(new Insets(0, 25, 0, 25));
        progressBar.progressProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0) {
                progressBar.setVisible(false);
                progressBar.setProgress(0);
                vBox.setDisable(false);
                resetSendEmailTab();
            }
        });
        return progressBar;
    }

    private void resetSendEmailTab() {
        toAddresses.setText("");
        ccAddresses.setText("");
        bccAddresses.setText("");
        subject.setText("");
        attachments.setText("");
    }
}
