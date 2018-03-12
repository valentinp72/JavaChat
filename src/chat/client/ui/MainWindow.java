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

/**
 * The main window of the application. This displays either a configuration panel to allow login,
 * either a discussion panel to allow discussion.
 */
public class MainWindow extends JFrame {

	/** The client */
	private Client client;

	/** The config panel */
	private ConfigurationPanel panelConfig;

	/** The connected panel */
	private ConnectedPanel panelConnected;

	/** The discussion panel */
	private DiscussionPanel panelDiscussion;

	/** The logout panel */
	private LogoutPanel panelLogout;

	/**
	 * Instantiates a new window.
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
	 * Sets the client of the window.
	 *
	 * @param ip the ip to connect on
	 * @param port the port of the server
	 * @param username the username to login
	 * @return a String: if connected "OK", else an explaination of the error
	 */
	public String setClient(String ip, int port, String username) {
		try {
			this.client = new Client(ip, port, username);
			this.getContentPane().remove(this.panelConfig);
			this.getContentPane().add(panelLogout,     BorderLayout.NORTH);
			this.getContentPane().add(panelDiscussion, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);

			// we ask the client to do theses actions when the client
			// receive something
			// this allows a MVC-like pattern, the client does not
			// know anything about the UI
			this.client.setActionMessages(new ActionsMessages() {
				// action when a new message occurs
				public void newMessage(DataMessage message) {
					panelDiscussion.addMessage(message);
				}

				// action when the server wants to reset all the messages
				public void setMessages(List<DataMessage> messages) {
					panelDiscussion.setMessages(messages);
				}

				// action when the server sends the new user list
				public void setUsers(List<DataUser> users) {
					panelConnected.setUsers(users);
				}

				// action when the server send a connection error
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
	 * Reset client. This closes the connection and update the UI to be
	 * back to the login panel.
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
	 * Send a new message to the server.
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
	 * Gets the connected panel.
	 *
	 * @return the connected apnel
	 */
	public ConnectedPanel getPanelConnected() {
		return this.panelConnected;
	}

	/**
	 * Gets the client
	 *
	 * @return the client
	 */
	public Client getClient() {
		return this.client;
	}

}
