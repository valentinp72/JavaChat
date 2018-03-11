package chat.client.ui;

import java.util.List;


import chat.client.Client;
import chat.client.ActionsMessages;
import chat.messages.ClientMessage;
import chat.messages.ClientMessageMessage;

import chat.messages.DataMessage;
import chat.messages.DataUser;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainWindow extends JFrame {

	private Client client;

	private ConfigurationPanel panelConfig;
	private ConnectedPanel     panelConnected;
	private DiscussionPanel    panelDiscussion;
	private LogoutPanel        panelLogout;

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

	public String setClient(String ip, int port, String username) {
		try {
			this.client = new Client(ip, port, username);
			this.getContentPane().remove(this.panelConfig);
			this.getContentPane().add(panelLogout,     BorderLayout.NORTH);
			this.getContentPane().add(panelDiscussion, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);

			this.client.setActionMessages(new ActionsMessages() {
				@Override
				public void newMessage(DataMessage message) {
					panelDiscussion.addMessage(message);
				}

				@Override
				public void setMessages(List<DataMessage> messages) {
					panelDiscussion.setMessages(messages);
				}

				@Override
				public void setUsers(List<DataUser> users) {
					panelConnected.setUsers(users);
				}

				@Override
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

	public boolean sendMessage(String message) {
		if(this.client != null) {
			ClientMessageMessage toSend = new ClientMessageMessage(message);
			return this.client.send(toSend);
		}

		return false;
	}

	public ConnectedPanel getPanelConnected() {
		return this.panelConnected;
	}

}
