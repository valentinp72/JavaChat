package chat.messages;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerMessageUsers.
 */
public class ServerMessageUsers extends ServerMessage {

	/** The users. */
	private List<DataUser> users;

	/**
	 * Instantiates a new server message users.
	 *
	 * @param users the users
	 */
	public ServerMessageUsers(List<DataUser> users) {
		this.users = users;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public List<DataUser> getUsers() {
		return this.users;
	}

	/* (non-Javadoc)
	 * @see chat.messages.Message#toString()
	 */
	public String toString() {
		return toString(users);
	}

}
