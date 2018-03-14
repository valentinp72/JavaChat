package chat.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import chat.messages.DataMessage;
import chat.messages.DataMessageInfo;
import chat.messages.DataUser;
import chat.messages.ServerMessage;
import chat.messages.ServerMessageMessages;
import chat.messages.ServerMessageNewMessage;
import chat.messages.ServerMessageUsers;

import chat.server.command.Command;
import chat.server.command.CommandInfo;
import chat.server.command.CommandHelp;
import chat.server.command.CommandMsg;
import chat.server.command.CommandColor;



/**
 * This class represents the core server.
 */

public class Server {

	/** The user the welcome and goodbye messages are sent by */
	public static final DataUser ADMIN_USER = new DataUser("");

	public static final DataUser INFO_USER  = new DataUser("INFO");

	private static final String DEFAULT_MESSAGE_CONT = "Bonjour et bienvenue sur JavaChat™ !\n"
	                                                 + "Vous pouvez :\n"
													 + "  - envoyer un message depuis la zone 'Message'\n"
													 + "  - effectuer une commande en débutant un message par un '/'\n"
													 + "  - afficher les commandes disponnibles avec '/help'\n"
													 + "  - envoyer un message privé avec '/msg destinataire message'\n"
													 + "  - changer la couleur de votre pseudo avec '/color #rrggbb'\n"
													 + "\n"
													 + " Bon chat ! :)";
	private static final DataMessage DEFAULT_MESSAGE = new DataMessage(INFO_USER, DEFAULT_MESSAGE_CONT);

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


			this.addMessage(DEFAULT_MESSAGE);
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
		client.stop();
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
	 * If the message stars with a /, it interprets it as a command
	 *
	 * @param message the message
	 */
	public void addMessage(DataMessage message) {
		if(message.getMessage().length() != 0) {
			if(!interpretCommand(message)) {
				this.messages.add(message);
				ServerMessageNewMessage msg = new ServerMessageNewMessage(message);
				this.sendServerMsgToClients(msg);
			}
		}
	}

	/**
	 * If the message is a command, interprets it, otherwise
	 * it just return false
	 *
	 * @param message the message that should be a command starting with a /
	 * @return false if the command was not valid, true otherwise
	 */
	public boolean interpretCommand(DataMessage message) {
		if(message.getMessage().charAt(0) == '/') {

			String contents[] = message.getMessage().split(" ", 2);
			String command = contents[0].substring(1);

			Command c;

			switch(command) {
				case "msg":
					c = new CommandMsg();
					break;
				case "info":
					c = new CommandInfo();
					break;
				case "help":
					c = new CommandHelp();
					break;
				case "color":
					c = new CommandColor();
					break;

				// not a valid command
				default:
					this.sendErrorMessage(command + ": commande invalide", message.getUser().getUsername());
					return true;
			}

			String error = c.execute(this, message);
			if(error != Command.PAS_ERREUR) {
				this.sendErrorMessage(command + " : " + error, message.getUser().getUsername());
			}
			return true;
		}
		return false;
	}

	public void sendErrorMessage(String error, String username) {
		ClientThread dest = getClientByName(username);
		DataMessage   msg = new DataMessageInfo(ADMIN_USER, "Erreur " + error);
		dest.send(new ServerMessageNewMessage(msg));
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

	public ClientThread getClientByName(String username) {
		for(ClientThread client : clients) {
			if(client.getUser().getUsername().equals(username)) {
				return client;
			}
		}
		throw new IllegalArgumentException(username + " n'est pas connecté au serveur");
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
