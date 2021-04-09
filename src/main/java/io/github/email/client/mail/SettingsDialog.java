package io.github.email.client.mail;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;

public class SettingsDialog extends JDialog {

	private final ConfigService configUtil;
	
	private final JLabel labelHost = new JLabel("Host name: ");
	private final JLabel labelPort = new JLabel("Port number: ");
	private final JLabel labelUser = new JLabel("Username: ");
	private final JLabel labelPass = new JLabel("Password: ");
	
	private final JTextField textHost = new JTextField(20);
	private final JTextField textPort = new JTextField(20);
	private final JTextField textUser = new JTextField(20);
	private final JTextField textPass = new JTextField(20);
	
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
		
		add(labelHost, constraints);
		
		constraints.gridx = 1;
		add(textHost, constraints);
		
		constraints.gridy = 1;
		constraints.gridx = 0;
		add(labelPort, constraints);
		
		constraints.gridx = 1;
		add(textPort, constraints);

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
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		add(buttonSave, constraints);
		
		buttonSave.addActionListener(this::buttonSaveActionPerformed);
	}
	
	private void loadSettings() {
		Properties configProps = configUtil.getProperties();
		textHost.setText(configProps.getProperty("mail.smtp.host"));
		textPort.setText(configProps.getProperty("mail.smtp.port"));
		textUser.setText(configProps.getProperty("mail.user"));
		textPass.setText(configProps.getProperty("mail.password"));		
	}
	
	private void buttonSaveActionPerformed(ActionEvent event) {
		try {
			configUtil.saveProperties(textHost.getText(),
					textPort.getText(),
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