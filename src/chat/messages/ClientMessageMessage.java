package chat.messages;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientMessageMessage.
 */
public class ClientMessageMessage extends ClientMessage {

	/** The message. */
	private String message;

	/**
	 * Instantiates a new client message message.
	 *
	 * @param message the message
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

	/* (non-Javadoc)
	 * @see chat.messages.Message#toString()
	 */
	public String toString() {
		return toString(message);
	}

}
