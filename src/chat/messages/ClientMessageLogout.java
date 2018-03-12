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

	}

	public void action(ClientThread clientThread) {
		clientThread.actionLogout();
	}

}
