package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.ui.components.buttons.GetEmailsButton;
import io.github.email.client.ui.components.tables.ReceivedEmailTable;
import io.github.email.client.util.Email;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GetEmailsTab extends Tab {

    public GetEmailsTab() {
        this.setClosable(false);
        this.setText("Get emails");
        this.setContent(getEmailsContent());
    }

    private Node getEmailsContent() {
        VBox main = new VBox();

        ProgressBar progressBar = getProgressBar();
        TableView<Email> tableView = new ReceivedEmailTable(main);
        JFXButton getEmailsButton = new GetEmailsButton(tableView, progressBar);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(50);
        hBox.getChildren().addAll(getEmailsButton, progressBar);

        main.getChildren().addAll(hBox, tableView);
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
}
