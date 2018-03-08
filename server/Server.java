import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

	private int port;
	private String host;

	private List<ClientThread> clients;
	private ServerSocket server;

	public Server() {
		try {
			this.host    = "127.0.0.1";
			this.port    = 5890;
			this.clients = new ArrayList<ClientThread>();
			
			System.out.print("Démarrage du serveur " + this.host + ":" + this.port + "...");
			this.server = new ServerSocket(port, 100, InetAddress.getByName(host));
			System.out.println("fait!");

		} 
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void addClient(Socket client) {
		ClientThread thread = new ClientThread(client, this);
		clients.add(thread);
	}

	public void removeClient(ClientThread client) {
		clients.remove(client);
	}

	public void run() {
		while(true) {
			try {
				Socket clientSc = server.accept();
				this.addClient(clientSc);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Server s = new Server();
		s.run();
	}

}