package chat.client.ui;

import chat.messages.DataUser;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ListCellRenderer extends DefaultListCellRenderer {

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
