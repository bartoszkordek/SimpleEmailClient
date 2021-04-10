package io.github.email.client.dialogs;

import io.github.email.client.service.ConfigService;

import javax.swing.JButton;
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
	
	private final JLabel labelSmptHost = new JLabel("Smpt Host name: ");
	private final JLabel labelSmptPort = new JLabel("Smpt Port number: ");
	private final JLabel labelUser = new JLabel("Username: ");
	private final JLabel labelPass = new JLabel("Password: ");
	private final JLabel labelImapHost = new JLabel("Imap Host name: ");
	private final JLabel labelImapPort = new JLabel("Imap Port number: ");
	
	private final JTextField textSmptHost = new JTextField(20);
	private final JTextField textSmptPort = new JTextField(20);
	private final JTextField textUser = new JTextField(20);
	private final JTextField textPass = new JTextField(20);
	private final JTextField textImapHost=new JTextField(20);
	private final JTextField textImapPort=new JTextField(20);
	
	private final JButton buttonSave = new JButton("Save");
	
	public SettingsDialog(JFrame parent, ConfigService configUtil) {
		super(parent, "SMTP settings", true);
		this.configUtil = configUtil;
		setupForm();
		loadSettings();
		pack();
		setLocationRelativeTo(null);
	}
	
	private void setupForm() {
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(10, 10, 5, 10);
		constraints.anchor = GridBagConstraints.WEST;
		
		add(labelSmptHost, constraints);
		
		constraints.gridx = 1;
		add(textSmptHost, constraints);
		
		constraints.gridy = 1;
		constraints.gridx = 0;
		add(labelSmptPort, constraints);
		
		constraints.gridx = 1;
		add(textSmptPort, constraints);

		constraints.gridy = 2;
		constraints.gridx = 0;
		add(labelUser, constraints);
		
		constraints.gridx = 1;
		add(textUser, constraints);

		constraints.gridy = 3;
		constraints.gridx = 0;
		add(labelPass, constraints);
		
		constraints.gridx = 1;
		add(textPass, constraints);

		constraints.gridy = 4;
		constraints.gridx = 0;
		add(labelImapHost, constraints);

		constraints.gridx = 1;
		add(textImapHost, constraints);

		constraints.gridy = 5;
		constraints.gridx = 0;
		add(labelImapPort, constraints);

		constraints.gridx = 1;
		add(textImapPort, constraints);
		
		constraints.gridy = 6;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		add(buttonSave, constraints);
		
		buttonSave.addActionListener(this::buttonSaveActionPerformed);
	}
	
	private void loadSettings() {
		Properties configProps = configUtil.getProperties();
		textSmptHost.setText(configProps.getProperty("mail.smtp.host"));
		textSmptPort.setText(configProps.getProperty("mail.smtp.port"));
		textUser.setText(configProps.getProperty("mail.user"));
		textPass.setText(configProps.getProperty("mail.password"));
		textImapHost.setText(configProps.getProperty("mail.imap.host"));
		textImapPort.setText(configProps.getProperty("mail.imap.port"));
	}
	
	private void buttonSaveActionPerformed(ActionEvent event) {
		try {
			configUtil.saveProperties(
					textSmptHost.getText(),
					textSmptPort.getText(),
					textUser.getText(),
					textPass.getText(),
					textImapHost.getText(),
					textImapPort.getText()
			);
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