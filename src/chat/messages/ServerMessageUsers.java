package chat.messages;

import java.util.List;

public class ServerMessageUsers extends ServerMessage {

	private List<DataUser> users;

	public ServerMessageUsers(List<DataUser> users) {
		this.users = users;
	}

	public List<DataUser> getUsers() {
		return this.users;
	}

	public String toString() {
		return toString(users);
	}

}
