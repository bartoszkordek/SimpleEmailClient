package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.ui.components.buttons.GetEmailsButton;
import io.github.email.client.ui.components.gridpane.ReceiveEmailGridPane;
import io.github.email.client.ui.components.tables.ReceivedEmailSideBarTable;
import io.github.email.client.ui.components.textfields.ReceivedEmailTextField;
import io.github.email.client.ui.components.textfields.SubjectEmailTextField;
import io.github.email.client.util.Email;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class GetEmailsTab extends Tab {

    public GetEmailsTab() {
        this.setClosable(false);
        this.setText("Get emails");
        this.setContent(getEmailsContent());
    }

    private Node getEmailsContent() {
        VBox mainVBox = new VBox();

        HBox main = new HBox();
        ProgressBar progressBar = getProgressBar();
        TableView tableView = new ReceivedEmailSideBarTable(main);
        JFXButton getEmailsButton = new GetEmailsButton(tableView, progressBar);

        ObservableList<Email> selected;
        selected = tableView.getSelectionModel().getSelectedItems();

        Email selectedEmail = new Email();

        selected.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                System.out.println("Selected Value" + selected);
                selectedEmail.setTo(selected.get(0).getTo());
                selectedEmail.setCc(selected.get(0).getCc());
                selectedEmail.setBcc(selected.get(0).getBcc());
                selectedEmail.setDate(selected.get(0).getDate());
                selectedEmail.setBodyHtml(selected.get(0).getBodyHtml());
                selectedEmail.setFrom(selected.get(0).getFrom());
                selectedEmail.setSubject(selected.get(0).getSubject());
                mainVBox.getChildren().set(0,getEmailTabContent(selectedEmail));
            }
        });

        VBox header = new VBox();
        header.getChildren().addAll(progressBar);
        header.getChildren().addAll(getEmailsButton);

        VBox sideBar = new VBox();
        sideBar.getChildren().addAll(header);
        sideBar.getChildren().addAll(tableView);
        main.getChildren().addAll(sideBar);


        mainVBox.setAlignment(Pos.CENTER_LEFT);
        mainVBox.setSpacing(50);

        mainVBox.getChildren().addAll(getEmailTabContent(selectedEmail));

        main.getChildren().addAll(mainVBox);
        main.getChildren().addAll(progressBar);

        return main;
    }

    private ProgressBar getProgressBar() {
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(150);
        progressBar.setVisible(false);
        progressBar.progressProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0) progressBar.setVisible(false);
        });
        return progressBar;
    }

    private Node getEmailTabContent(Email email) {

        VBox vBox = new VBox();

        HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setHtmlText(email.getBodyHtml());

        ReceivedEmailTextField fromAddress = new ReceivedEmailTextField("From:");
        if(email.getFrom() != null){
            fromAddress = new ReceivedEmailTextField("From: " +email.getFrom());
        }

        ReceivedEmailTextField toAddresses = new ReceivedEmailTextField("To:");
        if(email.getTo() != null){
            toAddresses = new ReceivedEmailTextField("To: " +email.getTo());
        }

        ReceivedEmailTextField ccAddresses = new ReceivedEmailTextField("Cc:");
        if(email.getCc() != null){
            ccAddresses = new ReceivedEmailTextField("Cc: " +email.getCc());
        }
        ReceivedEmailTextField bccAddresses = new ReceivedEmailTextField("Bcc:");
        if(email.getBcc() != null){
            bccAddresses = new ReceivedEmailTextField("Bcc: " +email.getBcc());
        }

        ReceivedEmailTextField subjectText = new ReceivedEmailTextField("Subject:");
        if(email.getSubject() != null){
            subjectText= new ReceivedEmailTextField("Subject: " +email.getSubject());
        }

        System.out.println("email.getSubject() " + email.getSubject());
        SubjectEmailTextField subject = new SubjectEmailTextField();
        if(email.getSubject() != null){
            subject.setMessage(email.getSubject());
        }

        GridPane gridPane = new ReceiveEmailGridPane(
                new ReceivedEmailTextField[]{fromAddress, toAddresses, ccAddresses, bccAddresses, subjectText}
        );

        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(htmlEditor);

        return vBox;
    }
}
