package chat.server.command;

import java.util.List;
import java.io.IOException;

import chat.server.Server;
import chat.server.ClientThread;
import chat.messages.ServerMessageNewMessage;
import chat.messages.DataMessageInfo;
import chat.messages.DataMessage;
import chat.messages.ServerMessage;

public class CommandMsg extends Command {

	public String execute(Server server, DataMessage original) {
		String[] msgs = this.splitFirst(original);
		List<String> arguments = this.getArgs(msgs);

		// check the number of arguments
		if(arguments.size() < 2)
			return "argument manquant\n" + usage();

		String username = arguments.get(0);
		arguments.remove(0);

		// we get the concerned client and sender
		ClientThread client;
		ClientThread sender;
		try {
			client = server.getClientByName(username);
			sender = server.getClientByName(original.getUser().getUsername());
		}
		catch(IllegalArgumentException e) {
			return e.getMessage() + "\n" + usage();
		}

		// we send him the message, and also to the sender
		String realMessage = String.join(" ", arguments);
		//DataMessage messageC = new DataMessageInfo(original.getUser(), "@" + original.getUser().getUsername() + ": " + realMessage);
		DataMessage messageS = new DataMessageInfo(original.getUser(), "@" + username + ": " + realMessage);

		//ServerMessage msgC   = new ServerMessageNewMessage(messageC);
		ServerMessage msgS   = new ServerMessageNewMessage(messageS);
		try {
			sender.send(msgS);
			client.send(msgS);
		}
		catch(IOException e) {
			e.printStackTrace();
		}


		return PAS_ERREUR;
	}

	public String usage() {
		return super.usage() + "msg <pseudo> <message>";
	}

}
