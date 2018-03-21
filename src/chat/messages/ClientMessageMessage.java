package chat.messages;

import chat.server.ClientThread;

/**
 * This class represents a message the client send to the server to send a new message.
 */
public class ClientMessageMessage extends ClientMessage {

	/** The message */
	private String message;

	/**
	 * Instantiates a new client message message.
	 *
	 * @param message the message to be send
	 */
	public ClientMessageMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Returns the client message
	 * @return the client message
	 */
	public String toString() {
		return toString(message);
	}

	/**
	 * The action to be done when the server wants to execute the message
	 *
	 * @param clientThread the thread that received this message
	 */
	public boolean action(ClientThread clientThread) {
		if(message.length() > 0) {
			// the client sent a new message
			return clientThread.sendNewMessage(new DataMessage(clientThread.getUser(), this.message));
		}
		return false;
	}

}
