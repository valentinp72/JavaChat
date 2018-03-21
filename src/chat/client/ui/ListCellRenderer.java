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

	/**
	 * The window the cell rendered is about
	 */
	private MainWindow window;

	/**
	 * Create a new renderer with a given window
	 *
	 * @param window the window
	 */
	public ListCellRenderer(MainWindow window) {
		super();
		this.window = window;
	}

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

			if(window.getClient() != null && user.getUsername().equals(window.getClient().getClientName())) {
				c.setBackground(user.getBackgroundColor());
			}

			c.setForeground(user.getColor());

		}

		return c;
	}

}
