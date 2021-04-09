package io.github.email.client.mail;

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
	
	public void saveProperties(String host, String port, String user, String pass) throws IOException {
		setProperties(host, port, user, pass);
		OutputStream outputStream = new FileOutputStream(configFile);
		properties.store(outputStream, "Email settings");
		outputStream.close();
	}

	private void setDefaultProperties() {
		setProperties("smtp.gmail.com", "587", "mail@gmail.com", "pass");
	}

	private void setProperties(String host, String port, String user, String pass) {
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port);
		properties.setProperty("mail.user", user);
		properties.setProperty("mail.password", pass);
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");
	}
}