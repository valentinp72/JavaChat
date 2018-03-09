package chat.client.ui;

import java.util.List;


import chat.client.Client;
import chat.client.ActionsMessages;
import chat.messages.ClientMessage;
import chat.messages.ClientMessageMessage;

import chat.messages.DataMessage;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainWindow extends JFrame {

	private Client client;

	private ConfigurationPanel panelConfig;
	private ConnectedPanel     panelConnected;
	private DiscussionPanel    panelDiscussion;

	public MainWindow() {
		super();

		this.setTitle("Chat");
		this.setSize(500, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// create the panels
		this.panelConfig     = new ConfigurationPanel(this);
		this.panelConnected  = new ConnectedPanel(this);
		this.panelDiscussion = new DiscussionPanel(this);

		// add everything on the window
		this.setLayout(new BorderLayout());
		this.getContentPane().add(panelConfig,     BorderLayout.NORTH);
		this.getContentPane().add(panelConnected,  BorderLayout.WEST);
		this.getContentPane().add(panelDiscussion, BorderLayout.CENTER);

		this.setVisible(true);

		//System.out.println(this.setClient());
	}

	public String setClient(String ip, int port, String username) {
		try {
			this.client = new Client(ip, port, username);

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
				public void setUsers(List<String> users) {
					panelConnected.setUsers(users);
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

	public boolean sendMessage(String message) {
		if(this.client != null) {
			ClientMessageMessage toSend = new ClientMessageMessage(message);
			return this.client.send(toSend);
		}

		return false;
	}


}
