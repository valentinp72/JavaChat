package chat.messages;

public class ServerMessageNewMessage extends ServerMessage {

	private DataMessage message;

	public ServerMessageNewMessage(DataMessage message) {
		this.message = message;
	}

	public DataMessage getMessage() {
		return this.message;
	}

	public String toString() {
		return toString(message);
	}

}
