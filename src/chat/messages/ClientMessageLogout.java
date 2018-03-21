package chat.messages;

import chat.server.ClientThread;

/**
 * This class represents a message the client sends to the server to tell goodbye.
 */
public class ClientMessageLogout extends ClientMessage {

	/**
	 * Instantiates a new client message logout.
	 */
	public ClientMessageLogout() {
		// nothing
	}

	/**
	 * The action to be done when the server wants to execute the message
	 *
	 * @param clientThread the thread that received this message
	 */
	public boolean action(ClientThread clientThread) {
		return clientThread.actionLogout();
	}

}
