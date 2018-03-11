package chat.messages;

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

}
