package io.github.email.client.ui.components.tables;

import io.github.email.client.ui.components.background.CustomBackground;
import io.github.email.client.ui.stages.ShowEmailStage;
import io.github.email.client.util.Email;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ReceivedEmailTable extends TableView<Email> {

    public ReceivedEmailTable(Pane pane) {
        this.setPlaceholder(getNoContentLabel());
        this.setBackground(CustomBackground.getBackground("images/background/bg4.jpg"));
        this.createColumns();
        this.prefHeightProperty().bind(pane.heightProperty());
        this.prefWidthProperty().bind(pane.widthProperty());
        this.setOnMouseClicked(this::handleMouseClicked);
    }

    private void createColumns() {

        TableColumn<Email, String> fromColumn = new TableColumn<>("From");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        this.getColumns().add(fromColumn);

        TableColumn<Email, String> toColumn = new TableColumn<>("To");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        this.getColumns().add(toColumn);

        TableColumn<Email, String> ccColumn = new TableColumn<>("Cc");
        ccColumn.setCellValueFactory(new PropertyValueFactory<>("cc"));
        this.getColumns().add(ccColumn);

        TableColumn<Email, String> bccColumn = new TableColumn<>("Bcc");
        bccColumn.setCellValueFactory(new PropertyValueFactory<>("bcc"));
        this.getColumns().add(bccColumn);

        TableColumn<Email, String> subject = new TableColumn<>("Subject");
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        this.getColumns().add(subject);

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

    private void handleMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) return;

        TableView.TableViewSelectionModel<Email> selectionModel = this.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        ObservableList<Email> selectedItems = selectionModel.getSelectedItems();
        Email selectedEmail = selectedItems.get(0);

        ShowEmailStage showEmailStage = new ShowEmailStage(selectedEmail);
        showEmailStage.show();
    }
}
