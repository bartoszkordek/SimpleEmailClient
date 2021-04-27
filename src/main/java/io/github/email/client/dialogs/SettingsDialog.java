package io.github.email.client.dialogs;

import io.github.email.client.service.ConfigService;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;

public class SettingsDialog extends JDialog {

	private final ConfigService configUtil;
	
	private final JLabel labelSmtpHost = new JLabel("SMTP host: ");
	private final JLabel labelImapHost = new JLabel("IMAP host: ");
	private final JLabel labelSmtpPort = new JLabel("SMTP port: ");
	private final JLabel labelImapPort = new JLabel("IMAP port: ");
	private final JLabel labelUser = new JLabel("Username: ");
	private final JLabel labelPass = new JLabel("Password: ");
	
	private final JTextField textSmtpHost = new JTextField(20);
	private final JTextField textImapHost = new JTextField(20);
	private final JTextField textSmtpPort = new JTextField(20);
	private final JTextField textImapPort = new JTextField(20);
	private final JTextField textUser = new JTextField(20);
	private final JTextField textPass = new JTextField(20);

	private final JButton buttonSave = new JButton("Save");
	
	public SettingsDialog(JFrame parent, ConfigService configUtil) {
		super(parent, "Email settings", true);
		this.configUtil = configUtil;
		setupForm();
		loadSettings();
		pack();
		setLocationRelativeTo(null);
	}
	
	private void setupForm() {
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 5, 10);
		constraints.anchor = GridBagConstraints.WEST;

		addComponent(labelSmtpHost, constraints, 0, 0);
		addComponent(textSmtpHost, constraints, 1, 0);
		addComponent(labelSmtpPort, constraints, 0, 1);
		addComponent(textSmtpPort, constraints, 1, 1);

		addComponent(labelImapHost, constraints, 0, 2);
		addComponent(textImapHost, constraints, 1, 2);
		addComponent(labelImapPort, constraints, 0, 3);
		addComponent(textImapPort, constraints, 1, 3);

		addComponent(labelUser, constraints, 0, 4);
		addComponent(textUser, constraints, 1, 4);
		addComponent(labelPass, constraints, 0, 5);
		addComponent(textPass, constraints, 1, 5);
		
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		add(buttonSave, constraints);
		
		buttonSave.addActionListener(this::buttonSaveActionPerformed);
	}

	private void addComponent(JComponent component, GridBagConstraints constraints, int x, int y) {
		constraints.gridx = x;
		constraints.gridy = y;
		add(component, constraints);
	}
	
	private void loadSettings() {
		Properties configProps = configUtil.getProperties();
		textSmtpHost.setText(configProps.getProperty("mail.smtp.host"));
		textSmtpPort.setText(configProps.getProperty("mail.smtp.port"));
		textImapHost.setText(configProps.getProperty("mail.imap.host"));
		textImapPort.setText(configProps.getProperty("mail.imap.port"));
		textUser.setText(configProps.getProperty("mail.user"));
		textPass.setText(configProps.getProperty("mail.password"));		
	}
	
	private void buttonSaveActionPerformed(ActionEvent event) {
		try {
			configUtil.saveProperties(
					textSmtpHost.getText(),
					textImapHost.getText(),
					textSmtpPort.getText(),
					textImapPort.getText(),
					textUser.getText(),
					textPass.getText());
			JOptionPane.showMessageDialog(SettingsDialog.this, 
					"Settings were saved successfully.");
			dispose();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, 
					"Error saving settings: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}		
	}
}