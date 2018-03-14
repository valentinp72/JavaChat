package chat.server.command;

import java.util.List;

import chat.server.Server;
import chat.messages.ServerMessageNewMessage;
import chat.messages.DataMessageInfo;
import chat.messages.DataMessage;
import chat.messages.ServerMessage;

public class CommandColor extends Command {

	public String execute(Server server, DataMessage original) {

		String msgs[] = this.splitFirst(original);
		List<String> arguments = this.getArgs(msgs);


		// check the number of arguments
		if(arguments.size() != 1)
			return "mauvais nombre d'arguments\n" + usage();

		try {
			original.getUser().setColor(arguments.get(0));
		}
		catch(NumberFormatException e) {
			return "couleur non valide\n" + usage();
		}

		server.sendUserList();

		return PAS_ERREUR;
	}

	public String usage() {
		return super.usage() + "color <nouvelle couleur #rrggbb>";
	}

}
