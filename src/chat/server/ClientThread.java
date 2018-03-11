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
import chat.messages.DataUser;
import chat.messages.ServerMessage;
import chat.messages.ServerMessageConnectionError;


// TODO: Auto-generated Javadoc
/**
 * The Class ClientThread.
 */
public class ClientThread implements Runnable {

	/** The Constant ADMIN_USER. */
	private static final DataUser ADMIN_USER = new DataUser("");

	/** The user. */
	private DataUser user;
	
	/** The thread. */
	private Thread thread;
	
	/** The socket. */
	private Socket socket;
	
	/** The server. */
	private Server server;

	/** The output. */
	private ObjectOutputStream output;
	
	/** The input. */
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

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		System.out.println("Nouveau client!");
		try {
			ClientMessageLogin loginMsg = (ClientMessageLogin) this.read();

			if(this.server.getUsernames().contains(loginMsg.getUsername()) || loginMsg.getUsername() == ADMIN_USER.getUsername()) {
				this.send(new ServerMessageConnectionError("Pseudo déjà prit !"));
				this.server.removeClient(this);
				return;
			}
			if(loginMsg.getUsername().trim().length() == 0) {
				this.send(new ServerMessageConnectionError("Pseudo invalide !"));
				this.server.removeClient(this);
				return;
			}
			if(loginMsg.getUsername().length() > 20) {
				this.send(new ServerMessageConnectionError("Pseudo trop long !"));
				this.server.removeClient(this);
				return;
			}


			this.user = new DataUser(loginMsg.getUsername());
			this.server.sendUserList();
			this.server.sendMessagesList();
			this.sendWelcomeMessage();

			while(true) {
				ClientMessage message = this.read();
				if(message instanceof ClientMessageMessage) {
					ClientMessageMessage msg = (ClientMessageMessage) message;
					this.server.addMessage(new DataMessage(user, msg.getMessage()));
				}
				if(message instanceof ClientMessageLogout) {
					ClientMessageLogout msg = (ClientMessageLogout) message;
					this.server.removeClient(this);
					this.server.sendUserList();
					this.sendGoodbyeMessage();
					return;
				}
				System.out.println(message.toString());
			}
		}
		catch(IOException e) {
			this.server.removeClient(this);
			this.server.sendUserList();
			this.sendGoodbyeMessage();
		}
	}

	/**
	 * Send.
	 *
	 * @param msg the msg
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
	 * Read.
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
	 * Send welcome message.
	 */
	public void sendWelcomeMessage() {
		DataMessage msg = new DataMessage(ADMIN_USER, user.toString() + " vient de rejoindre.");
		this.server.addMessage(msg);
	}

	/**
	 * Send goodbye message.
	 */
	public void sendGoodbyeMessage() {
		DataMessage msg = new DataMessage(ADMIN_USER, user.toString() + " vient de quitter.");
		this.server.addMessage(msg);
	}

}
