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
}