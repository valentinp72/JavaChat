package chat.server;

import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {

	private Thread thread;
	private Socket socket;
	private Server server;

	private PrintWriter         output;
	private BufferedInputStream input;

	public ClientThread(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;

		try {
			this.output = new PrintWriter(socket.getOutputStream());
			this.input  = new BufferedInputStream(socket.getInputStream());
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		this.thread = new Thread(this);
		this.thread.start();
	}

	public void run() {
		System.out.println("Nouveau client!");
		try {
			while(true) {
				String message = this.read();
				System.out.println("'" + message + "'");
			}
		}
		catch(IOException e) {
			this.server.removeClient(this);
		}
	}


	private String read() throws IOException{
		String message = "";
		byte[] b = new byte[4096];
		int stream = input.read(b);
		message = new String(b, 0, stream);
		return message;
	}

}
