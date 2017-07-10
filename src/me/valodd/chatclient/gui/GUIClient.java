package me.valodd.chatclient.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class GUIClient extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// ---------- MENU ----------
	private JMenuBar menuBar;

	// MENU CONNECTIONS
	private JMenu mnConnections;
	private JMenuItem mntmConnect, mntmDisconnect, mntmQuit;
	// MENU HELP
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	// ---------- ----------
	private JSplitPane splitPane, splitPane_1;
	private JPanel panelChat, panelChannels, panelInfo;

	// CHAT
	private JTextPane txtpnChat, txtFldChat;
	private StyledDocument sdChat;
	private JScrollPane jspChat;

	/**
	 * Create the application.
	 */
	public GUIClient() {
		initialize();
		setOutputStyles();
	}

	private void setOutputStyles() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		for (GUIChatStyle guics : GUIChatStyle.values()) {
			Style style = txtpnChat.addStyle(guics.getName(), def);
			if (guics.getFontFamily() != null)
				StyleConstants.setFontFamily(style, guics.getFontFamily());
			StyleConstants.setFontSize(style, guics.getSize());
			StyleConstants.setFirstLineIndent(style, guics.getFirstLineIndent());
			if (guics.getForeground() != null)
				StyleConstants.setForeground(style, guics.getForeground());
			if (guics.getBackground() != null)
				StyleConstants.setBackground(style, guics.getBackground());
			StyleConstants.setBold(style, guics.isBold());
			StyleConstants.setUnderline(style, guics.isUnderline());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Set Default Theme
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

		// FRAME
		setBounds(100, 100, 750, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));

		splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		// TODO CHANGE THE NEXT 2 LINES
		// splitPane.setDividerLocation(0.7);
		splitPane.setDividerLocation(450);

		// PANNEL CHAT
		panelChat = new JPanel();
		panelChat.setLayout(new BorderLayout(0, 0));

		// CHAT
		txtpnChat = new JTextPane();
		txtpnChat.setEditable(false);

		sdChat = txtpnChat.getStyledDocument();

		txtFldChat = new JTextPane();
		txtFldChat.setToolTipText("Enter Chat Message");
		// ON ENTER PRESSED
		txtFldChat.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
		txtFldChat.getActionMap().put("enter", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage(txtFldChat.getText());
				txtFldChat.setText("");
			}
		});

		jspChat = new JScrollPane(txtpnChat);

		// SPLITPANE CHAT - OTHER
		splitPane_1 = new JSplitPane();
		splitPane_1.setEnabled(false);
		splitPane_1.setContinuousLayout(true);
		// TODO CHANGE THE NEXT 2 LINES
		// splitPane_1.setDividerLocation(0.7);
		splitPane_1.setDividerLocation(450);

		// EVENT ON WINDOWS RESIZED
		addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				splitPane.setDividerLocation(0.7);
				splitPane_1.setDividerLocation(0.7);
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// NOTHING TO DO HERE
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// NOTHING TO DO HERE
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// NOTHING TO DO HERE
			}
		});

		// PANNEL CHANNELS
		panelChannels = new JPanel();

		// PANNEL INFO
		panelInfo = new JPanel();

		// MENU BAR
		menuBar = new JMenuBar();

		// ---------- MENU CONNECTIONS ----------
		mnConnections = new JMenu("Connections");

		// MENU ITEM CONNECT
		mntmConnect = new JMenuItem("Connect");
		mntmConnect.setActionCommand("CONNECT");
		mntmConnect.addActionListener(this);

		// MENU ITEM DISCONNECT
		mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.setActionCommand("DISCONNECT");
		mntmDisconnect.addActionListener(this);

		mntmQuit = new JMenuItem("Quit");
		mntmQuit.setActionCommand("QUIT");
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

		// ADD MAIN SPLITPANE
		getContentPane().add(splitPane, "name_34340883196789");

		// ADD PANEL CHAT AND ANOTHER SPLITPANE
		splitPane.setRightComponent(panelChat);
		splitPane.setLeftComponent(splitPane_1);

		// ADD TXT PANE CHAT AND TXT FIELD CHAT IN PANEL CHAT
		panelChat.add(jspChat, BorderLayout.CENTER);
		panelChat.add(txtFldChat, BorderLayout.SOUTH);

		// ADD PANEL INFO AND PANAL CHANNELS
		splitPane_1.setRightComponent(panelInfo);
		splitPane_1.setLeftComponent(panelChannels);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == null)
			return;
		if ("CONNECT".equalsIgnoreCase(action)) {
			GUIManager.showGUIConnect();
		} else if ("DISCONNECT".equalsIgnoreCase(action)) {
			// TODO DISCONNECT
		} else if ("QUIT".equalsIgnoreCase(action)) {
			// TODO DISCONNECT
			System.exit(0);
		} else if ("about".equalsIgnoreCase(action)) {
			GUIManager.showGUIAbout();
		}
	}

	private boolean isChatViewAtBottom() {
		JScrollBar scb = jspChat.getVerticalScrollBar();
		int min = scb.getValue() + scb.getVisibleAmount();
		int max = scb.getMaximum();
		return min == max;
	}

	private void scrollToBottom() {
		SwingUtilities.invokeLater(
				() -> jspChat.getVerticalScrollBar().setValue(jspChat.getVerticalScrollBar().getMaximum()));
	}

	public JTextPane getTxtFieldChat() {
		return txtFldChat;
	}

	public JTextPane getTxtPaneChat() {
		return txtpnChat;
	}

	public String getText() {
		return txtpnChat.getText();
	}

	private void sendMessage(String msg) {
		if (msg.startsWith("msg:"))
			addText(msg, GUIChatStyle.MESSAGE, true);
		else
			addText(msg, GUIChatStyle.USERNAME, true);
		// TODO CHECK IF USER CAN SEND CHAT MESSAGE AND SEND PACKET MESSAGE
	}

	public void addText(String txt, GUIChatStyle style, boolean isNewLine) {
		String newLine = isNewLine ? "\n" : "";
		try {
			sdChat.insertString(sdChat.getLength(), String.format("%s%s", newLine, txt),
					sdChat.getStyle(style.getName()));
			if (isChatViewAtBottom() && isNewLine)
				scrollToBottom();
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}
}
