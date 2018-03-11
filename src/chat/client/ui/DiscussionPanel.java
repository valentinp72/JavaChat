package chat.client.ui;

import chat.messages.DataMessage;
import java.awt.Adjustable;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.StyledDocument;

public class DiscussionPanel extends JPanel {

	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private Button     btnSend;
	private JTextArea  message;

	private JTable conversation;
	private JScrollPane scrollPane;
	private MessagesTableModel tableModel;
	private ConnectedPanel panelConnected;

	private MainWindow window;

	public DiscussionPanel(MainWindow window) {
		this.window = window;
		this.layout = new GridBagLayout();
		this.gbc    = new GridBagConstraints();

		this.setLayout(this.layout);

		this.createMessageArea();
		this.createTableArea();
		this.createSendArea();
		this.panelConnected = this.window.getPanelConnected();

		gbc.fill       = GridBagConstraints.BOTH;

		gbc.weightx    = 0.4;
		gbc.gridx      = 0;
		gbc.gridy      = 0;
		gbc.gridwidth  = 4;
		gbc.gridheight = 6;
		gbc.ipadx      = 70;
		this.add(panelConnected, gbc);
		gbc.ipadx      = 0;

		gbc.weightx    = 0.6;
		gbc.gridx      = 4;
		gbc.gridwidth  = 1;

		gbc.weighty    = 0.8;
		gbc.gridy      = 0;
		gbc.gridheight = 3;
		this.add(scrollPane, gbc);

		gbc.weighty    = 0.0;
		gbc.gridy      = 3;
		gbc.gridheight = 2;
		this.add(message, gbc);

		gbc.weighty    = 0.0;
		gbc.gridy      = 5;
		gbc.gridheight = 1;
		this.add(btnSend, gbc);
	}

	private void createMessageArea() {
		this.message = new JTextArea("Message");
		this.message.setRows(1);
		this.message.setBorder(BorderFactory.createTitledBorder("Message"));
		this.message.setLineWrap(true);
		this.message.setWrapStyleWord(true);

		// maximum lenght of the message
		int MAX_LENGTH = 140;
		int MAX_NEWLN  = 4;
		this.message.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				int lines = message.getText().split("\r\n|\r|\n", -1).length;
				if (str == null || message.getText().length() >= MAX_LENGTH || lines >= MAX_NEWLN) {
	            	return;
	        	}
				super.insertString(offs, str, a);
	    	}
		});
	}

	private void createTableArea() {
		this.tableModel   = new MessagesTableModel();
		this.conversation = new JTable(this.tableModel);
		this.conversation.setRowSelectionAllowed(false);
		this.conversation.getColumn("Pseudo").setMaxWidth(150);
		this.conversation.getColumn("Message").setWidth(200);
		this.conversation.getColumn("Heure").setMaxWidth(50);
		this.conversation.getColumn("Message").setCellRenderer(new WrapTableCellRenderer());
		this.conversation.getColumn("Pseudo").setCellRenderer(new ColorTableCellRenderer());
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
