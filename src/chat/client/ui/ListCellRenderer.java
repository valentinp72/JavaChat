package chat.client.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import chat.messages.DataUser;

/**
 * This class allows a JList to have a foreground if it contains a DataUser.
 * The choosen color is the color of the user.
 */
public class ListCellRenderer extends DefaultListCellRenderer {

	/* (non-Javadoc)
	 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(
		JList list,
		Object value,
		int index,
		boolean isSelected,
		boolean cellHasFocus
	) {

		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if(value instanceof DataUser) {
			DataUser user = (DataUser) value;
			c.setForeground(user.getColor());
		}

		return c;
	}

}
