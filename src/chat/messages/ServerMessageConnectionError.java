package chat.messages;

public class ServerMessageConnectionError extends ServerMessage {

	private String explanation;

	public ServerMessageConnectionError(String explanation) {
		this.explanation = explanation;
	}

	public String getExplanation() {
		return this.explanation;
	}

	public String toString() {
		return toString(explanation);
	}

}
