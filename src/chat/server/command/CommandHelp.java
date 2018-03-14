package chat.server.command;

import java.util.List;

import chat.server.Server;
import chat.server.ClientThread;
import chat.messages.ServerMessageNewMessage;
import chat.messages.DataMessageInfo;
import chat.messages.DataMessage;
import chat.messages.ServerMessage;

public class CommandHelp extends Command {

	public String execute(Server server, DataMessage original) {

		ClientThread sender;
		try {
			sender = server.getClientByName(original.getUser().getUsername());
		}
		catch(IllegalArgumentException e) {
			return e.getMessage() + "\n" + usage();
		}

		// we send him the message, and also to the sender
		DataMessage message = new DataMessageInfo(original.getUser(), this.content());
		ServerMessage msg   = new ServerMessageNewMessage(message);
		sender.send(msg);

		return PAS_ERREUR;
	}

	public String content() {

		String[] commandes = {"help", "msg", "info"};

		String s = " = AIDE: = \n\n";

		s += " - Commandes:\n";
		for(String cmd : commandes) {
			s +=   "    > /" + cmd + " : " + infoCommand(cmd) + "\n";
		}

		return s;
	}

	public String infoCommand(String command) {
		switch(command) {
			case "help":
				return "affiche ce menu d'aide";
			case "msg" :
				return "envoi d'un message privé à un utilisateur";
			case "info" :
				return "envoi d'un message d'information à tout le monde";

			default:
				return "aide non trouvée !";
		}
	}

	public String usage() {
		return super.usage() + "help";
	}

}
