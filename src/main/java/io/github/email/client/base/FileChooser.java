package io.github.email.client.base;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileChooser extends JPanel implements ActionListener {
	private final JTextField textField;
	private final JFileChooser fileChooser;
	private final JButton attachButton;

	public FileChooser(String textFieldLabel, String buttonLabel) {
		super(new BorderLayout());
		fileChooser = new JFileChooser();
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel label = new JLabel(textFieldLabel);
		textField = new JTextField(30);
		attachButton = new JButton(buttonLabel);
		attachButton.addActionListener(this);
		addComponent(label, constraints, 0);
		addComponent(textField, constraints, 1);
		addComponent(attachButton, constraints, 2);
	}

	private void addComponent(JComponent component, GridBagConstraints constraints, int x) {
		constraints.gridx = x;
		constraints.gridy = 0;
		add(component, constraints);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == attachButton) {
			if (fileChooser.showOpenDialog(FileChooser.this) == JFileChooser.APPROVE_OPTION) {
				if (textField.getText().equals("")) {
					textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
				} else {
					textField.setText(textField.getText() + "," + fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		}
	}
	
	public String getSelectedFilePaths() {
		return textField.getText();
	}
}