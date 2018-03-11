package chat.client;

import java.util.List;

import chat.messages.DataMessage;
import chat.messages.DataUser;

/**
 * The interface that every wants to make actions when the client receive a message
 * must complains.
 */
public interface ActionsMessages {

	/**
	 * This method is called when the client receives a new message to
	 * be saved by the server.
	 *
	 * @param message the new message
	 */
	public void newMessage(DataMessage message);

	/**
	 * This method is called when the client receives a new set of messages to
	 * replace the current messages.
	 *
	 * @param messages the new messages
	 */
	public void setMessages(List<DataMessage> messages);

	/**
	 * This method is called when the client receives the new list
	 * of users by the server.
	 *
	 * @param users the new users
	 */
	public void setUsers(List<DataUser> users);

	/**
	 * This method is called when the client receives an error at the login.
	 *
	 * @param error the error
	 */
	public void connectionError(String error);
}
