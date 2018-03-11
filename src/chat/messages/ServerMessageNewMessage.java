package chat.messages;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerMessageNewMessage.
 */
public class ServerMessageNewMessage extends ServerMessage {

	/** The message. */
	private DataMessage message;

	/**
	 * Instantiates a new server message new message.
	 *
	 * @param message the message
	 */
	public ServerMessageNewMessage(DataMessage message) {
		this.message = message;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public DataMessage getMessage() {
		return this.message;
	}

	/* (non-Javadoc)
	 * @see chat.messages.Message#toString()
	 */
	public String toString() {
		return toString(message);
	}

}
