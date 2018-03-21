package chat.server.command;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

import chat.server.Server;
import chat.messages.DataMessage;

/**
 * This class represents a command that can be executed
 * by the client on the server.
 * It is possible to get the given arguments of the command.
 */
public abstract class Command {

	/**
	 * This is a string saying there were no error during the process of the command
	 */
	public static final String PAS_ERREUR = "";

	/**
	 * Returns an array containing in the first place
	 * the name of the command, and in the other place
	 * a string of all arguments.
	 *
	 * @param m the message
	 * @return the command and arguments
	 */
	public String[] splitFirst(DataMessage m) {
		return m.getMessage().split(" ", 2);
	}

	/**
	 * Returns a list of all the given arguments. If there
	 * were no arguments, returns an empty list.
	 *
	 * @param msg the array of command and arguments from splitFirst
	 * @return the list of all arguments
	 */
	public List<String> getArgs(String[] msg) {
		try {
			return new LinkedList<String>(Arrays.asList(msg[1].split("\\s+")));
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return new LinkedList<String>();
		}
	}

	/**
	 * Execute the command on the server.
	 *
	 * @param server the server the command will be executed on
	 * @param original the message containing the command
	 * @return a String saying the error, or PAS_ERREUR
	 */
	public abstract String execute(Server server, DataMessage original);

	/**
	 * Returns the usage of the current command.
	 *
	 * @return the usage of the command
	 */
	public String usage() {
		return "\tUsage: /";
	}

}
