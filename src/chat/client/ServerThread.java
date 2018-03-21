package chat.client;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import chat.messages.ServerMessage;
import chat.messages.ServerMessageConnectionError;
import chat.messages.ServerMessageMessages;
import chat.messages.ServerMessageNewMessage;
import chat.messages.ServerMessageUsers;

/**
 * This class runs a thread that listens for server messages
 */

public class ServerThread implements Runnable {

	/** The client */
	private Client client;

	/** The socket */
	private Socket socket;

	/** The thread */
	private Thread thread;

	/** True if the thread is running */
	private boolean running;

	/**
	 * Instantiates a new server thread.
	 *
	 * @param socket the socket
	 * @param client the client
	 */
	public ServerThread(Socket socket, Client client) {
		this.socket  = socket;
		this.client  = client;

		this.thread  = new Thread(this);
		this.thread.start();
		this.running = true;
	}

	/**
	 * Stop to listen messages.
	 */
	public void stop() {
		this.running = false;
		this.thread.interrupt();
	}

	/*
	 * Run the thread. This gets messages from the server
	 * and take actions from them.
	 */
	public void run() {
		try {
			while(running) {

				try {
					ActionsMessages actions = client.getActionsMessages();

					// we wait to be ready to make actions
					if(actions != null) {
						ServerMessage message = client.read();

						// we do the action
						message.action(actions);
					}
					/*
					else {
						// we wait a little bit more
						try {
							Thread.sleep(100);

						}
						catch(InterruptedException e) {
							// pas grave
						}
					}
					*/

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
