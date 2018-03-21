package chat.client.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import chat.messages.DataMessage;

/**
 * This class tells the model to be used by the JTable to display the messages.
 */

public class MessagesTableModel extends AbstractTableModel {

	/** The messages */
	private List<DataMessage> messages;

	/** The header */
	private final String[] header;

	/**
	 * Instantiates a new messages table model.
	 */
	public MessagesTableModel() {
		super();

		this.messages = new ArrayList<DataMessage>();
		this.header   = new String[] {"Pseudo", "Message", "Heure"};

	}

	/**
	 * Returns the number of rows in the table.
	 * @return the number of rows in the table.
	 */
	public int getRowCount() {
		return this.messages.size();
	}

	/**
	 * Returns the number of columns in the table.
	 * @return the number of columns in the table.
	 */
	public int getColumnCount() {
		return this.header.length;
	}

	/**
	 * Returns the name of the column at the given index
	 * @return the name of the column at the given index
	 */
	public String getColumnName(int column) {
		return this.header[column];
	}

	/**
	 * Returns the value at the given coordinates in the table.
	 * @return the value at the given coordinates in the table.
	 */
	public Object getValueAt(int row, int column) {
		try {
			DataMessage message = this.messages.get(row);
			switch(column) {
				case 0:
					return message.getUser().toString();
				case 1:
					return message.getMessage();
				case 2:
					return message.getTime();
				default:
					return "Erreur !";
			}
		}
		catch (Exception e) {
			return "Erreur !";
		}
	}

	/**
	 * Returns the color to be displayed at the given cell.
	 * It always return BLACK, except if the column is the username one. It then
	 * returns the color of the sending user.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the color at
	 */
	public Color getColorAt(int row, int column) {
		if(column == 0) {
			return this.messages.get(row).getUser().getColor();
		}
		else if(column == 1) {
			return this.messages.get(row).getColor();
		}
		return Color.BLACK;
	}

	/**
	 * Adds a message to the table.
	 *
	 * @param message the message to add
	 */
	public void addMessage(DataMessage message) {
		this.messages.add(message);
		int position = this.messages.size() - 1;
		this.fireTableRowsInserted(position, position);
	}

	/**
	 * Sets the messages to be displayed
	 *
	 * @param messages the new messages
	 */
	public void setMessages(List<DataMessage> messages) {
		this.messages = messages;
		this.fireTableRowsInserted(0, this.messages.size() - 1);
	}



}
