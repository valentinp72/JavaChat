/*
 * 
 */
package chat.client.ui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import chat.client.ActionsMessages;
import chat.client.Client;
import chat.messages.ClientMessageMessage;
import chat.messages.DataMessage;
import chat.messages.DataUser;

// TODO: Auto-generated Javadoc
/**
 * The Class MainWindow.
 */
public class MainWindow extends JFrame {

	/** The client. */
	private Client client;

	/** The panel config. */
	private ConfigurationPanel panelConfig;
	
	/** The panel connected. */
	private ConnectedPanel     panelConnected;
	
	/** The panel discussion. */
	private DiscussionPanel    panelDiscussion;
	
	/** The panel logout. */
	private LogoutPanel        panelLogout;

	/**
	 * Instantiates a new main window.
	 */
	public MainWindow() {
		super();

		this.setTitle("Chat");
		this.setSize(800, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// create the panels
		this.panelConfig     = new ConfigurationPanel(this);
		this.panelLogout     = new LogoutPanel(this);
		this.panelConnected  = new ConnectedPanel(this);
		this.panelDiscussion = new DiscussionPanel(this);

		// add everything on the window
		this.setLayout(new BorderLayout());
		this.getContentPane().add(panelConfig, BorderLayout.CENTER);

		this.setVisible(true);
	}

	/**
	 * Sets the client.
	 *
	 * @param ip the ip
	 * @param port the port
	 * @param username the username
	 * @return the string
	 */
	public String setClient(String ip, int port, String username) {
		try {
			this.client = new Client(ip, port, username);
			this.getContentPane().remove(this.panelConfig);
			this.getContentPane().add(panelLogout,     BorderLayout.NORTH);
			this.getContentPane().add(panelDiscussion, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);

			this.client.setActionMessages(new ActionsMessages() {
				public void newMessage(DataMessage message) {
					panelDiscussion.addMessage(message);
				}

				public void setMessages(List<DataMessage> messages) {
					panelDiscussion.setMessages(messages);
				}

				public void setUsers(List<DataUser> users) {
					panelConnected.setUsers(users);
				}

				public void connectionError(String error) {
					resetClient();
					JOptionPane.showMessageDialog(
						new JFrame(),
						error,
						"Dialog",
						JOptionPane.ERROR_MESSAGE
					);
				}
			});
		}
		catch(UnknownHostException e) {
			return "Unknown host!";
		}
		catch(IOException e) {
			return "Internet connection error!";
		}
		return "OK";
	}

	/**
	 * Reset client.
	 */
	public void resetClient() {
		if(this.client != null) {
			this.client.disconnect();
			this.client = null;
		}
		this.getContentPane().remove(this.panelLogout);
		this.getContentPane().remove(this.panelDiscussion);
		this.getContentPane().add(panelConfig, BorderLayout.CENTER);
		this.repaint();
		this.setVisible(true);
	}

	/**
	 * Send message.
	 *
	 * @param message the message
	 * @return true, if successful
	 */
	public boolean sendMessage(String message) {
		if(this.client != null) {
			ClientMessageMessage toSend = new ClientMessageMessage(message);
			return this.client.send(toSend);
		}

		return false;
	}

	/**
	 * Gets the panel connected.
	 *
	 * @return the panel connected
	 */
	public ConnectedPanel getPanelConnected() {
		return this.panelConnected;
	}

}
