package chat.client.ui;

import chat.messages.DataUser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class ConnectedPanel extends JPanel {

	private JScrollPane    scroll;
	private JList<DataUser>  list;

	public ConnectedPanel(MainWindow window) {
		super();
		this.setLayout(new BorderLayout());
		this.list   = new JList<DataUser>();
		this.list.setCellRenderer(new ListCellRenderer());
		this.scroll = new JScrollPane(this.list);

		this.scroll.setBorder(BorderFactory.createTitledBorder("Connectés"));
		this.add(scroll);
	}

	public void setUsers(DataUser[] content) {
		this.list.setListData(content);
	}

	public void setUsers(List<DataUser> content) {
		this.setUsers(content.toArray(new DataUser[0]));
	}

}
