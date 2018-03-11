package chat.client.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class LogoutPanel extends JPanel {

	private Button btnLogout;

	private MainWindow window;

	public LogoutPanel(final MainWindow window) {
		this.window = window;

		this.setLayout(new GridLayout(1, 1));

		this.btnLogout = new Button("Déconnexion");

		this.btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("logout");
				window.resetClient();
			}

		});

		this.add(this.btnLogout);
	}


}
