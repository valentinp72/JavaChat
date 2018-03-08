package chat.client;

import chat.messages.ClientMessage;
import chat.messages.ClientMessageLogin;
import chat.messages.ClientMessageLogout;
import chat.messages.ClientMessageMessage;

import chat.messages.ServerMessage;
import chat.messages.ServerMessageUsers;

import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {

	private Client client;
	private Socket socket;
	private Thread thread;

	public ServerThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;

		this.thread = new Thread(this);
		this.thread.start();
	}

	public void run() {
		System.out.println("Connecté!");
		while(true) {
			try {
				ServerMessage message = client.read();
				System.out.println(message.toString());
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
