/*
 * 
 */
package chat.client.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import chat.messages.DataMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class MessagesTableModel.
 */
public class MessagesTableModel extends AbstractTableModel {

	/** The messages. */
	private List<DataMessage> messages;
	
	/** The header. */
	private final String[]    header;

	/**
	 * Instantiates a new messages table model.
	 */
	public MessagesTableModel() {
		super();

		this.messages = new ArrayList<DataMessage>();
		this.header   = new String[] {"Pseudo", "Message", "Heure"};

	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return this.messages.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return this.header.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		return this.header[column];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column) {
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

	/**
	 * Gets the color at.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the color at
	 */
	public Color getColorAt(int row, int column) {
		if(column == 0) {
			return this.messages.get(row).getUser().getColor();
		}
		return Color.BLACK;
	}

	/**
	 * Adds the message.
	 *
	 * @param message the message
	 */
	public void addMessage(DataMessage message) {
		this.messages.add(message);
		int position = this.messages.size() - 1;
		this.fireTableRowsInserted(position, position);
	}

	/**
	 * Sets the messages.
	 *
	 * @param messages the new messages
	 */
	public void setMessages(List<DataMessage> messages) {
		this.messages = messages;
		this.fireTableRowsInserted(0, this.messages.size() - 1);
	}



}
