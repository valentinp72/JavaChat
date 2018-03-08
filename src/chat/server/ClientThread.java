package chat.server;

import chat.messages.ClientMessage;

import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {

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
			while(true) {
				ClientMessage message = this.read();
				System.out.println(message.toString());
			}
		}
		catch(IOException e) {
			this.server.removeClient(this);
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

}
