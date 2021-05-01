package io.github.email.client.dialogs;

import io.github.email.client.base.AttachmentDownloader;
import io.github.email.client.imap.Attachment;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

public class EmailContentDialog extends JDialog {
    private final GridBagConstraints constraints = new GridBagConstraints();

    public EmailContentDialog(JFrame parent, String emailBody, List<Attachment> attachments) {
        super(parent, "Email body", true);
        setLayout(new GridBagLayout());
        addAttachments(attachments);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 30;
        JTextArea textAreaMessage = new JTextArea(30, 40);
        add(new JScrollPane(textAreaMessage), constraints);
        textAreaMessage.setText(emailBody);
        pack();
        setLocationRelativeTo(null);
    }

    private void addAttachments(List<Attachment> attachments) {
        constraints.gridy = 0;
        int x = 0;
        for (Attachment attachment : attachments) {
            AttachmentDownloader attachmentDownloader = new AttachmentDownloader(attachment);
            constraints.gridx = x++;
            add(attachmentDownloader, constraints);
        }
    }
}
