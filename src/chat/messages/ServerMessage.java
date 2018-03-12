package chat.messages;

import chat.client.ActionsMessages;

/**
 * The class represents a message the server can send to the client.
 */

public abstract class ServerMessage extends Message {

	// nothing to do here

	/**
	 * Do the client action when there this message
	 *
	 * @param actions the ActionsMessages object
	 */
	abstract public void action(ActionsMessages action);

}
