package chat.messages;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerMessageConnectionError.
 */
public class ServerMessageConnectionError extends ServerMessage {

	/** The explanation. */
	private String explanation;

	/**
	 * Instantiates a new server message connection error.
	 *
	 * @param explanation the explanation
	 */
	public ServerMessageConnectionError(String explanation) {
		this.explanation = explanation;
	}

	/**
	 * Gets the explanation.
	 *
	 * @return the explanation
	 */
	public String getExplanation() {
		return this.explanation;
	}

	/* (non-Javadoc)
	 * @see chat.messages.Message#toString()
	 */
	public String toString() {
		return toString(explanation);
	}

}
