package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.ui.components.buttons.GetEmailsButton;
import io.github.email.client.ui.components.tables.ReceivedEmailTable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;

public class GetEmailsTab extends Tab {

    public GetEmailsTab() {
        this.setClosable(false);
        this.setText("Get emails");
        this.setContent(getEmailsContent());
    }

    private Node getEmailsContent() {
        FlowPane main = new FlowPane();

        TableView tableView = new ReceivedEmailTable(main);

        JFXButton getEmailsButton = new GetEmailsButton("Get emails", tableView);

        main.getChildren().add(getEmailsButton);

        main.getChildren().add(tableView);

        return main;
    }
}
