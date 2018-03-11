package chat.messages;

/**
 * This message is sent by the server when there is a connection error.
 */

public class ServerMessageConnectionError extends ServerMessage {

	/** The explanation of the error */
	private String explanation;

	/**
	 * Instantiates a new server connection error message.
	 *
	 * @param explanation the explanation of the errror
	 */
	public ServerMessageConnectionError(String explanation) {
		this.explanation = explanation;
	}

	/**
	 * Gets the explanation of the error
	 *
	 * @return the explanation
	 */
	public String getExplanation() {
		return this.explanation;
	}

	/**
	 * Returns the server message
	 * @return the server message
	 */
	public String toString() {
		return toString(explanation);
	}

}
