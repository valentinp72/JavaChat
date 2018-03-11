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

// TODO: Auto-generated Javadoc
/**
 * The Class Client.
 */
public class Client {

	/** The server IP. */
	private InetAddress serverIP;
	
	/** The client name. */
	private String      clientName;
	
	/** The server port. */
	private int         serverPort;

	/** The socket. */
	private Socket socket;
	
	/** The writer. */
	private ObjectOutputStream writer;
	
	/** The reader. */
	private ObjectInputStream  reader;
	
	/** The thread. */
	private ServerThread thread;

	/** The actions messages. */
	private ActionsMessages actionsMessages;

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
	 * Connect.
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
	 * Disconnect.
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
	 * Send.
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
	 * Read.
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
	 * Sets the action messages.
	 *
	 * @param action the new action messages
	 */
	public void setActionMessages(ActionsMessages action) {
		this.actionsMessages = action;
	}

	/**
	 * Gets the actions messages.
	 *
	 * @return the actions messages
	 */
	public ActionsMessages getActionsMessages() {
		return this.actionsMessages;
	}

}
