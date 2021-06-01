package io.github.email.client.ui.components.tabs;

import com.jfoenix.controls.JFXButton;
import io.github.email.client.util.ImageLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;

import static io.github.email.client.ui.components.background.CustomBackground.getBackground;

public class GetEmailsTab extends Tab {

    public GetEmailsTab() {
        this.setClosable(false);
        this.setText("Get emails");
        this.setContent(getEmailsContent());
    }

    private Node getEmailsContent() {
        FlowPane main = new FlowPane();

        TableView tableView = new TableView();

        JFXButton jfoenixButton = new JFXButton("Get emails");
        jfoenixButton.getStyleClass().add("button-raised");
        jfoenixButton.setOnMouseClicked(event -> {
            tableView.getItems().add(new Person("John", "Doe"));
            tableView.getItems().add(new Person("Jane", "Deer"));
        });

        main.setBackground(getBackground("images/background/bg4.jpg"));
        main.getChildren().add(jfoenixButton);



        TableColumn<Person, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> column2 = new TableColumn<>("Last name");
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);


        tableView.setPlaceholder(new Label("No rows to display"));
        ImageLoader loader = new ImageLoader();
        tableView.setBackground(getBackground("images/background/bg4.jpg"));

        main.getChildren().add(tableView);

        return main;
    }
}
