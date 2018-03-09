package chat.client.ui;

import java.util.List;
import java.util.ArrayList;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import chat.messages.DataMessage;

public class DiscussionPanel extends JPanel {

	private GridLayout layout;
	private Button     btnSend;
	private JTextArea  message;

	private JTable conversation;
	private MessagesTableModel tableModel;

	private MainWindow window;

	public DiscussionPanel(MainWindow window) {
		this.window = window;
		this.layout = new GridLayout(3, 1);
		this.setLayout(this.layout);

		this.message = new JTextArea("Message");
		this.message.setRows(4);
		this.message.setColumns(80);
		this.message.setBorder(BorderFactory.createTitledBorder("Message"));
		this.message.setLineWrap(true);

		this.tableModel   = new MessagesTableModel();
		this.conversation = new JTable(this.tableModel);
		this.conversation.getColumnModel().getColumn(0).setWidth(50);
		this.conversation.getColumnModel().getColumn(1).setWidth(500);
		this.conversation.getColumnModel().getColumn(2).setWidth(50);

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
			}
		});

		this.add(new JScrollPane(conversation));
		this.add(message);
		this.add(btnSend);
	}

	public void showLastMessage() {
	//	int lastIndex = conversation.getModel().getSize() - 1;
	//	if (lastIndex >= 0) {
   	//		conversation.ensureIndexIsVisible(lastIndex);
	//	}
	}

	public void addMessage(DataMessage message) {
		//this.messages.add(message);
		//this.conversation.setListData(messages.toArray(new DataMessage[messages.size()]));
		//this.showLastMessage();
		this.tableModel.addMessage(message);
	}

	public void setMessages(List<DataMessage> messages) {
	//	this.messages = messages;
	//	this.conversation.setListData(messages.toArray(new DataMessage[messages.size()]));
	//	this.showLastMessage();
		this.tableModel.setMessages(messages);
	}

}
