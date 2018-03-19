package chat.client.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The panel allowing the user configure the server, the port, it's name, and
 * to log in.
 */

public class ConfigurationPanel extends JPanel {

	/** The username */
	private Field username;

	/** The ip */
	private Field ip;

	/** The port */
	private Field port;

	/** The login button */
	private Button btnLogin;

	/** The layout */
	private GridBagLayout layout;

	/** The grid constraints */
	private GridBagConstraints gbc;

	/** The window */
	private MainWindow window;

	/**
	 * Instantiates a new configuration panel.
	 *
	 * @param window the window the panel is displayed on.
	 */
	public ConfigurationPanel(final MainWindow window) {
		this.window = window;

		this.layout = new GridBagLayout();
		this.gbc    = new GridBagConstraints();

		this.setLayout(this.layout);

		this.username = new Field(" Nom", "pseudo");
		this.ip       = new Field("  IP", "127.0.0.1");
		this.port     = new Field("Port", "5890");
		this.btnLogin = new Button("Connexion");

		this.ip.addActionUpdate(() -> this.updateLoginState());
		this.port.addActionUpdate(() -> this.updateLoginState());
		this.username.addActionUpdate(() -> this.updateLoginState());


		// add the action when the login button is clicked
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
					// en fait, c'est relou
					// this.displayInfo("Connected !");
				}
			}

			/**
			 * Display a dialog box with an error message.
			 * @param message the error messaye to display
			 */
			public void displayError(String message) {
				display(message, JOptionPane.ERROR_MESSAGE);
			}

			/**
			 * Display a dialog box with an information message.
			 * @param message the error messaye to display
			 */
			public void displayInfo(String message) {
				display(message, JOptionPane.INFORMATION_MESSAGE);
			}

			/**
			 * Display a dialog box with a message and an option pane.
			 * @param message the error messaye to display
			 * @param option  the option pane (JOptionPane) to choose.
			 */
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

	/**
	 * Updates the state of the login button according to the
	 * states of the fields (disable and enable the button).
	 *
	 * @return true if the button is now active, false otherwise
	 */
	public boolean updateLoginState() {
		if(this.username.getText().length() > 0 && this.ip.getText().length() > 0) {
			try {
				if(this.port.getInt() > 0 && this.port.getInt() <= 65535) {
					this.btnLogin.setEnabled(true);
					return true;
				}
			}
			catch(NumberFormatException e) {
				// only check if the port is valid
			}
		}
		this.btnLogin.setEnabled(false);
		return false;
	}

	/**
	 * Says the login button is selected, so when the user
	 * press the "enter" key, he is connected.
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		SwingUtilities.getRootPane(btnLogin).setDefaultButton(btnLogin);
	}


}
