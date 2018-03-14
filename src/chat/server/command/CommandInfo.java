package chat.server.command;

import java.util.List;

import chat.server.Server;
import chat.messages.ServerMessageNewMessage;
import chat.messages.DataMessageInfo;
import chat.messages.DataMessage;
import chat.messages.ServerMessage;

public class CommandInfo extends Command {

	public String execute(Server server, DataMessage original) {

		String args[] = this.splitFirst(original);


		// check the number of arguments
		if(args.length < 2)
			return "argument manquant\n" + usage();

		DataMessage message = new DataMessageInfo(server.INFO_USER, args[1]);
		ServerMessage msg   = new ServerMessageNewMessage(message);

		server.sendServerMsgToClients(msg);

		return PAS_ERREUR;
	}

	public String usage() {
		return super.usage() + "info <message>";
	}

}
