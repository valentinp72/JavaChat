package chat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import chat.messages.ClientMessage;
import chat.messages.ClientMessageLogin;
import chat.messages.ClientMessageLogout;
import chat.messages.ServerMessage;

/**
 * The main client class. This class takes care of receiving and sending
 * messages to the server. It also connect and disconnect to the servers.
 */
public class Client {

	/** The server IP */
	private InetAddress serverIP;

	/** The client name */
	private String clientName;

	/** The server port */
	private int serverPort;

	/** The server socket */
	private Socket socket;

	/** The writer to the server */
	private ObjectOutputStream writer;

	/** The reader to the server */
	private ObjectInputStream reader;

	/** The thread the client will be listening for the server */
	private ServerThread thread;

	/** The actions to do when messages are comming */
	private volatile ActionsMessages actionsMessages;

	/**
	 * Instantiates a new client.
	 *
	 * @param serverIP the server IP
	 * @param serverPort the server port
	 * @param clientName the client name
	 * @throws UnknownHostException the unknown host exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Client(String serverIP, int serverPort, String clientName)
		throws UnknownHostException, IOException {
			this.serverIP   = InetAddress.getByName(serverIP);
			this.serverPort = serverPort;
			this.clientName = clientName;
			this.socket     = connect();
			this.thread     = new ServerThread(this.socket, this);
	}

	/**
	 * Connect to the server by creating a new socket.
	 *
	 * @return the socket
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Socket connect() throws IOException {
		// Use system proxies
		System.setProperty("java.net.useSystemProxies", "true");

		Socket sock = new Socket(serverIP, serverPort);

		if(sock == null) {
			throw new IOException();
		}
		// outgoing messages
		this.writer = new ObjectOutputStream(sock.getOutputStream());

		// ingoing messages
		this.reader = new ObjectInputStream(sock.getInputStream());;

		// send the hello and the clientName to the server
		this.send(new ClientMessageLogin(this.clientName));

		return sock;
	}

	/**
	 * Disconnect to the server.
	 */
	public void disconnect() {
		if(this.socket != null) {
			try {
				this.send(new ClientMessageLogout());
				this.reader.close();
				this.writer.close();
				this.thread.stop();
				this.socket.close();
				socket = null;
			}
			catch(IOException e) {
				// not important
			}
		}
	}

	/**
	 * Send a ClientMessage to the server
	 *
	 * @param message the message
	 * @return true, if successful
	 */
	public boolean send(ClientMessage message) {
		try {
			writer.reset();
			writer.writeObject(message);
			return true;
		}
		catch(IOException e) {
			return false;
		}
	}

	/**
	 * Read a ServerMessage from the server. This is a blocking method.
	 * If there is an error (the received message is not a ServerMessage)
	 * this returns null.
	 *
	 * @return the server message
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ServerMessage read() throws IOException {
		try {
			return (ServerMessage) reader.readObject();
		}
		catch(ClassNotFoundException e) {
			return null;
		}
	}

	/**
	 * Sets the action to be done when receiving messages.
	 *
	 * @param action the new actions
	 */
	public void setActionMessages(ActionsMessages action) {
		this.actionsMessages = action;
	}

	/**
	 * Gets the actions.
	 *
	 * @return the actions
	 */
	public ActionsMessages getActionsMessages() {
		return this.actionsMessages;
	}

	/**
	 * Gets the client name.
	 *
	 * @return the client name
	 */
	public String getClientName() {
		return this.clientName;
	}

}
