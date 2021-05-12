package io.github.email.client.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PropertiesLoaderTest {

    private PropertiesLoader propertiesLoader;
    private Properties properties;

    @BeforeEach
    void setUp() {
        properties = new Properties();
        propertiesLoader = new PropertiesLoaderImpl(properties);
    }

    @Nested
    class WhenGetImapProtocolName {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.imap", "imapTest");
            assertThat(propertiesLoader.getImapProtocolName()).isEqualTo("imapTest");
        }

        @Test
        void shouldReturnDefaultValueIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getImapProtocolName()).isEqualTo("imap");
        }
    }

    @Nested
    class WhenGetImapAuth {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.imap.auth", "false");
            assertThat(propertiesLoader.getImapAuth()).isFalse();
        }

        @Test
        void shouldReturnDefaultValueIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getImapAuth()).isTrue();
        }
    }

    @Nested
    class WhenGetImapHost {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.imap.host", "test");
            assertThat(propertiesLoader.getImapHost()).isEqualTo("test");
        }

        @Test
        void shouldReturnNullIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getImapHost()).isNull();
        }
    }

    @Nested
    class WhenGetImapPort {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.imap.port", "555");
            assertThat(propertiesLoader.getImapPort()).isEqualTo(555);
        }

        @Test
        void shouldReturnNullIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getImapPort()).isEqualTo(993);
        }
    }

    @Nested
    class WhenGetImapSocketFactoryClass {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() throws ClassNotFoundException {
            properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            assertThat(propertiesLoader.getImapSocketFactoryClass()).isEqualTo(javax.net.ssl.SSLSocketFactory.class);
        }

        @Test
        void shouldReturnNullIfPropertyIsNotDefinedInPropertiesObject() {
            assertThatThrownBy(
                    () -> propertiesLoader.getImapSocketFactoryClass()
            ).isInstanceOf(ClassNotFoundException.class);
        }
    }

    @Nested
    class WhenGetImapSocketFactoryFallback {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.imap.socketFactory.fallback", "true");
            assertThat(propertiesLoader.getImapSocketFactoryFallback()).isTrue();
        }

        @Test
        void shouldReturnDefaultValueIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getImapSocketFactoryFallback()).isFalse();
        }
    }

    @Nested
    class WhenGetImapSocketFactoryPort {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.imap.socketFactory.port", "555");
            assertThat(propertiesLoader.getImapSocketFactoryPort()).isEqualTo(555);
        }

        @Test
        void shouldReturnNullIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getImapSocketFactoryPort()).isEqualTo(993);
        }
    }

    @Nested
    class WhenGetImapSslCheckServerIdentity {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.imap.ssl.checkserveridentity", "true");
            assertThat(propertiesLoader.getImapSslCheckServerIdentity()).isTrue();
        }

        @Test
        void shouldReturnDefaultValueIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getImapSslCheckServerIdentity()).isFalse();
        }
    }

    @Nested
    class WhenGetImapSslTrust {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.imap.ssl.trust", "test");
            assertThat(propertiesLoader.getImapSslTrust()).isEqualTo("test");
        }

        @Test
        void shouldReturnDefaultValueIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getImapSslTrust()).isEqualTo("*");
        }
    }

    @Nested
    class WhenGetPassword {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.password", "test");
            assertThat(propertiesLoader.getPassword()).isEqualTo("test");
        }

        @Test
        void shouldReturnNullIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getPassword()).isNull();
        }
    }

    @Nested
    class WhenGetSmtpProtocolName {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.smtp", "smtpTest");
            assertThat(propertiesLoader.getSmtpProtocolName()).isEqualTo("smtpTest");
        }

        @Test
        void shouldReturnDefaultValueIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getSmtpProtocolName()).isEqualTo("smtp");
        }
    }


    @Nested
    class WhenGetSmtpAuth {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.smtp.auth", "false");
            assertThat(propertiesLoader.getSmtpAuth()).isFalse();
        }

        @Test
        void shouldReturnDefaultValueIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getSmtpAuth()).isTrue();
        }
    }


    @Nested
    class WhenGetSmtpHost {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.smtp.host", "test");
            assertThat(propertiesLoader.getSmtpHost()).isEqualTo("test");
        }

        @Test
        void shouldReturnNullIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getSmtpHost()).isNull();
        }
    }

    @Nested
    class WhenGetSmtpPort {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.smtp.port", "555");
            assertThat(propertiesLoader.getSmtpPort()).isEqualTo(555);
        }

        @Test
        void shouldReturnNullIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getSmtpPort()).isEqualTo(587);
        }
    }

    @Nested
    class WhenGetSmtpSslTrust {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.smtp.ssl.trust", "test");
            assertThat(propertiesLoader.getSmtpSslTrust()).isEqualTo("test");
        }

        @Test
        void shouldReturnDefaultValueIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getSmtpSslTrust()).isEqualTo("*");
        }
    }

    @Nested
    class WhenGetSmtpStartTlsEnable{
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.smtp.starttls.enable", "false");
            assertThat(propertiesLoader.getSmtpStartTlsEnable()).isFalse();
        }

        @Test
        void shouldReturnDefaultValueIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getSmtpStartTlsEnable()).isTrue();
        }
    }

    @Nested
    class WhenGetUser {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.user", "test");
            assertThat(propertiesLoader.getUser()).isEqualTo("test");
        }

        @Test
        void shouldReturnNullIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getUser()).isNull();
        }
    }

    @Nested
    class WhenGetTransportProtocol {
        @Test
        void shouldReturnValueDefinedInPropertiesObject() {
            properties.setProperty("mail.transport.protocol", "test");
            assertThat(propertiesLoader.getTransportProtocol()).isEqualTo("test");
        }

        @Test
        void shouldReturnNullIfPropertyIsNotDefinedInPropertiesObject() {
            assertThat(propertiesLoader.getTransportProtocol()).isNull();
        }
    }

}