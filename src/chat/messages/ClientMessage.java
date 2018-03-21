package chat.messages;

import chat.server.ClientThread;

/**
 * The class represents a message the client can send to the server.
 */

public abstract class ClientMessage extends Message {

	/**
	 * The action to be done when the server wants to execute the message
	 *
	 * @param clientThread the thread that received this message
	 * @return true or false if the action could be done correctly
	 */
	abstract public boolean action(ClientThread clientThread);

}
