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

// TODO: Auto-generated Javadoc
/**
 * The Class Server.
 */
public class Server {

	/** The port. */
	private int port;
	
	/** The host. */
	private String host;

	/** The messages. */
	private List<DataMessage> messages;
	
	/** The clients. */
	private List<ClientThread> clients;
	
	/** The server. */
	private ServerSocket server;

	/**
	 * Instantiates a new server.
	 */
	public Server() {
		try {
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
	 * Adds the client.
	 *
	 * @param client the client
	 */
	public void addClient(Socket client) {
		ClientThread thread = new ClientThread(client, this);
		clients.add(thread);
	}

	/**
	 * Removes the client.
	 *
	 * @param client the client
	 */
	public void removeClient(ClientThread client) {
		clients.remove(client);
	}

	/**
	 * Gets the users.
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
	 * Gets the usernames.
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
	 * Send user list.
	 */
	public void sendUserList() {
		List<DataUser> users = this.getUsers();
		this.sendServerMsgToClients(new ServerMessageUsers(users));
	}

	/**
	 * Adds the message.
	 *
	 * @param message the message
	 */
	public void addMessage(DataMessage message) {
		this.messages.add(message);
		ServerMessageNewMessage msg = new ServerMessageNewMessage(message);
		this.sendServerMsgToClients(msg);
	}

	/**
	 * Send messages list.
	 */
	public void sendMessagesList() {
		ServerMessageMessages msgs = new ServerMessageMessages(this.messages);
		this.sendServerMsgToClients(msgs);
	}

	/**
	 * Send server msg to clients.
	 *
	 * @param msg the msg
	 */
	public void sendServerMsgToClients(ServerMessage msg) {
		for(ClientThread client : clients) {
			client.send(msg);
		}
	}

	/**
	 * Run.
	 */
	public void run() {
		while(true) {
			try {
				Socket clientSc = server.accept();
				this.addClient(clientSc);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
