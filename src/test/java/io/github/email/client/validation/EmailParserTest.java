package io.github.email.client.validation;

import io.github.email.client.exceptions.InvalidEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailParserTest {
    private EmailParser emailParser;
    private String[] expectedEmails;

    @BeforeEach
    void setUp() {
        emailParser = new EmailParserImpl();
        expectedEmails = new String[]{
                "jan.kowalski@gmail.com",
                "gzacharski@student.agh.edu.pl",
                "jbs62345@zwoho.com",
                "xdf90794@cuoly.com"
        };
    }

    @Test
    void shouldReturnArrayOfEmails() throws InvalidEmailException {
        String emailsAsString = "jan.kowalski@gmail.com;gzacharski@student.agh.edu.pl;" +
                "jbs62345@zwoho.com;xdf90794@cuoly.com";
        assertThat(emailParser.parseEmails(emailsAsString)).isEqualTo(expectedEmails);
    }

    @Test
    void shouldReturnArrayOfEmailsWithTrimmingEmails() throws InvalidEmailException {
        String emailsAsString = "jan.kowalski@gmail.com;         gzacharski@student.agh.edu.pl   ;" +
                "jbs62345@zwoho.com;xdf90794@cuoly.com";
        assertThat(emailParser.parseEmails(emailsAsString)).isEqualTo(expectedEmails);
    }

    @Test
    void shouldThrowExceptionWhenInvalidEmailProvided() {
        String emailsAsString = "jan.kowalski@gmail;         gzacharski@student.agh.edu.pl   ;" +
                "jbs62345@zwoho.com;xdf90794@cuoly.com";

        assertThatThrownBy(() -> emailParser.parseEmails(emailsAsString))
                .isInstanceOf(InvalidEmailException.class);
    }

    @Test
    void shouldReturnNullWhenNoEmailsProvided() throws InvalidEmailException {
        String emailsAsString="";
        assertThat(emailParser.parseEmails(emailsAsString)).isNull();
    }

    @Test
    void shouldReturnNullWhenNoEmailsProvidedWithTrimming() throws InvalidEmailException {
        String emailsAsString="  ";
        assertThat(emailParser.parseEmails(emailsAsString)).isNull();
    }

    @Test
    void shouldThrowExceptionWhenNoEmailsProvidedWithTrimming(){
        String emailsAsString="  ;   ";
        assertThatThrownBy(() -> emailParser.parseEmails(emailsAsString))
                .isInstanceOf(InvalidEmailException.class);
    }
}