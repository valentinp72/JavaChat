package chat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

	/** The user the welcome and goodbye messages are sent by */
	private static final DataUser ADMIN_USER = new DataUser("");

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

		this.thread = new Thread(this);
		this.thread.start();
	}

	/**
	 * Runs the thread. This wait for a welcome message, and then wait for messages from the client.
	 */
	public void run() {
		try {

			// hello message
			ClientMessageLogin loginMsg = (ClientMessageLogin) this.read();

			// check the username is not already taken
			if(this.server.getUsernames().contains(loginMsg.getUsername()) || loginMsg.getUsername() == ADMIN_USER.getUsername()) {
				this.send(new ServerMessageConnectionError("Pseudo déjà prit !"));
				this.server.removeClient(this);
				return;
			}

			// check the username is not null
			if(loginMsg.getUsername().trim().length() == 0) {
				this.send(new ServerMessageConnectionError("Pseudo invalide !"));
				this.server.removeClient(this);
				return;
			}

			// check the username length
			if(loginMsg.getUsername().length() > 20) {
				this.send(new ServerMessageConnectionError("Pseudo trop long !"));
				this.server.removeClient(this);
				return;
			}

			// that seemed okay

			this.user = new DataUser(loginMsg.getUsername()); // we create the real user
			this.server.sendUserList();     // we send the updated user list to the clients
			this.send(new ServerMessageMessages(server.getMessages())); // we send the messages to the client
			this.sendWelcomeMessage();      // we send the welcome message

			while(true) {

				// we wait until we receive a message from the client
				ClientMessage message = this.read();

				if(message instanceof ClientMessageMessage) {
					ClientMessageMessage msg = (ClientMessageMessage) message;

					if(msg.getMessage().length() > 0) {
						// the client sent a new message
						this.server.addMessage(new DataMessage(user, msg.getMessage()));
					}
				}

				if(message instanceof ClientMessageLogout) {
					ClientMessageLogout msg = (ClientMessageLogout) message;

					// the client wants to log out
					this.server.removeClient(this);
					this.server.sendUserList();
					this.sendGoodbyeMessage();
					return;
				}
				System.out.println(message.toString());
			}
		}
		catch(IOException e) {
			// we remove the client
			this.server.removeClient(this);
			this.server.sendUserList();
			this.sendGoodbyeMessage();
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
		DataMessage msg = new DataMessageInfo(ADMIN_USER, user.toString() + " vient de rejoindre.");
		this.server.addMessage(msg);
	}

	/**
	 * Send goodbye message to all.
	 */
	public void sendGoodbyeMessage() {
		DataMessage msg = new DataMessageInfo(ADMIN_USER, user.toString() + " vient de quitter.");
		this.server.addMessage(msg);
	}

}
