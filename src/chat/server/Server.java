package chat.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import chat.messages.DataMessage;
import chat.messages.DataUser;
import chat.messages.ServerMessage;
import chat.messages.ServerMessageMessages;
import chat.messages.ServerMessageNewMessage;
import chat.messages.ServerMessageUsers;

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

	public List<DataUser> getUsers() {
		List<DataUser> users = new ArrayList<DataUser>();
		for(ClientThread client : clients) {
			if(client.getUser() != null) {
				users.add(client.getUser());
			}
		}
		return users;
	}

	public List<String> getUsernames() {
		List<String> usernames = new ArrayList<String>();
		for(DataUser user : this.getUsers()) {
			usernames.add(user.toString());
		}
		return usernames;
	}

	public void sendUserList() {
		List<DataUser> users = this.getUsers();
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
