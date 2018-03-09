package chat.client;


import chat.messages.*;

import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Client {

	private InetAddress serverIP;
	private String      clientName;
	private int         serverPort;

	private Socket socket;
	private ObjectOutputStream writer;
	private ObjectInputStream  reader;
	private ServerThread thread;

	private ActionsMessages actionsMessages;

	public Client(String serverIP, int serverPort, String clientName)
		throws UnknownHostException, IOException {
			this.serverIP   = InetAddress.getByName(serverIP);
			this.serverPort = serverPort;
			this.clientName = clientName;
			this.socket     = connect();
			this.thread     = new ServerThread(this.socket, this);
	}

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

	public void disconnect() {
		if(this.socket != null) {
			try {
				this.send(new ClientMessageLogout());
				socket.close();
				socket = null;
			}
			catch(IOException e) {
				// not important
			}
		}
	}

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

	public ServerMessage read() throws IOException {
		try {
			return (ServerMessage) reader.readObject();
		}
		catch(ClassNotFoundException e) {
			return null;
		}
	}

	public void setActionMessages(ActionsMessages action) {
		this.actionsMessages = action;
	}

	public ActionsMessages getActionsMessages() {
		return this.actionsMessages;
	}

}
