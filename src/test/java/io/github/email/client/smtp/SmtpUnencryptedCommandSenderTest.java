package io.github.email.client.smtp;

import io.github.email.client.util.PropertiesLoader;
import io.github.email.client.util.PropertiesLoaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SmtpUnencryptedCommandSenderTest {

    private SmtpUnencryptedCommandSender smtpUnencryptedCommandSender;
    private BufferedReader reader;
    private PropertiesLoader properties;

    @BeforeEach
    void setUp() {
        PrintWriter writer = mock(PrintWriter.class);
        reader = mock(BufferedReader.class);
        properties = mock(PropertiesLoaderImpl.class);
        smtpUnencryptedCommandSender = new SmtpUnencryptedCommandSenderImpl(writer, reader, properties);
    }

    @Nested
    class WhenConnectionEstablished {

        @Test
        void shouldReturnResponse220WhenConnectionEstablished() throws IOException {
            when(reader.readLine()).thenReturn("220 connection established");
            assertThat(smtpUnencryptedCommandSender.connectionEstablished()).startsWith("220");
        }

        @Test
        void shouldReturnThrowExceptionWhenNoConnectionEstablished() throws IOException {
            when(reader.readLine()).thenReturn("554 no connection established");
            assertThatThrownBy(
                    () -> smtpUnencryptedCommandSender.connectionEstablished()
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending HELO command error occurred.");
        }
    }

    @Nested
    class WhenSendHelloCommand {
        @Test
        void shouldReturnResponse250WhenServerRespondToHelloCmd() throws IOException {
            when(properties.getSmtpHost()).thenReturn("smtp.gmail.com");
            when(reader.readLine()).thenReturn("250 helo response");
            assertThat(smtpUnencryptedCommandSender.sendHELOCommand()).startsWith("250");
        }

        @Test
        void shouldReturnThrowExceptionWhen504HeloResponse() throws IOException {
            when(reader.readLine()).thenReturn("504 error helo response");
            assertThatThrownBy(
                    () -> smtpUnencryptedCommandSender.sendHELOCommand()
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending HELO command error occurred.");
        }

        @Test
        void shouldReturnThrowExceptionWhen550HeloResponse() throws IOException {
            when(reader.readLine()).thenReturn("550 error helo response");
            assertThatThrownBy(
                    () -> smtpUnencryptedCommandSender.sendHELOCommand()
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending HELO command error occurred.");
        }
    }

    @Nested
    class WhenSendEHLOCommand {

        @Test
        void shouldReturnResponse250WhenServerRespondToEhloCmd() throws IOException {
            when(properties.getSmtpHost()).thenReturn("smtp.gmail.com");
            when(reader.readLine()).thenReturn("250 elho response");
            assertThat(smtpUnencryptedCommandSender.sendEHLOCommand()).startsWith("250");
        }

        @Test
        void shouldReturnThrowExceptionWhen504EhloResponse() throws IOException {
            when(reader.readLine()).thenReturn("504 error ehlo response");
            assertThatThrownBy(
                    () -> smtpUnencryptedCommandSender.sendEHLOCommand()
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending EHLO command error occurred.");
        }

        @Test
        void shouldReturnThrowExceptionWhen550EhloResponse() throws IOException {
            when(reader.readLine()).thenReturn("550 error ehlo response");
            assertThatThrownBy(
                    () -> smtpUnencryptedCommandSender.sendEHLOCommand()
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending EHLO command error occurred.");
        }
    }
}