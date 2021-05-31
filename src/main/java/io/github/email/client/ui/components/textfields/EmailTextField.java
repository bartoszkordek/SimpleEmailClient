package io.github.email.client.ui.components.textfields;

import io.github.email.client.validation.EmailValidator;
import io.github.email.client.validation.EmailValidatorImpl;

import java.util.Arrays;

public class EmailTextField extends CustomTextField {
    private final EmailValidator validator;

    public EmailTextField(String name) {
        this.validator = new EmailValidatorImpl();
        this.getLabel().setText(name);
        this.setMessage("At least one email provided is invalid.");
        this.setStyle("-fx-label-float:true;-fx-pref-width: 1000");
        this.setOnKeyReleased(event -> validateTextFieldOnKeyReleased());
        this.focusedProperty().addListener((observable, oldValue, newValue) -> validateWhenNoFocused(newValue));
    }

    private void validateWhenNoFocused(Boolean newValue) {
        if (Boolean.FALSE.equals(newValue)) {
            if (isAllEmailValid()) {
                this.setStyle("-fx-background-color: white");
                this.setValid(true);
            } else {
                this.setStyle("-fx-background-color: rgb(250, 160, 160)");
                this.setValid(false);
            }
            this.handleLabelChange();
        }
    }

    private boolean isAllEmailValid() {
        String text = this.getText();
        String[] emails = text.split(";");
        emails = trimEmails(emails);
        if (emails.length == 1 && emails[0].length() == 0) return true;
        return validator.isAllEmailValid(emails);
    }

    private String[] trimEmails(String[] emails) {
        return Arrays.stream(emails)
                .map(String::trim)
                .toArray(String[]::new);
    }

    private void validateTextFieldOnKeyReleased() {
        if (!isAllEmailValid()) return;
        this.setStyle("-fx-background-color: white");
        this.setValid(true);
        this.handleLabelChange();
    }
}
