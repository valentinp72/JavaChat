package chat.server.command;

import java.util.Map;
import java.util.List;

import chat.server.Server;
import chat.server.ClientThread;
import chat.messages.ServerMessageNewMessage;
import chat.messages.DataMessageInfo;
import chat.messages.DataMessage;
import chat.messages.ServerMessage;

public class CommandEmojis extends Command {

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

		String s = " = EMOJIS: = \n";

		s += " Pour entrer un emoji, il vous suffit d'entrer dans votre message les raccourcis correspondants aux smileys. Pour ne pas être interprétés comme des smileys, vous devez les précéder d'un point d'exclamation (!).\n\n";

		for(Map.Entry<String, String> pair : DataMessage.getEmojis().entrySet()) {
			s += " - " + pair.getValue() + " => !" + pair.getKey() + "\n";
		}

		return s;
	}

	public String usage() {
		return super.usage() + "emojis";
	}

}
