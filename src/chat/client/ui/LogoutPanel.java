package chat.client.ui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogoutPanel extends JPanel {

	private Button btnLogout;

	private MainWindow window;

	public LogoutPanel(MainWindow window) {
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
