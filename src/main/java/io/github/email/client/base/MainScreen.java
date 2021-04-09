package io.github.email.client.base;

import io.github.email.client.dialogs.SendDialog;
import io.github.email.client.dialogs.SettingsDialog;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.EmailReceiver;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import java.awt.Dimension;
import java.util.List;

public class MainScreen extends JFrame {
	private final ConfigService configUtil = new ConfigService();
	private final JMenuBar menuBar = new JMenuBar();
	private final JButton sendButton = new JButton("Send email");
	private final JButton settingsButton = new JButton("Settings");

	public MainScreen() {
		super("Simple email client");
		setupMenu();
		setSize(600, 400);
		setLocationRelativeTo(null);	// center on screen
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
		setLocationRelativeTo(null);	// center on screen

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
		String protocol = "imap";
		String host = "imap.gmail.com";
		String port = "993";
		String userName = "aghproject2020@gmail.com";
		String password = "LirykaFunika2020";
		EmailReceiver receiver = new EmailReceiver();
		return receiver.downloadEmails(protocol, host, port, userName, password);
	}
}