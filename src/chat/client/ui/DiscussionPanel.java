package chat.client.ui;

import java.util.List;
import java.util.ArrayList;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import java.awt.Dimension;
import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import chat.messages.DataMessage;

public class DiscussionPanel extends JPanel {

	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private Button     btnSend;
	private JTextArea  message;

	private JTable conversation;
	private JScrollPane scrollPane;
	private MessagesTableModel tableModel;

	private MainWindow window;

	public DiscussionPanel(MainWindow window) {
		this.window = window;
		this.layout = new GridBagLayout();
		this.gbc    = new GridBagConstraints();

		this.setLayout(this.layout);

		this.createMessageArea();
		this.createTableArea();
		this.createSendArea();

		gbc.weightx    = 1.0;
		gbc.gridx      = 0;
		gbc.fill       = GridBagConstraints.BOTH;
		gbc.gridwidth  = 1;
		gbc.gridheight = 1;

		gbc.weighty    = 0.8;
		gbc.gridy      = 0;
		this.add(scrollPane, gbc);

		gbc.weighty    = 0.2;
		gbc.gridy      = 1;
		this.add(message, gbc);

		gbc.weighty    = 0.0;
		gbc.gridy      = 2;
		this.add(btnSend, gbc);

	}

	private void createMessageArea() {
		this.message = new JTextArea("Message");
		this.message.setRows(4);
		this.message.setColumns(80);
		this.message.setBorder(BorderFactory.createTitledBorder("Message"));
		this.message.setLineWrap(true);
	}

	private void createTableArea() {
		this.tableModel   = new MessagesTableModel();
		this.conversation = new JTable(this.tableModel);
		this.conversation.setRowSelectionAllowed(false);
		this.conversation.getColumn("Pseudo").setMaxWidth(100);
		this.conversation.getColumn("Message").setWidth(300);
		this.conversation.getColumn("Heure").setMaxWidth(50);
		this.conversation.getColumn("Message").setCellRenderer(new WrapTableCellRenderer());
		this.scrollPane = new JScrollPane(conversation);
	}

	private void createSendArea() {
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
	}

	private void showLastMessage() {
		// https://stackoverflow.com/a/31317110/7625364
		JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
	    AdjustmentListener downScroller = new AdjustmentListener() {
	        @Override
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            Adjustable adjustable = e.getAdjustable();
	            adjustable.setValue(adjustable.getMaximum());
	            verticalBar.removeAdjustmentListener(this);
	        }
	    };
	    verticalBar.addAdjustmentListener(downScroller);
	}

	public void addMessage(DataMessage message) {
		this.tableModel.addMessage(message);
		this.showLastMessage();
	}

	public void setMessages(List<DataMessage> messages) {
		this.tableModel.setMessages(messages);
		this.showLastMessage();
	}

}
