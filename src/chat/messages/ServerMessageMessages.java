package chat.messages;

import java.util.List;

public class ServerMessageMessages extends ServerMessage {

	private List<String> messages;

	public ServerMessageMessages(List<String> messages) {
		this.messages = messages;
	}

	public List<String> getMessages() {
		return this.messages;
	}

	public String toString() {
		return toString(messages);
	}

}
