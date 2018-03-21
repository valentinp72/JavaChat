package chat.server;
import java.util.Arrays;
import java.net.BindException;
import java.net.UnknownHostException;

/**
 * The server application main programm.
 */

public class Application {

	/**
	 * Instantiates a new application.
	 */
	public Application(String host, int port) {
		try {
			Server server = new Server(host, port);
			server.run();
		}
		catch(BindException e) {
			System.out.println("\nErreur: impossible d'assigner l'adresse et le port au serveur (" + e.getMessage() + ")");
		}
		catch(UnknownHostException e) {
			System.out.println("\nErreur: l'hôte du serveur n'as pas pu être déterminée (" + e.getMessage() + ")");
		}
		catch(Exception e) {
			System.out.println("\nErreur avec le serveur: \n\t" + e);
		}
	}

	/**
	 * The main program.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		// default values
		String host = "127.0.0.1";
		int    port = 5890;

		try {
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		catch(IndexOutOfBoundsException e) {
			// nothing, we just keep the default values
		}
		catch(NumberFormatException e) {
			// nothing, we just keep the default values
		}

		Application app = new Application(host, port);
	}

}
