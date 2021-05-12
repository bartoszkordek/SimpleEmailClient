package io.github.email.client.smtp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SmtpCommandTest {

    @Test
    void shouldReturnProperAuthLoginToString() {
        assertThat(SmtpCommand.AUTH_LOGIN.toString()).hasToString("auth login");
    }

    @Test
    void shouldReturnAuthLoginName() {
        assertThat(SmtpCommand.AUTH_LOGIN.getName()).hasToString("auth login");
    }

    @Test
    void shouldReturnProperAuthPlainToString() {
        assertThat(SmtpCommand.AUTH_PLAIN.toString()).hasToString("auth plain");
    }

    @Test
    void shouldReturnAuthPlainName() {
        assertThat(SmtpCommand.AUTH_PLAIN.getName()).hasToString("auth plain");
    }

    @Test
    void shouldReturnProperDataToString() {
        assertThat(SmtpCommand.DATA.toString()).hasToString("data");
    }

    @Test
    void shouldReturnDataName() {
        assertThat(SmtpCommand.DATA.getName()).hasToString("data");
    }

    @Test
    void shouldReturnProperHelloToString() {
        assertThat(SmtpCommand.HELO.toString()).hasToString("helo");
    }

    @Test
    void shouldReturnHelloName() {
        assertThat(SmtpCommand.HELO.getName()).hasToString("hello");
    }

    @Test
    void shouldReturnProperEhloToString() {
        assertThat(SmtpCommand.EHLO.toString()).hasToString("ehlo");
    }

    @Test
    void shouldReturnEhloName() {
        assertThat(SmtpCommand.EHLO.getName()).hasToString("extended hello");
    }

    @Test
    void shouldReturnProperMailToString() {
        assertThat(SmtpCommand.MAIL.toString()).hasToString("mail from:");
    }

    @Test
    void shouldReturnMailName() {
        assertThat(SmtpCommand.MAIL.getName()).hasToString("mail");
    }

    @Test
    void shouldReturnProperRcptToString() {
        assertThat(SmtpCommand.RCPT.toString()).hasToString("rcpt to:");
    }

    @Test
    void shouldReturnRcptName() {
        assertThat(SmtpCommand.RCPT.getName()).hasToString("recipient");
    }

    @Test
    void shouldReturnProperQuitToString() {
        assertThat(SmtpCommand.QUIT.toString()).hasToString("quit");
    }

    @Test
    void shouldReturnQuitName() {
        assertThat(SmtpCommand.QUIT.getName()).hasToString("quit");
    }

    @Test
    void shouldReturnProperRsetToString() {
        assertThat(SmtpCommand.RSET.toString()).hasToString("rset");
    }

    @Test
    void shouldReturnRsetName() {
        assertThat(SmtpCommand.RSET.getName()).hasToString("reset");
    }

    @Test
    void shouldReturnProperVrfyToString() {
        assertThat(SmtpCommand.VRFY.toString()).hasToString("vrfy");
    }

    @Test
    void shouldReturnVrfyName() {
        assertThat(SmtpCommand.VRFY.getName()).hasToString("verify");
    }

    @Test
    void shouldReturnProperExpnToString() {
        assertThat(SmtpCommand.EXPN.toString()).hasToString("expn");
    }

    @Test
    void shouldReturnExpnName() {
        assertThat(SmtpCommand.EXPN.getName()).hasToString("expand");
    }

    @Test
    void shouldReturnProperHelpToString() {
        assertThat(SmtpCommand.HELP.toString()).hasToString("help");
    }

    @Test
    void shouldReturnHelpName() {
        assertThat(SmtpCommand.HELP.getName()).hasToString("help");
    }

    @Test
    void shouldReturnProperNoopToString() {
        assertThat(SmtpCommand.NOOP.toString()).hasToString("noop");
    }

    @Test
    void shouldReturnNoopName() {
        assertThat(SmtpCommand.NOOP.getName()).hasToString("noop");
    }


}