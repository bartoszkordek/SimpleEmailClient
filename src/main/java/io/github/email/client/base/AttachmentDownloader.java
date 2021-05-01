package io.github.email.client.base;

import io.github.email.client.imap.Attachment;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AttachmentDownloader extends JPanel {
	private static final int BUFFER_SIZE = 4096;
	private final JFileChooser fileChooser;

	public AttachmentDownloader(Attachment attachment) {
		super(new BorderLayout());
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		setLayout(new GridBagLayout());
		JButton downloadButton = new JButton(attachment.getFileName());
		downloadButton.addActionListener((e) -> downloadAttachment(attachment));
		add(downloadButton);
	}

	private void downloadAttachment(Attachment attachment) {
		if (fileChooser.showDialog(this, "Download") == JFileChooser.APPROVE_OPTION) {
			String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
			String fileName = attachment.getFileName();
			try (FileOutputStream outputStream = new FileOutputStream(selectedPath + "/" + fileName)) {
				InputStream inputStream = new ByteArrayInputStream(attachment.getContent());
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				JOptionPane.showMessageDialog(this,
						"File " + fileName + " was downloaded successfully");
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,
						"Error while downloading file " + fileName + ":" + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}