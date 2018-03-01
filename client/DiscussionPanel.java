import java.awt.GridLayout;

import javax.swing.JPanel;

public class DiscussionPanel extends JPanel {

	private GridLayout layout;

	private Button btnSend;
	private Field  message;

	public DiscussionPanel(MainWindow window) {
		this.layout = new GridLayout(3, 1);
		this.setLayout(this.layout);

		this.message = new Field("Message");
		this.message.setPreferredSize(400, 200);
		this.btnSend = new Button("Envoyer");

		this.add(message);
		this.add(btnSend);
	}

}
