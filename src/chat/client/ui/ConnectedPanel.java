package chat.client.ui;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class ConnectedPanel extends JPanel {

	private JScrollPane scroll;
	private JList<String> list;

	public ConnectedPanel(MainWindow window) {
		super();
		this.list   = new JList<String>();
		this.scroll = new JScrollPane(this.list);

		//Dimension d = scroll.getPreferredSize();
		//d.width = 200;
		//scroll.setPreferredSize(d);

		this.scroll.setBorder(BorderFactory.createTitledBorder("Connectés"));
		this.add(scroll);
	}

	public void setUsers(String[] content) {
		this.list.setListData(content);
	}

	public void setUsers(List<String> content) {
		this.setUsers(content.toArray(new String[0]));
	}

}
