package io.github.email.client.ui.components.textfields;

import io.github.email.client.util.Email;

public class EmailTextFieldFactory {

    public static EmailTextField getEmailTextField(String fieldName, String fieldContent) {
        EmailTextField textField = new EmailTextField(fieldName);

        if (fieldContent == null) {
            textField.setVisible(false);
        } else {
            textField.setText(fieldContent);
            textField.setEditable(false);
        }
        return textField;
    }

    public static SubjectEmailTextField getSubjectEmailTextField(Email email){
        SubjectEmailTextField textField = new SubjectEmailTextField();

        if (email.getSubject() == null) {
            textField.setVisible(false);
        } else {
            textField.setText(email.getSubject());
            textField.setEditable(false);
        }
        return textField;
    }
}
