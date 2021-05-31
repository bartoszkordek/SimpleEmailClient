package io.github.email.client.ui.components.textfields;

import com.jfoenix.controls.JFXTextField;
import io.github.email.client.validation.EmailValidator;
import io.github.email.client.validation.EmailValidatorImpl;

public class EmailTextField extends JFXTextField {
    private final EmailValidator validator;

    public EmailTextField() {
        super();
        this.validator = new EmailValidatorImpl();
        this.setStyle("-fx-label-float:true;-fx-pref-width: 1000");
        this.setOnKeyReleased(event -> validateTextFieldOnKeyReleased());
        this.focusedProperty().addListener((observable, oldValue, newValue) -> validateWhenNoFocused(newValue));
    }

    private void validateWhenNoFocused(Boolean newValue) {
        if (Boolean.FALSE.equals(newValue)) {
            String text = this.getText();
            String[] emails = text.split(";");
            boolean isAllValid = validator.isAllEmailValid(emails);
            if (!isAllValid) {
                this.setStyle("-fx-background-color: rgb(250, 160, 160)");
            } else {
                this.setStyle("-fx-background-color: white");
            }
        }
    }

    private void validateTextFieldOnKeyReleased() {
        String text = this.getText();
        String[] emails = text.split(";");
        boolean isAllValid = true;
        if (emails.length == 1 && emails[0].length() != 0) {
            isAllValid = validator.isAllEmailValid(emails);
        }
        if (isAllValid) this.setStyle("-fx-background-color: white");
    }
}
