package chat.messages;

import java.util.List;

public class ServerMessageMessages extends ServerMessage {

	private List<DataMessage> messages;

	public ServerMessageMessages(List<DataMessage> messages) {
		this.messages = messages;
	}

	public List<DataMessage> getMessages() {
		return this.messages;
	}

	public String toString() {
		return toString(messages);
	}

}
