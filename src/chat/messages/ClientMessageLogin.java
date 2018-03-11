package chat.messages;

/**
 * This class represents a hello from the client to the server.
 */
public class ClientMessageLogin extends ClientMessage {

	/** The username */
	private String username;

	/**
	 * Instantiates a new client message login.
	 *
	 * @param username the username
	 */
	public ClientMessageLogin(String username) {
		this.username = username;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Returns the client message
	 * @return the client message
	 */
	public String toString() {
		return toString(username);
	}

}
