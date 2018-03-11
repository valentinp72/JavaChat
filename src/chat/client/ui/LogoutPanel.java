package chat.client.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * The logout panel. This allows the user to click on a logout button, and to be
 * redirected to the login frame.
 */
public class LogoutPanel extends JPanel {

	/** The btn logout */
	private Button btnLogout;

	/** The window */
	private MainWindow window;

	/**
	 * Instantiates a new logout panel.
	 *
	 * @param window the window
	 */
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
