package io.github.email.client.smtp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SmtpStateTest {

    @Test
    void shouldReturnProperAuthLoginToString() {
        assertThat(SmtpState.AUTH_LOGIN.toString()).hasToString("auth login");
    }

    @Test
    void shouldReturnProperAuthPlainToString() {
        assertThat(SmtpState.AUTH_PLAIN.toString()).hasToString("auth plain");
    }

    @Test
    void shouldReturnProperDataToString() {
        assertThat(SmtpState.DATA.toString()).hasToString("data");
    }

    @Test
    void shouldReturnProperInitToString() {
        assertThat(SmtpState.INIT.toString()).hasToString("init");
    }

    @Test
    void shouldReturnProperHelloToString() {
        assertThat(SmtpState.HELO.toString()).hasToString("helo");
    }

    @Test
    void shouldReturnProperEhloToString() {
        assertThat(SmtpState.EHLO.toString()).hasToString("ehlo");
    }

    @Test
    void shouldReturnProperMailToString() {
        assertThat(SmtpState.MAIL_FROM.toString()).hasToString("mail from");
    }

    @Test
    void shouldReturnProperRcptToString() {
        assertThat(SmtpState.RCPT_TO.toString()).hasToString("rcpt to");
    }

    @Test
    void shouldReturnProperQuitToString() {
        assertThat(SmtpState.QUIT.toString()).hasToString("quit");
    }

}