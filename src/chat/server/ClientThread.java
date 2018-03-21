package chat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import chat.messages.ClientMessage;
import chat.messages.ClientMessageLogin;
import chat.messages.ClientMessageLogout;
import chat.messages.ClientMessageMessage;
import chat.messages.DataMessage;
import chat.messages.DataMessageInfo;
import chat.messages.DataUser;
import chat.messages.ServerMessage;
import chat.messages.ServerMessageMessages;
import chat.messages.ServerMessageConnectionError;


/**
 * This class represents a server thread working with a client, always waiting for messages
 * from the client.
 */

public class ClientThread implements Runnable {

	/** The user the thread is communicating with */
	private DataUser user;

	/** The thread */
	private Thread thread;

	/** The socket */
	private Socket socket;

	/** The server */
	private Server server;

	/** The output to the client */
	private ObjectOutputStream output;

	/** The input from the client */
	private ObjectInputStream  input;

	/** True if the thread is running */
	private boolean running;

	/**
	 * Instantiates a new client thread.
	 *
	 * @param socket the socket
	 * @param server the server
	 */
	public ClientThread(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;

		try {
			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.input  = new ObjectInputStream(socket.getInputStream());

			this.thread  = new Thread(this);
			this.thread.start();
			this.running = true;
		}
		catch(IOException e) {
			this.server.removeClient(this);
		}
	}

	/**
	 * Runs the thread. This wait for a welcome message, and then wait for messages from the client.
	 */
	public void run() {
		try {

			// hello message
			ClientMessageLogin loginMsg = (ClientMessageLogin) this.read();

			if(loginMsg.action(this)) {

				this.user = new DataUser(loginMsg.getUsername()); // we create the real user
				this.server.sendUserList();     // we send the updated user list to the clients
				this.send(new ServerMessageMessages(server.getMessages())); // we send the messages to the client
				this.sendWelcomeMessage();      // we send the welcome message

				while(running) {

					// we wait until we receive a message from the client
					ClientMessage message = this.read();

					message.action(this);

					System.out.println(message.toString());
				}
			}

		}
		catch(IOException e) {
			// we remove the client
			this.server.removeClient(this);
			this.server.sendUserList();
			this.sendGoodbyeMessage();
			thread.currentThread().interrupt();
		}

	}

	/**
	 * Stop to listen messages.
	 */
	public void stop() {
		if(this.running) {
			this.running = false;
			this.thread.interrupt();
		}
	}

	/**
	 * Send a message to the client
	 *
	 * @param msg the message to send
	 * @throws IOException if the message could not be sent
	 */
	public void send(ServerMessage msg) throws IOException {
		output.reset();
		output.writeObject(msg);
	}

	/**
	 * Read a message from the client. This is blocking.
	 *
	 * @return the client message
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private ClientMessage read() throws IOException {
		try {
			return (ClientMessage) input.readObject();
		}
		catch(ClassNotFoundException e) {
			return null;
		}
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public DataUser getUser() {
		return this.user;
	}

	/**
	 * Send welcome message to all.
	 */
	public void sendWelcomeMessage() {
		DataMessage msg = new DataMessageInfo(server.ADMIN_USER, user.toString() + " vient de rejoindre.");
		this.server.addMessage(msg);
	}

	/**
	 * Send goodbye message to all.
	 */
	public void sendGoodbyeMessage() {
		DataMessage msg = new DataMessageInfo(server.ADMIN_USER, user.toString() + " vient de quitter.");
		this.server.addMessage(msg);
	}

	/**
	 * Send a new message to all clients connected to the server
	 * The server will first check if this is a command, and interprets it.
	 *
	 * @param message the message to send
	 * @return true
	 */
	public boolean sendNewMessage(DataMessage message) {
		this.server.addMessage(message);
		return true;
	}

	/**
	 * Tries to login the given username to the server.
	 * This sends a ServerMessageConnectionError to the client if
	 * he cannot connect with this name.
	 *
	 * @param username the name of the user
	 * @return true if the user was able to login, false otherwise.
	 */
	public boolean actionLogin(String username) {

		try {
			// check the username is not already taken
			if(this.server.usernameAlreadyTaken(username)) {
					this.send(new ServerMessageConnectionError("Ce pseudo déjà prit, ou le serveur en a empêché l'utilisation !"));
					this.server.removeClient(this);
					return false;
			}

			// check the username is not null
			if(username.trim().length() == 0) {
				this.send(new ServerMessageConnectionError("Pseudo invalide !"));
				this.server.removeClient(this);
				return false;
			}

			// check the username length
			if(username.length() > 20) {
				this.send(new ServerMessageConnectionError("Pseudo trop long !"));
				this.server.removeClient(this);
				return false;
			}
		}
		catch(IOException e) {
			actionLogout();
			return false;
		}
		return true;
	}

	/**
	 * Logouts the client of the server.
	 *
	 * @return true
	 */
	public boolean actionLogout() {
		// the client wants to log out
		this.server.removeClient(this);
		this.server.sendUserList();
		this.sendGoodbyeMessage();
		return true;
	}

}
