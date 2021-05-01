package io.github.email.client.dialogs;

import io.github.email.client.base.AttachmentDownloader;
import io.github.email.client.imap.Attachment;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

public class EmailContentDialog extends JDialog {

    public EmailContentDialog(JFrame parent, String bodyPlain, String bodyHtml, List<Attachment> attachments) {
        super(parent, "Email body", true);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JEditorPane contentText;
        if (!"".equals(bodyHtml)) {
            contentText = new JEditorPane("text/html", bodyHtml);
        } else {
            contentText = new JEditorPane("text/plain", bodyPlain);
        }
        JScrollPane scrollableContentText = new JScrollPane(contentText);
        JPanel attachmentsPanel = new JPanel();
        attachmentsPanel.setLayout(new GridBagLayout());
        addAttachments(attachments, attachmentsPanel);
        mainPanel.add(attachmentsPanel, BorderLayout.NORTH);
        mainPanel.add(scrollableContentText, BorderLayout.CENTER);
        setSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        add(mainPanel);
    }

    private void addAttachments(List<Attachment> attachments, JPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        int x = 0;
        for (Attachment attachment : attachments) {
            AttachmentDownloader attachmentDownloader = new AttachmentDownloader(attachment);
            constraints.gridx = x++;
            panel.add(attachmentDownloader, constraints);
        }
    }
}
