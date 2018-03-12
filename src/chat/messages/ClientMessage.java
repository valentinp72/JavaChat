package chat.messages;

import chat.server.ClientThread;

/**
 * The class represents a message the client can send to the server.
 */

public abstract class ClientMessage extends Message {

	abstract public void action(ClientThread clientThread);

}
