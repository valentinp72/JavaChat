package chat.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import chat.messages.DataMessage;
import chat.messages.DataUser;
import chat.messages.ServerMessage;
import chat.messages.ServerMessageMessages;
import chat.messages.ServerMessageNewMessage;
import chat.messages.ServerMessageUsers;

/**
 * This class represents the core server.
 */

public class Server {

	/** The port of the server */
	private int port;

	/** The host */
	private String host;

	/** The messages */
	private List<DataMessage> messages;

	/** The clients */
	private List<ClientThread> clients;

	/** The server socket */
	private ServerSocket server;

	/**
	 * Instantiates a new server.
	 */
	public Server() {
		try {
			// default configuration
			this.host     = "127.0.0.1";
			this.port     = 5890;
			this.clients  = new ArrayList<ClientThread>();
			this.messages = new ArrayList<DataMessage>();

			System.out.print("Démarrage du serveur " + this.host + ":" + this.port + "...");
			this.server = new ServerSocket(port, 100, InetAddress.getByName(host));
			System.out.println("fait!");

		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds a client to the server. This starts dedicated thread for it.
	 *
	 * @param client the client
	 */
	public void addClient(Socket client) {
		ClientThread thread = new ClientThread(client, this);
		clients.add(thread);
	}

	/**
	 * Removes the client from the server.
	 *
	 * @param client the client
	 */
	public void removeClient(ClientThread client) {
		clients.remove(client);
	}

	/**
	 * Gets the connected users.
	 *
	 * @return the users
	 */
	public List<DataUser> getUsers() {
		List<DataUser> users = new ArrayList<DataUser>();
		for(ClientThread client : clients) {
			if(client.getUser() != null) {
				users.add(client.getUser());
			}
		}
		return users;
	}

	/**
	 * Gets the usernames of all connected users.
	 *
	 * @return the usernames
	 */
	public List<String> getUsernames() {
		List<String> usernames = new ArrayList<String>();
		for(DataUser user : this.getUsers()) {
			usernames.add(user.toString());
		}
		return usernames;
	}

	/**
	 * Send the user list to all clients.
	 */
	public void sendUserList() {
		List<DataUser> users = this.getUsers();
		this.sendServerMsgToClients(new ServerMessageUsers(users));
	}

	/**
	 * Adds a message and send it to all.
	 *
	 * @param message the message
	 */
	public void addMessage(DataMessage message) {
		this.messages.add(message);
		ServerMessageNewMessage msg = new ServerMessageNewMessage(message);
		this.sendServerMsgToClients(msg);
	}

	/**
	 * Send the messages list to all clients.
	 */
	public void sendMessagesList() {
		ServerMessageMessages msgs = new ServerMessageMessages(this.messages);
		this.sendServerMsgToClients(msgs);
	}

	/**
	 * Send a server message to all clients.
	 *
	 * @param msg the message
	 */
	public void sendServerMsgToClients(ServerMessage msg) {
		for(ClientThread client : clients) {
			client.send(msg);
		}
	}

	/**
	 * Run the server : accept all the clients.
	 */
	public void run() {
		try {
			while(true) {
				Socket clientSc = server.accept();
				this.addClient(clientSc);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the messages of the server
	 * @return the messages
	 */
	public List<DataMessage> getMessages() {
		return this.messages;
	}

}
