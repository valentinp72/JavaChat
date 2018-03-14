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
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		this.thread  = new Thread(this);
		this.thread.start();
		this.running = true;
	}

	/**
	 * Runs the thread. This wait for a welcome message, and then wait for messages from the client.
	 */
	public void run() {
		try {

			// hello message
			ClientMessageLogin loginMsg = (ClientMessageLogin) this.read();

			loginMsg.action(this);
			System.out.println(loginMsg.toString());

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
	 */
	public void send(ServerMessage msg) {
		try {
			output.reset();
			output.writeObject(msg);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
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

	public void sendNewMessage(DataMessage message) {
		this.server.addMessage(message);
	}

	public void actionLogin(String username) {

		// check the username is not already taken
		if(this.server.getUsernames().contains(username) || username == server.ADMIN_USER.getUsername()) {
			this.send(new ServerMessageConnectionError("Pseudo déjà prit !"));
			this.server.removeClient(this);
		}

		// check the username is not null
		if(username.trim().length() == 0) {
			this.send(new ServerMessageConnectionError("Pseudo invalide !"));
			this.server.removeClient(this);
		}

		// check the username length
		if(username.length() > 20) {
			this.send(new ServerMessageConnectionError("Pseudo trop long !"));
			this.server.removeClient(this);
		}

	}

	public void actionLogout() {
		// the client wants to log out
		this.server.removeClient(this);
		this.server.sendUserList();
		this.sendGoodbyeMessage();
	}

}
