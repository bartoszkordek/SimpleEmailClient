package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.util.DownloadEmails;
import io.github.email.client.util.Email;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class GetEmailsButton extends JFXButton {

    private final TableView<Email> tableView;
    private final ProgressBar progressBar;

    public GetEmailsButton(TableView<Email> tableView, ProgressBar progressBar) {
        super("Get emails");
        this.tableView = tableView;
        this.progressBar = progressBar;
        this.getStyleClass().add("button-raised");
        this.setOnMouseClicked(this::downLoadEmails);
    }

    private void downLoadEmails(MouseEvent mouseEvent) {
        progressBar.setVisible(true);
        DownloadEmails downloadEmails = new DownloadEmails(progressBar, tableView);
        downloadEmails.start();
    }
}
