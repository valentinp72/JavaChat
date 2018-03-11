package chat.client;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import chat.messages.ServerMessage;
import chat.messages.ServerMessageConnectionError;
import chat.messages.ServerMessageMessages;
import chat.messages.ServerMessageNewMessage;
import chat.messages.ServerMessageUsers;

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
		this.thread.interrupt();
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
						else if(message instanceof ServerMessageConnectionError) {
							ServerMessageConnectionError msg = (ServerMessageConnectionError) message;
							actions.connectionError(msg.getExplanation());
						}

					}
					System.out.println("action:" + actions);


				}
				catch(SocketException e) {
					thread.currentThread().interrupt();
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
