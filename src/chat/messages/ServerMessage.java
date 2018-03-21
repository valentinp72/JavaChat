package chat.messages;

import chat.client.ActionsMessages;

/**
 * The class represents a message the server can send to the client.
 */

public abstract class ServerMessage extends Message {

	/**
	 * Do the client action when there this message
	 *
	 * @param action the ActionsMessages object
	 */
	abstract public void action(ActionsMessages action);

}
