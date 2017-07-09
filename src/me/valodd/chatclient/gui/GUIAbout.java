package me.valodd.chatclient.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GUIAbout extends JDialog {

	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JButton okButton;
	private JLabel lblInfo;

	/**
	 * Create the dialog.
	 */
	public GUIAbout() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());

		// PANNEL CENTER
		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// PANNEL BUTTON
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// TEXT INFO
		lblInfo = new JLabel("Created By 0ddlyoko and Valnico");

		// BUTTON OK
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getRootPane().setDefaultButton(okButton);

		// ADD PANNELS
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// ADD ITEMS INTO PANNEL BUTTON
		buttonPanel.add(lblInfo);
		buttonPanel.add(okButton);
	}

}
