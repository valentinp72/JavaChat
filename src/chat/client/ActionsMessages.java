package chat.client;

import java.util.List;

import chat.messages.DataMessage;
import chat.messages.DataUser;

public interface ActionsMessages {
	public void newMessage(DataMessage message);
	public void setMessages(List<DataMessage> messages);
	public void setUsers(List<DataUser> users);
	public void connectionError(String error);
}
