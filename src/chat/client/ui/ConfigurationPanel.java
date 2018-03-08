package chat.client.ui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConfigurationPanel extends JPanel {

	private Field  username;
	private Field  ip;
	private Field  port;
	private Button btnLogin;

	private MainWindow window;

	public ConfigurationPanel(MainWindow window) {
		this.window = window;

		this.setLayout(new GridLayout(2, 2));

		this.username = new Field("Nom",  "Votre nom");
		this.ip       = new Field("IP",   "127.0.0.1");
		this.port     = new Field("Port", "5890");
		this.btnLogin = new Button("Connexion");

		this.btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int portValue    = 0;
				String ipValue   = ip.getText();
				String usernameV = username.getText();

				try {
					portValue = port.getInt();
				}
				catch(NumberFormatException e) {
					this.displayError("Port is not valid!");
					return;
				}
				String res = window.setClient(ipValue, portValue, usernameV);
				if(res != "OK") {
					this.displayError(res);
				}
				else {
					this.displayInfo("Connected !");
				}
			}

			public void displayError(String message) {
				display(message, JOptionPane.ERROR_MESSAGE);
			}

			public void displayInfo(String message) {
				display(message, JOptionPane.INFORMATION_MESSAGE);
			}

			private void display(String message, int option) {
				JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", option);
			}

		});

		this.add(this.username);
		this.add(this.btnLogin);
		this.add(this.ip);
		this.add(this.port);
	}


}
