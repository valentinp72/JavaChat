package chat.client.ui;

import java.util.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import chat.messages.DataMessage;

public class MessagesTableModel extends AbstractTableModel {

	private List<DataMessage> messages;
	private final String[]    header;

	public MessagesTableModel() {
		super();

		this.messages = new ArrayList<DataMessage>();
		this.header   = new String[] {"Pseudo", "Message", "Heure"};

	}

	public int getRowCount() {
		return this.messages.size();
	}

	public int getColumnCount() {
		return this.header.length;
	}

	public String getColumnName(int column) {
		return this.header[column];
	}

	public Object getValueAt(int row, int column) {
		DataMessage message = this.messages.get(row);

		switch(column) {
			case 0:
				return message.getUsername();
			case 1:
				return message.getMessage();
			case 2:
				return message.getTime();
			default:
				return "Erreur !";
		}
	}

	public void addMessage(DataMessage message) {
		this.messages.add(message);
		int position = this.messages.size() - 1;
		this.fireTableRowsInserted(position, position);
	}

	public void setMessages(List<DataMessage> messages) {
		this.messages = messages;
		this.fireTableRowsInserted(0, this.messages.size() - 1);
	}

}