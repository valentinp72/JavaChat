/*
 * 
 */
package chat.client.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import chat.messages.DataUser;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectedPanel.
 */
public class ConnectedPanel extends JPanel {

	/** The scroll. */
	private JScrollPane    scroll;
	
	/** The list. */
	private JList<DataUser>  list;

	/**
	 * Instantiates a new connected panel.
	 *
	 * @param window the window
	 */
	public ConnectedPanel(MainWindow window) {
		super();
		this.setLayout(new BorderLayout());
		this.list   = new JList<DataUser>();
		this.list.setCellRenderer(new ListCellRenderer());
		this.scroll = new JScrollPane(this.list);

		this.scroll.setBorder(BorderFactory.createTitledBorder("Connectés"));
		this.add(scroll);
	}

	/**
	 * Sets the users.
	 *
	 * @param content the new users
	 */
	public void setUsers(DataUser[] content) {
		this.list.setListData(content);
	}

	/**
	 * Sets the users.
	 *
	 * @param content the new users
	 */
	public void setUsers(List<DataUser> content) {
		this.setUsers(content.toArray(new DataUser[0]));
	}

}
