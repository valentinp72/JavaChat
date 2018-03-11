package chat.client;

import chat.messages.DataMessage;
import chat.messages.DataUser;
import java.util.List;

public interface ActionsMessages {
	public void newMessage(DataMessage message);
	public void setMessages(List<DataMessage> messages);
	public void setUsers(List<DataUser> users);
	public void connectionError(String error);
}
