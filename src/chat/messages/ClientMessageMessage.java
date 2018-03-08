package chat.messages;

public class ClientMessageMessage extends ClientMessage {

	private String message;

	public ClientMessageMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public String toString() {
		return toString(message);
	}

}
