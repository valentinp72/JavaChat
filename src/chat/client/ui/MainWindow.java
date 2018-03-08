package chat.client.ui;

import chat.client.Client;
import chat.ClientMessage;


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

		String[] a = {"test"};
		panelConnected.setContent(a);

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
		if(this.client != null)
			return this.client.send(ClientMessage.SEND_MESSAGE, message);
		return false;
	}

}
