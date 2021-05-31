package io.github.email.client.validation;

public interface EmailValidator {
    boolean isAllEmailValid(String[] emails);

    boolean isEmailValid(String email);
}
