package chat.client;

import chat.messages.*;

import java.util.List;

import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {

	private Client client;
	private Socket socket;
	private Thread thread;
	private boolean running;

	public ServerThread(Socket socket, Client client) {
		this.socket  = socket;
		this.client  = client;
		this.running = true;

		this.thread  = new Thread(this);
		this.thread.start();
	}

	public void stop() {
		this.running = false;
		this.thread.stop();
	}

	public void run() {
		System.out.println("Connecté!");
		try {
			while(running) {

				try {
					ActionsMessages actions = client.getActionsMessages();

					// we wait to be ready to make actions
					if(actions != null) {
						ServerMessage message = client.read();

						System.out.println(message.toString());
						if(message instanceof ServerMessageMessages) {
							ServerMessageMessages msg = (ServerMessageMessages) message;
							actions.setMessages(msg.getMessages());
						}
						else if(message instanceof ServerMessageNewMessage) {
							ServerMessageNewMessage msg = (ServerMessageNewMessage) message;
							actions.newMessage(msg.getMessage());
						}
						else if(message instanceof ServerMessageUsers) {
							ServerMessageUsers msg = (ServerMessageUsers) message;
							actions.setUsers(msg.getUsers());
						}
					}

				}
				catch (InterruptedIOException ex) {
					System.out.println("interrupted");
					thread.currentThread().interrupt(); // Très important de réinterrompre
					break; // Sortie de la boucle infinie
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
