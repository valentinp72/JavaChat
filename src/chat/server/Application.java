package chat.server;

/**
 * The server application main programm.
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
	 * The main program.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Application app = new Application();
	}

}
