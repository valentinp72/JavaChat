package chat.messages;

/**
 * This class represents a message the server send to the
 * clients when there is a new message on the chat.
 */
public class ServerMessageNewMessage extends ServerMessage {

	/** The new message */
	private DataMessage message;

	/**
	 * Instantiates a new server new message messages (lol "_").
	 *
	 * @param message the message
	 */
	public ServerMessageNewMessage(DataMessage message) {
		this.message = message;
	}

	/**
	 * Gets the new message.
	 *
	 * @return the message
	 */
	public DataMessage getMessage() {
		return this.message;
	}

	/**
	 * Returns the server message
	 * @return the server message
	 */
	public String toString() {
		return toString(message);
	}

}
