package io.github.email.client.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigService {
	private final File configFile = new File("smtp.properties");
	private final Properties properties = new Properties();

	public ConfigService()  {
		if (configFile.exists()) {
			try (InputStream inputStream = new FileInputStream(configFile)) {
				properties.load(inputStream);
			} catch (IOException e) {
				throw new IllegalStateException("Cannot load properties from file");
			}
		} else {
			setDefaultProperties();
		}
	}

	public Properties getProperties() {
		return properties;
	}
	
	public void saveProperties(
			String smtpHost,
			String smtpPort,
			String user,
			String pass,
			String imapHost,
			String imapPort
	) throws IOException {
		setProperties(
				smtpHost,
				smtpPort,
				user,
				pass,
				imapHost,
				imapPort
		);
		OutputStream outputStream = new FileOutputStream(configFile);
		properties.store(outputStream, "Email settings");
		outputStream.close();
	}

	private void setDefaultProperties() {
		setProperties(
				"smtp.gmail.com",
				"587",
				"mail@gmail.com",
				"pass",
				"imap.gmail.com",
				"993"
		);
	}

	private void setProperties(
			String smtpHost,
			String smtpPort,
			String user,
			String pass,
			String imapHost,
			String imapPort
	) {
		properties.setProperty("mail.smtp.host", smtpHost);
		properties.setProperty("mail.smtp.port", smtpPort);
		properties.setProperty("mail.user", user);
		properties.setProperty("mail.password", pass);
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.setProperty("mail.imap","imap");
		properties.setProperty("mail.imap.host",imapHost);
		properties.setProperty("mail.imap.port",imapPort);
	}
}