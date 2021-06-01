package io.github.email.client.ui.components.buttons;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.ui.components.tabs.Person;
import io.github.email.client.ui.stages.ProgressBarStage;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class GetEmailsButton extends JFXButton {
    private TableView tableView;

    public GetEmailsButton(String text, TableView tableView) {
        super(text);
        this.tableView = tableView;
        this.getStyleClass().add("button-raised");
        this.setOnMouseClicked(this::downLoadEmails);
    }

    private void downLoadEmails(MouseEvent mouseEvent) {
        ProgressBarStage progressBarStage = new ProgressBarStage();
        progressBarStage.show();

        Thread downloadEmailThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    tableView.getItems().add(new Person("John", "Doe"));
                    Thread.sleep(1000);
                    double progress = ((double) i + 1.0) / 10;
                    progressBarStage.getProgressBar().setProgress(progress);
                    tableView.getItems().add(new Person("Jane", "Deer"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        downloadEmailThread.start();
    }
}
