package io.github.email.client.ui.components.gridpane;

import io.github.email.client.ui.components.buttons.SaveSettingsButton;
import io.github.email.client.ui.components.textfields.properties.BasicPropertyTextField;
import javafx.scene.layout.GridPane;

public class SettingsGridPane extends GridPane {

    public SettingsGridPane(BasicPropertyTextField[] fields, SaveSettingsButton button) {
        this.setVgap(15);
        this.setHgap(15);
        this.setStyle("-fx-padding: 30;");
        this.buildGridPane(fields, button);
    }

    private void buildGridPane(BasicPropertyTextField[] fields, SaveSettingsButton button) {
        for (int i = 0; i < fields.length; i++) {
            buildRowPane(fields[i], i);
        }
        this.add(button, 1, fields.length);
    }

    private void buildRowPane(BasicPropertyTextField field, int row) {
        this.add(field.getLabel(), 0, row);
        this.add(field, 1, row);
        this.add(field.getErrorLabel(), 2, row);
    }
}
