package chat.client.ui;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.BorderLayout;

public class ConnectedPanel extends JPanel {

	private BorderLayout layout;
	private JLabel label;
	private JList<String> list;

	public ConnectedPanel(MainWindow window) {
		this.label = new JLabel("Connectés");
		this.list  = new JList<String>();

		this.layout = new BorderLayout(10, 10);
		this.setLayout(layout);
		this.add(this.label, BorderLayout.NORTH);
		this.add(this.list,  BorderLayout.CENTER);

	}

	public void setUsers(String[] content) {
		this.list.setListData(content);
	}

	public void setUsers(List<String> content) {
		this.setUsers(content.toArray(new String[0]));
	}

}
