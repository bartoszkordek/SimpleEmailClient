package io.github.email.client.ui.components.tables;

import io.github.email.client.ui.components.background.CustomBackground;
import io.github.email.client.util.Email;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ReceivedEmailSideBarTable extends TableView {

    public ReceivedEmailSideBarTable(Pane pane) {
        this.setPlaceholder(getNoContentLabel());
        this.setBackground(CustomBackground.getBackground("images/background/bg4.jpg"));
        this.createColumns();
        this.prefHeightProperty().bind(pane.heightProperty());
    }

    private void createColumns() {

        TableColumn<Email, String> fromColumn = new TableColumn<>("From");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        this.getColumns().add(fromColumn);

        TableColumn<Email, String> subject = new TableColumn<>("Subject");
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        this.getColumns().add(subject);

        TableColumn<Email, String> bodyHtml = new TableColumn<>("Message");
        bodyHtml.setCellValueFactory(new PropertyValueFactory<>("bodyPlain"));
        this.getColumns().add(bodyHtml);

        TableColumn<Email, String> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.getColumns().add(date);

    }

    private Label getNoContentLabel() {
        Label label = new Label();
        label.setText("No email to display.\nClick button to download emails.");
        label.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-alignment: center");
        return label;
    }
}
