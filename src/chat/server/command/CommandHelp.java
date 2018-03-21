package chat.server.command;

import java.util.List;
import java.io.IOException;

import chat.server.Server;
import chat.server.ClientThread;
import chat.messages.ServerMessageNewMessage;
import chat.messages.DataMessageInfo;
import chat.messages.DataMessage;
import chat.messages.ServerMessage;

/**
 * This class represents a command listing to the user all the available commands.
 */

public class CommandHelp extends Command {

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

		String[] commandes = {"help", "msg", "info", "color", "emojis"};

		String s = " = AIDE: = \n\n";

		s += " - Commandes:\n";
		for(String cmd : commandes) {
			s +=   "    > /" + cmd + " : " + infoCommand(cmd) + "\n";
		}

		return s;
	}

	/**
	 * Returns an information about a given command.
	 *
	 * @param command the command to retreive the information
	 * @return an information
	 */
	public String infoCommand(String command) {
		switch(command) {
			case "help":
				return "affiche ce menu d'aide";
			case "msg":
				return "envoi d'un message privé à un utilisateur";
			case "info":
				return "envoi d'un message d'information à tout le monde";
			case "color":
				return "changement de la couleur du pseudo";
			case "emojis":
				return "affichage de la liste des emojis";

			default:
				return "aide non trouvée !";
		}
	}

	/**
	 * Returns the usage of the current command.
	 *
	 * @return the usage of the command
	 */
	public String usage() {
		return super.usage() + "help";
	}

}
