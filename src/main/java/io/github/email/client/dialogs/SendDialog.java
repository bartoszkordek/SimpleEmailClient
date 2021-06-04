package io.github.email.client.dialogs;

import io.github.email.client.base.FileChooser;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.SendApi;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Properties;

public class SendDialog extends JDialog {
    private final JLabel labelTo = new JLabel("To:");
    private final JLabel labelCc = new JLabel("CC:");
    private final JLabel labelBcc = new JLabel("BCC:");
    private final JLabel labelSubject = new JLabel("Subject:");
    private final JTextField fieldTo = new JTextField(30);
    private final JTextField fieldCc = new JTextField(30);
    private final JTextField fieldBcc = new JTextField(30);
    private final JTextField fieldSubject = new JTextField(30);
    private final JButton buttonSend = new JButton("Send");
    private final FileChooser fileChooser = new FileChooser("Attached:", "Attach");
    private final JTextArea textAreaMessage = new JTextArea(10, 30);
    private final GridBagConstraints constraints = new GridBagConstraints();
    private final ConfigService configUtil;
    private final SendApi sendApi;

    public SendDialog(JFrame parent, SendApi sendApi, ConfigService configUtil) {
        super(parent, "Send email", true);
        this.configUtil = configUtil;
        this.sendApi = sendApi;
        setLayout(new GridBagLayout());
        setupForm();
        pack();
        setLocationRelativeTo(null);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
    }

    private void setupForm() {
        addComponent(labelTo, 0, 0);
        addComponent(labelTo, 0, 0);
        addComponent(fieldTo, 1, 0);
        addComponent(labelCc, 0, 1);
        addComponent(fieldCc, 1, 1);
        addComponent(labelBcc, 0, 2);
        addComponent(fieldBcc, 1, 2);
        addComponent(labelSubject, 0, 3);
        addComponent(fieldSubject, 1, 3);
        addComponent(buttonSend, 2, 0);
        constraints.gridwidth = 3;
        addComponent(fileChooser, 0, 4);
        buttonSend.addActionListener(event -> buttonSendActionPerformed());
        addComponent(new JScrollPane(textAreaMessage), 0, 5);
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
        String[] ccAddresses = new String[0];
        if (!fieldCc.getText().equals("")) {
            ccAddresses = fieldCc.getText().split(",");
        }
        String[] bccAddresses = new String[0];
        if (!fieldBcc.getText().equals("")) {
            bccAddresses = fieldBcc.getText().split(",");
        }
        String subject = fieldSubject.getText();
        String message = textAreaMessage.getText();
        File[] attachFiles = new File[0];
        if (!fileChooser.getSelectedFilePaths().equals("")) {
            attachFiles = Arrays.stream(fileChooser.getSelectedFilePaths().split(","))
                    .map(File::new)
                    .toArray(File[]::new);
        }

        try {
            Properties configProperties = configUtil.getProperties();
            sendApi.sendEmail(configProperties, toAddresses, ccAddresses, bccAddresses, subject, message, attachFiles, null);

            JOptionPane.showMessageDialog(this,
                    "The e-mail has been sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error while sending the e-mail: " + e.getMessage(),
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
