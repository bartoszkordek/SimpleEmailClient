package io.github.email.client.dialogs;

import io.github.email.client.base.FileChooser;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.EmailService;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.Properties;

public class SendDialog extends JDialog {
    private final JLabel labelTo = new JLabel("To: ");
    private final JLabel labelSubject = new JLabel("Subject: ");
    private final JTextField fieldTo = new JTextField(30);
    private final JTextField fieldSubject = new JTextField(30);
    private final JButton buttonSend = new JButton("Send");
    private final FileChooser fileChooser = new FileChooser("Attached", "Attach File...");
    private final JTextArea textAreaMessage = new JTextArea(10, 30);
    private final GridBagConstraints constraints = new GridBagConstraints();
    private final ConfigService configUtil;

    public SendDialog(JFrame parent, ConfigService configUtil) {
        super(parent, "Send email", true);
        this.configUtil = configUtil;
        setLayout(new GridBagLayout());
        setupForm();
        pack();
        setLocationRelativeTo(null);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
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
                    "The e-mail has been sent successfully");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error while sending the e-mail: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateFields() {
        if (fieldTo.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter email address of recipient",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fieldTo.requestFocus();
            return false;
        }

        if (fieldSubject.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter subject",
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
}
