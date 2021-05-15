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

class SmtpSSLCommandSenderTest {

    private SmtpSSLCommandSender smtpSSLCommandSender;
    private PrintWriter writer;
    private BufferedReader reader;
    private PropertiesLoader properties;

    @BeforeEach
    void setUp() {
        writer = mock(PrintWriter.class);
        reader = mock(BufferedReader.class);
        properties = mock(PropertiesLoaderImpl.class);
        smtpSSLCommandSender = new SmtpSSLCommandSenderImpl(writer, reader, properties);
    }

    @Nested
    class WhenConnectionEstablished {

        @Test
        void shouldReturnResponse220WhenConnectionEstablished() throws IOException {
            when(reader.readLine()).thenReturn("220 connection established");
            assertThat(smtpSSLCommandSender.connectionEstablished()).startsWith("220");
        }

        @Test
        void shouldReturnThrowExceptionWhenNoConnectionEstablished() throws IOException {
            when(reader.readLine()).thenReturn("554 no connection established");
            assertThatThrownBy(
                    () -> smtpSSLCommandSender.connectionEstablished()
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
            assertThat(smtpSSLCommandSender.sendHELOCommand()).startsWith("250");
        }

        @Test
        void shouldReturnThrowExceptionWhen504HeloResponse() throws IOException {
            when(reader.readLine()).thenReturn("504 error helo response");
            assertThatThrownBy(
                    () -> smtpSSLCommandSender.sendHELOCommand()
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending HELO command error occurred.");
        }

        @Test
        void shouldReturnThrowExceptionWhen550HeloResponse() throws IOException {
            when(reader.readLine()).thenReturn("550 error helo response");
            assertThatThrownBy(
                    () -> smtpSSLCommandSender.sendHELOCommand()
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
            assertThat(smtpSSLCommandSender.sendEHLOCommand()).startsWith("250");
        }

        @Test
        void shouldReturnThrowExceptionWhen504EhloResponse() throws IOException {
            when(reader.readLine()).thenReturn("504 error ehlo response");
            assertThatThrownBy(
                    () -> smtpSSLCommandSender.sendEHLOCommand()
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending EHLO command error occurred.");
        }

        @Test
        void shouldReturnThrowExceptionWhen550EhloResponse() throws IOException {
            when(reader.readLine()).thenReturn("550 error ehlo response");
            assertThatThrownBy(
                    () -> smtpSSLCommandSender.sendEHLOCommand()
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending EHLO command error occurred.");
        }
    }

    @Nested
    class WhenSendAuthCommands {

    }

    @Nested
    class WhenSendMailFromCommand {
        @Test
        void shouldReturnResponse250WhenServerRespondToMailCmd() throws IOException {
            when(properties.getUser()).thenReturn("jan.kowalski@gmail.com");
            when(reader.readLine()).thenReturn("250 mail response");
            assertThat(smtpSSLCommandSender.sendMailFromCommand()).startsWith("250");
        }

        @ParameterizedTest
        @ValueSource(ints = {552, 451, 452, 550, 553, 503})
        void shouldReturnThrowExceptionWhenMailResponseWithStatus(int responseStatus) throws IOException {
            when(reader.readLine()).thenReturn(responseStatus + " error mail response");
            assertThatThrownBy(
                    () -> smtpSSLCommandSender.sendMailFromCommand()
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending MAIL FROM: command error occurred.");
        }
    }

    @Nested
    class WhenSendRcptToCommand {

        private String[] recipients;

        @BeforeEach
        void setUp() {
            recipients = new String[]{"aghproject2020@gmail.com", "aghproject2021@gmail.com"};
        }

        @ParameterizedTest
        @ValueSource(ints = {250, 251})
        void shouldReturnResponse250WhenServerRespondToRcptCmd(int responseStatus) throws IOException {
            when(properties.getUser()).thenReturn("jan.kowalski@gmail.com");
            when(reader.readLine()).thenReturn(responseStatus + " rcpt response");
            assertThat(smtpSSLCommandSender.sendRcptToCommand(recipients))
                    .startsWith(String.valueOf(responseStatus));
        }

        @ParameterizedTest
        @ValueSource(ints = {550, 551, 552, 553, 450, 451, 452, 503, 550})
        void shouldReturnThrowExceptionWhenMailResponseWithStatus(int responseStatus) throws IOException {
            when(reader.readLine()).thenReturn(responseStatus + " error mail response");
            assertThatThrownBy(
                    () -> smtpSSLCommandSender.sendRcptToCommand(recipients)
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending RCPT TO: command error occurred.");
        }
    }

    @Nested
    class WhenSendDataCommand {

    }

    @Nested
    class WhenSendQuitCommand {
        @Test
        void shouldReturnResponse221WhenServerRespondToQuitCmd() throws IOException {
            when(reader.readLine()).thenReturn("221 quit response");
            assertThat(smtpSSLCommandSender.sendQuitCommand()).startsWith("221");
        }

        @Test
        void shouldReturnThrowExceptionWhen500QuitCmd() throws IOException {
            when(reader.readLine()).thenReturn("500 error quit response");
            assertThatThrownBy(
                    () -> smtpSSLCommandSender.sendQuitCommand()
            ).isInstanceOf(ConnectException.class)
                    .hasMessage("While sending QUIT command error occurred.");
        }
    }
}