package io.github.email.client.base;

import io.github.email.client.dialogs.SendDialog;
import io.github.email.client.dialogs.SettingsDialog;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.EmailReceiver;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Properties;

public class MainScreen extends JFrame {
    private final ConfigService configUtil = new ConfigService();
    private final JMenuBar menuBar = new JMenuBar();
    private final JButton sendButton = new JButton("Send email");
    private final JButton settingsButton = new JButton("Settings");

    public MainScreen() {
        super("Simple email client");
        setupMenu();
        setSize(600, 400);
        setLocationRelativeTo(null);    // center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setupMenu() {
        sendButton.addActionListener(event -> {
            SendDialog dialog = new SendDialog(MainScreen.this, configUtil);
            dialog.setVisible(true);
        });
        settingsButton.addActionListener(event -> {
            SettingsDialog dialog = new SettingsDialog(MainScreen.this, configUtil);
            dialog.setVisible(true);
        });
        menuBar.add(prepareDownloadButton());
        menuBar.add(sendButton);
        menuBar.add(settingsButton);
        setJMenuBar(menuBar);
    }

    private JScrollPane prepareInboxTable() {
        String[] columnNames = {"From",
                "To",
                "CC",
                "Subject",
                "Date"};
        setSize(600, 400);
        setLocationRelativeTo(null);    // center on screen

        Object[][] data = prepareMessages();
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        return new JScrollPane(table);
    }

    private JButton prepareDownloadButton() {
        JButton downloadButton = new JButton("Download emails");
        downloadButton.addActionListener(e -> new SwingWorker<String, String>() {
            @Override
            protected String doInBackground() {
                publish("Updated");
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                JScrollPane inboxTable = prepareInboxTable();
                add(inboxTable);
                MainScreen.this.setVisible(true);
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(MainScreen.this, "Finished downloading");
            }
        }.execute());
        return downloadButton;
    }

    private String[][] prepareMessages() {
        Properties properties = configUtil.getProperties();

        String protocol = properties.getProperty("mail.imap", "imap");
        String host = properties.getProperty("mail.imap.host");
        String port = properties.getProperty("mail.imap.port", "993");
        String userName = properties.getProperty("mail.user");
        String password = properties.getProperty("mail.password");

        EmailReceiver receiver = new EmailReceiver();
        return receiver.downloadEmails(protocol, host, port, userName, password);
    }
}