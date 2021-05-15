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
    private BufferedReader reader;
    private PropertiesLoader properties;

    @BeforeEach
    void setUp() {
        PrintWriter writer = mock(PrintWriter.class);
        reader = mock(BufferedReader.class);
        properties = mock(PropertiesLoaderImpl.class);
        smtpSSLCommandSender = new SmtpSSLCommandSenderImpl(writer, reader, properties);
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