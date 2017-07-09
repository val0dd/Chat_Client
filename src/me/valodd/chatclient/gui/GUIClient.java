package me.valodd.chatclient.gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIClient extends JFrame implements ActionListener {

	private JMenuBar menuBar;

	private JMenu mnConnections;
	private JMenuItem mntmConnect, mntmDisconnect, mntmQuit;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;

	/**
	 * Create the application.
	 */
	public GUIClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Set Default Theme
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// FRAME
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));

		// BAR
		menuBar = new JMenuBar();

		// ---------- MENU CONNECTIONS ----------
		mnConnections = new JMenu("Connections");

		// MENU ITEM CONNECT
		mntmConnect = new JMenuItem("Connect");
		mntmConnect.setHorizontalAlignment(SwingConstants.LEFT);
		mntmConnect.setActionCommand("connect");
		mntmConnect.addActionListener(this);

		// MENU ITEM DISCONNECT
		mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.setHorizontalAlignment(SwingConstants.LEFT);
		mntmDisconnect.setActionCommand("disconnect");
		mntmDisconnect.addActionListener(this);

		mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(this);

		// ---------- MENU HELP ----------
		mnHelp = new JMenu("Help");

		// MENU ITEM ABOUT
		mntmAbout = new JMenuItem("About");
		mntmAbout.setActionCommand("about");
		mntmAbout.addActionListener(this);

		// ADD FRAME
		setJMenuBar(menuBar);

		// ADD MENUS
		menuBar.add(mnConnections);
		menuBar.add(mnHelp);

		// ADD MENUITEMS FOR MENU mnConnections
		mnConnections.add(mntmConnect);
		mnConnections.add(mntmDisconnect);
		mnConnections.addSeparator();
		mnConnections.add(mntmQuit);

		// ADD MENUITEMS FOR MENU mcHelp
		mnHelp.add(mntmAbout);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String action = ae.getActionCommand();
		if (action == null)
			return;
		if ("connect".equalsIgnoreCase(action)) {

		} else if ("disconnect".equalsIgnoreCase(action)) {

		} else if ("about".equalsIgnoreCase(action)) {
			GUIManager.showGUIAbout();
		}
	}
}
