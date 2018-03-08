package chat.messages;

public class ClientMessageLogin extends ClientMessage {

	private String username;

	public ClientMessageLogin(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public String toString() {
		return toString(username);
	}

}
