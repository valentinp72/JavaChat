package chat.server.command;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import chat.server.Server;
import chat.server.ClientThread;
import chat.messages.ServerMessageNewMessage;
import chat.messages.DataMessageInfo;
import chat.messages.DataMessage;
import chat.messages.ServerMessage;

/**
 * This class represents a command listing to the user all the available emojis and their shortcuts.
 */

public class CommandEmojis extends Command {

	/**
	 * Execute the command on the server.
	 *
	 * @param original the message containing the command
	 * @return PAS_ERREUR
	 */
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
		try {
			sender.send(msg);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		return PAS_ERREUR;
	}

	/**
	 * Returns the content of the help to display
	 *
	 * @return the content
	 */
	public String content() {

		String s = " = EMOJIS: = \n";

		s += " Pour entrer un emoji, il vous suffit d'entrer dans votre message les raccourcis correspondants aux smileys. Pour ne pas être interprétés comme des smileys, vous devez les précéder d'un autre deux point (par exemple :::joy: au lieu de ::joy: ).\n\n";

		for(Map.Entry<String, String> pair : DataMessage.getEmojis().entrySet()) {
			s += " - " + pair.getValue() + " => :" + pair.getKey() + "\n";
		}

		return s;
	}

	/**
	 * Returns the usage of the current command.
	 *
	 * @return the usage of the command
	 */
	public String usage() {
		return super.usage() + "emojis";
	}

}
