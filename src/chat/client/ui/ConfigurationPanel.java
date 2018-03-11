package chat.client.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ConfigurationPanel extends JPanel {


	private Field  username;
	private Field  ip;
	private Field  port;
	private Button btnLogin;

	private GridBagLayout      layout;
	private GridBagConstraints gbc;

	private MainWindow window;

	public ConfigurationPanel(MainWindow window) {
		this.window = window;

		this.layout = new GridBagLayout();
		this.gbc    = new GridBagConstraints();

		this.setLayout(this.layout);

		this.username = new Field(" Nom",  "pseudo");
		this.ip       = new Field("  IP",   "127.0.0.1");
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
					// en fait, c'est relou this.displayInfo("Connected !");
				}
			}

			public void displayError(String message) {
				display(message, JOptionPane.ERROR_MESSAGE);
			}

			public void displayInfo(String message) {
				display(message, JOptionPane.INFORMATION_MESSAGE);
			}

			private void display(String message, int option) {
				JOptionPane.showMessageDialog(new JFrame(), message, "Information", option);
			}

		});

		gbc.fill       = GridBagConstraints.BOTH;
		gbc.gridwidth  = 1;
		gbc.gridheight = 1;

		gbc.gridx      = 0;
		gbc.gridy      = 0;
		this.add(this.username, gbc);

		gbc.gridx      = 1;
		gbc.gridy      = 0;
		this.add(this.btnLogin, gbc);

		gbc.gridx      = 0;
		gbc.gridy      = 1;
		this.add(this.ip,       gbc);

		gbc.gridx      = 1;
		gbc.gridy      = 1;
		this.add(this.port,     gbc);
	}

	@Override
	public void addNotify() {
		super.addNotify();
		SwingUtilities.getRootPane(btnLogin).setDefaultButton(btnLogin);
	}


}
