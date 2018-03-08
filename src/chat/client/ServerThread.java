package chat.client;

import chat.messages.*;


import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {

	private Client client;
	private Socket socket;
	private Thread thread;

	public ServerThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;

		this.thread = new Thread(this);
		this.thread.start();
	}

	public void run() {
		System.out.println("Connecté!");
		while(true) {
			try {
				ServerMessage message = client.read();
				System.out.println(message.toString());
				if(message instanceof ServerMessageMessages) {
					ServerMessageMessages msg = (ServerMessageMessages) message;
					//System.out.println(msg.getMessages());
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
