package chat.messages;

import java.util.List;

public class ServerMessageUsers extends ServerMessage {

	private List<String> users;

	public ServerMessageUsers(List<String> users) {
		this.users = users;
	}

	public List<String> getUsers() {
		return this.users;
	}

	public String toString() {
		return toString(users);
	}

}
