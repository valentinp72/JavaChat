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
		try {
			while(true) {

				ServerMessage message = client.read();

				System.out.println(message.toString());
				if(message instanceof ServerMessageMessages) {
					ServerMessageMessages msg = (ServerMessageMessages) message;
					client.getActionsMessages().setMessages(msg.getMessages());
				}
				else if(message instanceof ServerMessageNewMessage) {
					ServerMessageNewMessage msg = (ServerMessageNewMessage) message;
					client.getActionsMessages().newMessage(msg.getMessage());
				}
				else if(message instanceof ServerMessageUsers) {
					ServerMessageUsers msg = (ServerMessageUsers) message;
					client.getActionsMessages().setUsers(msg.getUsers());
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
