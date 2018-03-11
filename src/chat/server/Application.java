package chat.server;

// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 */
public class Application {

	/**
	 * Instantiates a new application.
	 */
	public Application() {
		Server server = new Server();
		server.run();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Application app = new Application();
	}

}
