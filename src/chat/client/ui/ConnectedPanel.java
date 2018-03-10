package chat.client.ui;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import java.awt.BorderLayout;

public class ConnectedPanel extends JPanel {

	private JList<String> list;

	public ConnectedPanel(MainWindow window) {
		super();
		this.list = new JList<String>();

		this.list.setBorder(BorderFactory.createTitledBorder("Connectés"));
		this.add(this.list);
	}

	public void setUsers(String[] content) {
		this.list.setListData(content);
	}

	public void setUsers(List<String> content) {
		this.setUsers(content.toArray(new String[0]));
	}

}
