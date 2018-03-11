package chat.client;

import java.util.List;

import chat.messages.DataMessage;
import chat.messages.DataUser;

// TODO: Auto-generated Javadoc
/**
 * The Interface ActionsMessages.
 */
public interface ActionsMessages {
	
	/**
	 * New message.
	 *
	 * @param message the message
	 */
	public void newMessage(DataMessage message);
	
	/**
	 * Sets the messages.
	 *
	 * @param messages the new messages
	 */
	public void setMessages(List<DataMessage> messages);
	
	/**
	 * Sets the users.
	 *
	 * @param users the new users
	 */
	public void setUsers(List<DataUser> users);
	
	/**
	 * Connection error.
	 *
	 * @param error the error
	 */
	public void connectionError(String error);
}
