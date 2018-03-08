import java.util.List;
import java.util.ArrayList;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DiscussionPanel extends JPanel {

	private GridLayout layout;
	private Button     btnSend;
	private JTextArea  message;
	private JList<String> conversation;
	private List<String>  messages;

	private MainWindow window;

	public DiscussionPanel(MainWindow window) {
		this.window = window;
		this.layout = new GridLayout(3, 1);
		this.setLayout(this.layout);

		this.message = new JTextArea("Message");
		this.message.setRows(4);
		this.message.setColumns(80);
	
		this.messages     = new ArrayList<String>();
		this.conversation = new JList<String>();

		this.btnSend = new Button("Envoyer");
		this.btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				boolean sent = window.sendMessage(message.getText());
				if(sent) {
					message.setText("");
				}
				else {
					JOptionPane.showMessageDialog(
						new JFrame(), 
						"Erreur lors de l'envoi du message...", 
						"Dialog", 
						JOptionPane.ERROR_MESSAGE
					);
				}
				addMessage(message.getText());
			}	
		});

		this.add(conversation);
		this.add(message);
		this.add(btnSend);
	}
	
	public void addMessage(String message) {
		this.messages.add(message);
		this.conversation.setListData(messages.toArray(new String[messages.size()]));
	}

}
