package io.github.email.client.dialogs;

import io.github.email.client.base.FileChooser;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.EmailService;

import javax.swing.JButton;
import javax.swing.JComponent;
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
    private final JLabel labelTo = new JLabel("To:");
    private final JLabel labelCc = new JLabel("CC:");
    private final JLabel labelSubject = new JLabel("Subject:");
    private final JTextField fieldTo = new JTextField(30);
    private final JTextField fieldCc = new JTextField(30);
    private final JTextField fieldSubject = new JTextField(30);
    private final JButton buttonSend = new JButton("Send");
    private final FileChooser fileChooser = new FileChooser("Attached", "Attach");
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
        addComponent(labelTo, 0, 0);
        addComponent(fieldTo, 1, 0);
        addComponent(labelCc, 0, 1);
        addComponent(fieldCc, 1, 1);
        addComponent(labelSubject, 0, 2);
        addComponent(fieldSubject, 1, 2);
        addComponent(buttonSend, 2, 0);
        buttonSend.addActionListener(event -> buttonSendActionPerformed());
        addComponent(fileChooser, 2, 2);
        addComponent(new JScrollPane(textAreaMessage), 1, 3);
    }

    private void addComponent(JComponent component, int x, int y) {
        constraints.gridx = x;
        constraints.gridy = y;
        add(component, constraints);
    }

    private void buttonSendActionPerformed() {
        if (!validateFields()) {
            return;
        }

        String[] toAddresses = fieldTo.getText().split(",");
        String[] ccAddresses = fieldCc.getText().split(",");
        String subject = fieldSubject.getText();
        String message = textAreaMessage.getText();

        File[] attachFiles = null;

        if (!fileChooser.getSelectedFilePath().equals("")) {
            File selectedFile = new File(fileChooser.getSelectedFilePath());
            attachFiles = new File[] {selectedFile};
        }

        try {
            Properties smtpProperties = configUtil.getProperties();
            EmailService.sendEmail(smtpProperties, toAddresses, ccAddresses, subject, message, attachFiles);

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
