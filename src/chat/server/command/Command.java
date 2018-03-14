package chat.server.command;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;


import chat.server.Server;
import chat.messages.DataMessage;

public abstract class Command {

	public static final String PAS_ERREUR = "";

	public Command() {

	}

	public String[] splitFirst(DataMessage m) {
		return m.getMessage().split(" ", 2);
	}

	public List<String> getArgs(String[] msg) {
		try {
			return new LinkedList<String>(Arrays.asList(msg[1].split("\\s+")));
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return new LinkedList<String>();
		}
	}


	public abstract String execute(Server server, DataMessage original);

	public String usage() {
		return "\tUsage: /";
	}

}
