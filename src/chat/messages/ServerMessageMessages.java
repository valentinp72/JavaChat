package chat.messages;

import java.util.List;

/**
 * This class represents a message the server send to tell the clients all the messages of the chat.
 */
public class ServerMessageMessages extends ServerMessage {

	/** The messages */
	private List<DataMessage> messages;

	/**
	 * Instantiates a new server messages message.
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

	/**
	 * Returns the server message
	 * @return the server message
	 */
	public String toString() {
		return toString(messages);
	}

}
