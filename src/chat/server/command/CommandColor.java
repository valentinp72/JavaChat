package chat.server.command;

import java.util.List;

import chat.server.Server;
import chat.messages.ServerMessageNewMessage;
import chat.messages.DataMessageInfo;
import chat.messages.DataMessage;
import chat.messages.ServerMessage;

/**
 * This class represents a command allowing a user to change the color of his name.
 */

public class CommandColor extends Command {

	/**
	 * Execute the command on the server.
	 *
	 * @param original the message containing the command
	 * @return a String saying the error (argument not correct), or PAS_ERREUR
	 */
	public String execute(Server server, DataMessage original) {

		String msgs[] = this.splitFirst(original);
		List<String> arguments = this.getArgs(msgs);


		// check the number of arguments
		if(arguments.size() != 1)
			return "mauvais nombre d'arguments\n" + usage();

		// try to set the new color
		try {
			original.getUser().setColor(arguments.get(0));
		}
		catch(NumberFormatException e) {
			return "couleur non valide\n" + usage();
		}

		server.sendUserList();

		return PAS_ERREUR;
	}

	/**
	 * Returns the usage of the current command.
	 *
	 * @return the usage of the command
	 */
	public String usage() {
		return super.usage() + "color <nouvelle couleur #rrggbb>";
	}

}
