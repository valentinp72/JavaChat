package chat.server;

import chat.messages.*;

import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {

	private String username;
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
			this.username = loginMsg.getUsername();
			this.server.sendUserList();

			while(true) {
				ClientMessage message = this.read();
				if(message instanceof ClientMessageMessage) {
					ClientMessageMessage msg = (ClientMessageMessage) message;
					this.server.addMessage(new DataMessage(username, msg.getMessage()));
					this.server.sendMessagesList();
				}
				System.out.println(message.toString());
			}
		}
		catch(IOException e) {
			this.server.removeClient(this);
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

	public String getUsername() {
		return this.username;
	}

}
