package chat.messages;

import java.util.List;

/**
 * This class represents a message of the server when it's time for
 * the clients to update the user list.
 */

public class ServerMessageUsers extends ServerMessage {

	/** The users */
	private List<DataUser> users;

	/**
	 * Instantiates a new server users message.
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

	/**
	 * Returns the server message
	 * @return the server message
	 */
	public String toString() {
		return toString(users);
	}

}
