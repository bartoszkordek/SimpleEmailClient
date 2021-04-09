package io.github.email.client.mail;

import io.github.email.client.EmailReceiver;
import io.github.email.client.FileChooser;

import javax.swing.*;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.Properties;

public class SwingEmailSender extends JFrame {
	private final ConfigService configUtil = new ConfigService();
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menuFile = new JMenu("Settings");
	private final JMenuItem menuItemSetting = new JMenuItem("Settings..");
	private final JLabel labelTo = new JLabel("To: ");
	private final JLabel labelSubject = new JLabel("Subject: ");
	private final JTextField fieldTo = new JTextField(30);
	private final JTextField fieldSubject = new JTextField(30);
	private final JButton buttonSend = new JButton("SEND");
	private final FileChooser fileChooser = new FileChooser("Attached", "Attach File...");
	private final JTextArea textAreaMessage = new JTextArea(10, 30);
	private final GridBagConstraints constraints = new GridBagConstraints();
	
	public SwingEmailSender() {
		super("Simple email client");
		
		// set up layout
		setLayout(new GridBagLayout());
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(5, 5, 5, 5);
	
		setupMenu();
		setupForm();
		
		pack();
		setLocationRelativeTo(null);	// center on screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void setupMenu() {
		menuItemSetting.addActionListener(event -> {
			SettingsDialog dialog = new SettingsDialog(SwingEmailSender.this, configUtil);
			dialog.setVisible(true);
		});
		
		menuFile.add(menuItemSetting);
		menuBar.add(menuFile);
		setJMenuBar(menuBar);		
	}
	
	private void setupForm() {
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(labelTo, constraints);
		
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(fieldTo, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(labelSubject, constraints);
		
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(fieldSubject, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
		buttonSend.setFont(new Font("Arial", Font.BOLD, 16));
		add(buttonSend, constraints);
		
		buttonSend.addActionListener(event -> buttonSendActionPerformed());
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		add(fileChooser, constraints);
		
		constraints.gridy = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		
		add(new JScrollPane(textAreaMessage), constraints);		
	}
	
	private void buttonSendActionPerformed() {
		if (!validateFields()) {
			return;
		}
		
		String toAddress = fieldTo.getText();
		String subject = fieldSubject.getText();
		String message = textAreaMessage.getText();
		
		File[] attachFiles = null;
		
		if (!fileChooser.getSelectedFilePath().equals("")) {
			File selectedFile = new File(fileChooser.getSelectedFilePath());
			attachFiles = new File[] {selectedFile};
		}
		
		try {
			Properties smtpProperties = configUtil.getProperties();
			EmailService.sendEmail(smtpProperties, toAddress, subject, message, attachFiles);
			
			JOptionPane.showMessageDialog(this, 
					"The e-mail has been sent successfully!");
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, 
					"Error while sending the e-mail: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean validateFields() {
		if (fieldTo.getText().equals("")) {
			JOptionPane.showMessageDialog(this, 
					"Please enter To address!",
					"Error", JOptionPane.ERROR_MESSAGE);
			fieldTo.requestFocus();
			return false;
		}
		
		if (fieldSubject.getText().equals("")) {
			JOptionPane.showMessageDialog(this, 
					"Please enter subject!",
					"Error", JOptionPane.ERROR_MESSAGE);
			fieldSubject.requestFocus();
			return false;
		}
		
		if (textAreaMessage.getText().equals("")) {
			JOptionPane.showMessageDialog(this, 
					"Please enter message!",
					"Error", JOptionPane.ERROR_MESSAGE);
			textAreaMessage.requestFocus();
			return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(() -> new SwingEmailSender().setVisible(true));
	}
}