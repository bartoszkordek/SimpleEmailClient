package io.github.email.client.validation;

public class EmailValidatorImpl implements EmailValidator {
    private final org.apache.commons.validator.routines.EmailValidator validator;

    public EmailValidatorImpl() {
        this.validator = org.apache.commons.validator.routines.EmailValidator.getInstance();
    }

    @Override
    public boolean isAllEmailValid(String[] emails) {
        for (String email : emails) {
            email = email.trim();
            boolean isAllValid = isEmailValid(email);
            if (!isAllValid) return false;
        }

        return true;
    }

    @Override
    public boolean isEmailValid(String email) {
        return validator.isValid(email);
    }
}
