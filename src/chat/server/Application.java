package chat.server;

public class Application {

	public Application() {
		Server server = new Server();
		server.run();
	}

	public static void main(String[] args) {
		Application app = new Application();
	}

}
