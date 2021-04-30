package io.github.email.client.dialogs;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;

public class EmailContentDialog extends JDialog {

    public EmailContentDialog(JFrame parent, String emailBody) {
        super(parent, "Email body", true);
        setLayout(new GridBagLayout());
        JTextArea textAreaMessage = new JTextArea(30, 40);
        add(new JScrollPane(textAreaMessage));
        textAreaMessage.setText(emailBody);
        pack();
        setLocationRelativeTo(null);
    }
}
