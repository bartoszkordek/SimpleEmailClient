package io.github.email.client.validation;

import io.github.email.client.exceptions.InvalidEmailException;

public interface EmailParser {
    String[] parseEmails(String emailsAsString) throws InvalidEmailException;
}
