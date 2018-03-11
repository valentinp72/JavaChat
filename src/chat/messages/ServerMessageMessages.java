package chat.messages;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerMessageMessages.
 */
public class ServerMessageMessages extends ServerMessage {

	/** The messages. */
	private List<DataMessage> messages;

	/**
	 * Instantiates a new server message messages.
	 *
	 * @param messages the messages
	 */
	public ServerMessageMessages(List<DataMessage> messages) {
		this.messages = messages;
	}

	/**
	 * Gets the messages.
	 *
	 * @return the messages
	 */
	public List<DataMessage> getMessages() {
		return this.messages;
	}

	/* (non-Javadoc)
	 * @see chat.messages.Message#toString()
	 */
	public String toString() {
		return toString(messages);
	}

}
