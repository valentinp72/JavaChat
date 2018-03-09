package chat.client;

import java.util.List;

import chat.messages.DataMessage;

public interface ActionsMessages {
	public void newMessage(DataMessage message);
	public void setMessages(List<DataMessage> messages);
	public void setUsers(List<String> users);
}
