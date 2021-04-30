package io.github.email.client.base;

import io.github.email.client.dialogs.SendDialog;
import io.github.email.client.dialogs.SettingsDialog;
import io.github.email.client.imap.MailMetadata;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.EmailApi;
import io.github.email.client.service.LowLevelEmailApi;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import java.awt.Dimension;
import java.util.List;
import java.util.Properties;

public class MainScreen extends JFrame {
    private final ConfigService configUtil = new ConfigService();
    private final EmailApi emailApi = new LowLevelEmailApi();
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
            SendDialog dialog = new SendDialog(MainScreen.this, emailApi, configUtil);
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
                "BCC",
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
		Properties configProperties = configUtil.getProperties();
        List<MailMetadata> metadatas = emailApi.downloadEmails(configProperties, 10);
        String[][] converted = new String[metadatas.size()][6];
        for (int i = 0; i < converted.length; i++) {
            MailMetadata metadata = metadatas.get(i);
            converted[i][0] = metadata.getFrom();
            converted[i][1] = metadata.getTo();
            converted[i][2] = metadata.getCc();
            converted[i][3] = metadata.getBcc();
            converted[i][4] = metadata.getSubject();
            converted[i][5] = metadata.getDate();
        }
        return converted;
    }

}