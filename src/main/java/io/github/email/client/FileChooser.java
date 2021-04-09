package io.github.email.client;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileChooser extends JPanel implements ActionListener {
	private final JTextField textField;
	private final JFileChooser fileChooser;
	private final JButton attachButton;

	public FileChooser(String textFieldLabel, String buttonLabel) {
		super(new BorderLayout());
		fileChooser = new JFileChooser();
		
		setLayout(new BorderLayout());

		// creates the GUI
		JLabel label = new JLabel(textFieldLabel);
		
		textField = new JTextField(30);
		attachButton = new JButton(buttonLabel);

		attachButton.addActionListener(this);
		
		add(label);
		add(textField);
		add(attachButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == attachButton) {
			if (fileChooser.showOpenDialog(FileChooser.this) == JFileChooser.APPROVE_OPTION) {
				textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}
	}
	
	public String getSelectedFilePath() {
		return textField.getText();
	}
}