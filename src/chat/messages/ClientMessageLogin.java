package chat.messages;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientMessageLogin.
 */
public class ClientMessageLogin extends ClientMessage {

	/** The username. */
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

	/* (non-Javadoc)
	 * @see chat.messages.Message#toString()
	 */
	public String toString() {
		return toString(username);
	}

}
