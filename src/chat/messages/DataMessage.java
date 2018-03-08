package chat.messages;

import java.io.Serializable;

public class DataMessage implements Serializable {
	private String username;
	private String message;

	public DataMessage(String username, String message) {
		this.username = username;
		this.message  = message;
	}

	public String toString() {
		return "[" + this.username + "] - " + this.message;
	}
}
