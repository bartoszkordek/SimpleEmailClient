package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.ui.stages.ProgressBarStage;
import io.github.email.client.util.DownloadEmails;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class GetEmailsButton extends JFXButton {

    private final TableView tableView;

    public GetEmailsButton(String text, TableView tableView) {
        super(text);
        this.tableView = tableView;
        this.getStyleClass().add("button-raised");
        this.setOnMouseClicked(this::downLoadEmails);
    }

    private void downLoadEmails(MouseEvent mouseEvent) {
        ProgressBarStage progressBarStage = new ProgressBarStage();
        progressBarStage.show();

        DownloadEmails downloadEmails = new DownloadEmails(
                progressBarStage.getProgressBar(),
                tableView
        );

        downloadEmails.start();
    }
}
