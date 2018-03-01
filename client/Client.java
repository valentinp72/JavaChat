import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;

import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Client {


	private InetAddress serverIP;
	private String      clientName;
	private int         serverPort;

	private Socket socket;
	private BufferedWriter writer;
	private BufferedInputStream reader;

	public Client(String serverIP, int serverPort, String clientName) 
		throws UnknownHostException, IOException {
			this.serverIP   = InetAddress.getByName(serverIP);
			this.serverPort = serverPort;
			this.clientName = clientName;
			this.socket     = connect();
	}

	private Socket connect() throws IOException {
		// Use system proxies
		System.setProperty("java.net.useSystemProxies", "true");
		
		Socket sock = new Socket(serverIP, serverPort);
	
		if(sock == null) {
			throw new IOException();
		}
		// outgoing messages
		this.writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

		// ingoing messages
		this.reader = new BufferedInputStream(sock.getInputStream());;

		// send the hello and the clientName to the server
		this.send(ClientMessage.CONNECT, this.clientName);

		return sock;
	}

	public void disconnect() {
		if(this.socket != null) {
			try {
				this.send(ClientMessage.DISCONNECT, null);
				socket.close();
				socket = null;
			}
			catch(IOException e) {
				// not important
			}

		}
	}

	public boolean send(String message) {
		message += "\r\n";
		try {
			writer.write(message);
			writer.flush();
		}
		catch(IOException e) {
			return false;
		}
		return true;
	}

	public boolean send(ClientMessage type, String value) {
		return this.send(type.toString() + value);
	}

}
