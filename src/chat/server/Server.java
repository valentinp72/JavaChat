package chat.server;

import chat.messages.*;

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

	private List<DataMessage> messages;
	private List<ClientThread> clients;
	private ServerSocket server;

	public Server() {
		try {
			this.host     = "127.0.0.1";
			this.port     = 5890;
			this.clients  = new ArrayList<ClientThread>();
			this.messages = new ArrayList<DataMessage>();

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

	public List<String> getUsers() {
		List<String> users = new ArrayList<String>();
		for(ClientThread client : clients) {
			if(client.getUsername() != null) {
				users.add(client.getUsername());
			}
		}
		return users;
	}

	public void sendUserList() {
		List<String> users = this.getUsers();
		this.sendServerMsgToClients(new ServerMessageUsers(users));
	}

	public void addMessage(DataMessage message) {
		this.messages.add(message);
		ServerMessageNewMessage msg = new ServerMessageNewMessage(message);
		this.sendServerMsgToClients(msg);
	}

	public void sendMessagesList() {
		ServerMessageMessages msgs = new ServerMessageMessages(this.messages);
		this.sendServerMsgToClients(msgs);
	}

	public void sendServerMsgToClients(ServerMessage msg) {
		for(ClientThread client : clients) {
			client.send(msg);
		}
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

}
