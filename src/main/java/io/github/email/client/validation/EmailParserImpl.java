package io.github.email.client.validation;

import io.github.email.client.exceptions.InvalidEmailException;

public class EmailParserImpl implements EmailParser {
    private EmailValidator emailValidator = new EmailValidatorImpl();

    @Override
    public String[] parseEmails(String emailsAsString) throws InvalidEmailException {
        String[] emails = emailsAsString.split(";");
        if (emails.length == 1 && emails[0].trim().equals("")) return null;

        for (int i = 0; i < emails.length; i++) {
            emails[i] = emails[i].trim();
            boolean emailValid = emailValidator.isEmailValid(emails[i]);
            if (!emailValid) throw new InvalidEmailException();
        }

        return emails;
    }
}
