package io.github.email.client.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailValidatorTest {
    private EmailValidator emailValidator;

    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidatorImpl();
    }

    @Test
    void shouldReturnTrueWhenAllEmailsAreValid() {
        String[] testEmails = new String[]{
                "jan.kowalski@gmail.com",
                "gzacharski@student.agh.edu.pl",
                "jbs62345@zwoho.com",
                "xdf90794@cuoly.com"
        };
        assertThat(emailValidator.isAllEmailValid(testEmails)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenAtLeastOneEmailIsInvalid() {
        String[] testEmails = new String[]{
                "jan.kowalski@gmail.com",
                "gzacharski@student.agh.edu.pl",
                "jbs62345@zwoho.com",
                "xdf90794cuoly.com"
        };
        assertThat(emailValidator.isAllEmailValid(testEmails)).isFalse();
    }

    @Test
    void shouldReturnFalseWhenAllEmailAreInvalid() {
        String[] testEmails = new String[]{
                "jan.kowalski@gmail.co",
                "gzacharskistudent.agh.edu.pl",
                "jbs62345@zwohocom",
                "xdf90794cuoly.com"
        };
        assertThat(emailValidator.isAllEmailValid(testEmails)).isFalse();
    }
}