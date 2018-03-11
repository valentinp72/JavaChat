package chat.server;

import chat.messages.*;

import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {

	private static final DataUser ADMIN_USER = new DataUser("");

	private DataUser user;
	private Thread thread;
	private Socket socket;
	private Server server;

	private ObjectOutputStream output;
	private ObjectInputStream  input;

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

	public void send(ServerMessage msg) {
		try {
			output.reset();
			output.writeObject(msg);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private ClientMessage read() throws IOException {
		try {
			return (ClientMessage) input.readObject();
		}
		catch(ClassNotFoundException e) {
			return null;
		}
	}

	public DataUser getUser() {
		return this.user;
	}

	public void sendWelcomeMessage() {
		DataMessage msg = new DataMessage(ADMIN_USER, user.toString() + " vient de rejoindre.");
		this.server.addMessage(msg);
	}

	public void sendGoodbyeMessage() {
		DataMessage msg = new DataMessage(ADMIN_USER, user.toString() + " vient de quitter.");
		this.server.addMessage(msg);
	}

}
