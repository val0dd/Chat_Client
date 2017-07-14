package me.valodd.chatclient.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import me.valodd.chatclient.server.Server;
import me.valodd.chatclient.server.ServerManager;

public class GUIConnection extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanel contentPanel;
	private JPanel buttonPane;
	private JButton okButton, btnInNewTab, cancelButton;
	private JLabel lblServerAddress, lblName, lblPassword;
	private JTextField txtFldSrvAddr, txtFldName, txtFldPasswd;

	/**
	 * Create the dialog.
	 */
	public GUIConnection() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		// PANNEL CENTER
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPanel.setLayout(null);

		// PANNEL BUTTON
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// BUTTON OK
		okButton = new JButton("Connect");
		okButton.setActionCommand("CONNECT");
		okButton.addActionListener(this);
		getRootPane().setDefaultButton(okButton);

		// BUTTON IN NEW TAB
		btnInNewTab = new JButton("In New Tab");
		btnInNewTab.setActionCommand("INNEWTAB");
		btnInNewTab.addActionListener(this);

		// BUTTON CANCEL
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("CANCEL");
		cancelButton.addActionListener(this);

		// TEXT SERVER ADDRESS
		lblServerAddress = new JLabel("Server Address :");
		lblServerAddress.setBounds(10, 11, 89, 14);

		// INPUT TEXT SERVER ADDRESS
		txtFldSrvAddr = new JTextField();
		txtFldSrvAddr.setBounds(10, 36, 424, 20);
		txtFldSrvAddr.setColumns(10);

		// TEXT NAME
		lblName = new JLabel("Name:");
		lblName.setBounds(10, 67, 46, 14);

		// INPUT TEXT NAME
		txtFldName = new JTextField();
		txtFldName.setBounds(10, 92, 275, 20);
		txtFldName.setColumns(10);

		// TEXT PASSWORD
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(295, 67, 58, 14);

		// INPUT TEXT PASSWORD
		txtFldPasswd = new JTextField();
		txtFldPasswd.setBounds(295, 92, 139, 20);
		txtFldPasswd.setColumns(10);

		// ADD PANNELS
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		// ADD ITEMS INTO PANNEL BUTTON
		buttonPane.add(okButton);
		buttonPane.add(btnInNewTab);
		buttonPane.add(cancelButton);

		// ADD ITEMS INTO PANNEL MID
		contentPanel.add(lblServerAddress);
		contentPanel.add(txtFldSrvAddr);
		contentPanel.add(lblName);
		contentPanel.add(txtFldName);
		contentPanel.add(lblPassword);
		contentPanel.add(txtFldPasswd);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == null)
			return;
		if ("CONNECT".equalsIgnoreCase(action) || "INNEWTAB".equalsIgnoreCase(action)) {
			String srvAddr = txtFldSrvAddr.getText();
			String pseudo = txtFldName.getText();
			String password = txtFldPasswd.getText() == null ? "" : txtFldPasswd.getText();
			if (srvAddr != null && !"".equalsIgnoreCase(srvAddr) && pseudo != null && !"".equalsIgnoreCase(pseudo)) {
				if ("CONNECT".equalsIgnoreCase(action)) {
					if (ServerManager.getActiveServer() != null)
						ServerManager.getActiveServer().disconnect();
					GUIManager.getGUIClient().addText("Trying to resolve hostname \"" + srvAddr + "\"",
							GUIChatStyle.INFO, true);
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								InetAddress ia = InetAddress.getByName(srvAddr);
								GUIManager.getGUIClient().addText("Trying to connect to server \"" + srvAddr + "\"",
										GUIChatStyle.INFO, true);
								Server srv = new Server(ia, ServerManager.PORT);
								if (srv.connect(pseudo, password)) {
									GUIManager.getGUIClient().addText(
											"Connected, waiting for incoming data. Please wait", GUIChatStyle.INFO,
											true);
									ServerManager.addServer(srv);
									ServerManager.setActiveServer(srv);
									dispose();
								} else {
									GUIManager.getGUIClient().addText("ERROR: Failed to connect to this server",
											GUIChatStyle.ERROR, true);
								}
							} catch (UnknownHostException ex) {
								GUIManager.getGUIClient().addText(
										"ERROR: Could not resolve hostname \"" + srvAddr + "\" : " + ex.getMessage(),
										GUIChatStyle.ERROR, true);
							}
						}
					}).start();
				} else if ("INNEWTAB".equalsIgnoreCase(action)) {
					/*
					 * GUIManager.getGUIClient().
					 * addText("Trying to resolve hostname \"" + srvAddr + "\"",
					 * GUIChatStyle.INFO, true); new Thread(new Runnable() {
					 * 
					 * @Override public void run() { try { InetAddress ia =
					 * InetAddress.getByName(srvAddr);
					 * GUIManager.getGUIClient().
					 * addText("Trying to connect to server \"" + srvAddr +
					 * "\"", GUIChatStyle.INFO, true); Server srv = new
					 * Server(ia, ServerManager.PORT); if (srv.connect(pseudo,
					 * password)) { GUIManager.getGUIClient().addText(
					 * "Connected, waiting for incoming data. Please wait",
					 * GUIChatStyle.INFO, true); ServerManager.addServer(srv);
					 * if (ServerManager.getActiveServer() == null)
					 * ServerManager.setActiveServer(srv); dispose(); } else {
					 * GUIManager.getGUIClient().
					 * addText("ERROR: Failed to connect to this server",
					 * GUIChatStyle.ERROR, true); } } catch
					 * (UnknownHostException ex) {
					 * GUIManager.getGUIClient().addText(
					 * "ERROR: Could not resolve hostname \"" + srvAddr +
					 * "\" : " + ex.getMessage(), GUIChatStyle.ERROR, true); } }
					 * }).start();
					 */
				}
			}
		} else if ("CANCEL".equalsIgnoreCase(action)) {
			dispose();
		}
	}
}
