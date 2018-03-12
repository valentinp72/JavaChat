package chat.client.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import chat.messages.DataUser;

/**
 * The UI listing all the connected users to the chat.
 */

public class ConnectedPanel extends JPanel {

	/** The scroll pane, allowing to scroll if the window is not big enough */
	private JScrollPane scroll;

	/** The list of connected users */
	private JList<DataUser> list;

	/** The main window */
	private MainWindow window;

	/**
	 * Instantiates a new connected panel.
	 *
	 * @param window the window
	 */
	public ConnectedPanel(MainWindow window) {
		super();
		this.window = window;

		this.setLayout(new BorderLayout());
		this.list   = new JList<DataUser>();
		this.list.setCellRenderer(new ListCellRenderer(window));
		this.scroll = new JScrollPane(this.list);

		this.scroll.setBorder(BorderFactory.createTitledBorder("Connectés"));
		this.add(scroll);
	}

	/**
	 * Sets the users to be displayed on the panel
	 *
	 * @param content the new users
	 */
	public void setUsers(DataUser[] content) {
		this.list.setListData(content);
	}

	/**
	* Sets the users to be displayed on the panel
	 *
	 * @param content the new users
	 */
	public void setUsers(List<DataUser> content) {
		this.setUsers(content.toArray(new DataUser[0]));
	}

}
