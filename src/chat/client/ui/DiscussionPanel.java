package chat.client.ui;

import java.awt.Adjustable;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import chat.messages.DataMessage;

/**
 * The main panel, allowing the user to chat.
 */

public class DiscussionPanel extends JPanel {

	/** The layout */
	private GridBagLayout layout;

	/** The constrains */
	private GridBagConstraints gbc;

	/** The send button */
	private Button btnSend;

	/** The message */
	private JTextArea message;

	/** The conversation */
	private JTable conversation;

	/** The scroll pane */
	private JScrollPane scrollPane;

	/** The table model */
	private MessagesTableModel tableModel;

	/** The connected panel */
	private ConnectedPanel panelConnected;

	/** The window */
	private MainWindow window;

	/**
	 * Instantiates a new discussion panel.
	 *
	 * @param window the window
	 */
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

		// this is where the magic happens

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

	/**
	 * Creates the message area, allowing the user to enter text.
	 */
	private void createMessageArea() {
		this.message = new JTextArea("Message");
		this.message.setRows(1);
		this.message.setBorder(BorderFactory.createTitledBorder("Message"));
		this.message.setLineWrap(true);
		this.message.setWrapStyleWord(true);

		// maximum lenght of the message
		final int MAX_LENGTH = 140;
		final int MAX_NEWLN  = 4;
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

	/**
	 * Creates the table area, listing all the previous messages (a JTable).
	 */
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

	/**
	 * Creates the send area, allowing to send the messages.
	 */
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

	/**
	 * Scroll the pane to show the last message.
	 * TODO: ça déconne encore sur les messages qui sont
	 * trop longs et qui sont wraps en plusieurs lignes
	 */
	private void showLastMessage() {
		// https://stackoverflow.com/a/31317110/7625364
		final JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
	    AdjustmentListener downScroller = new AdjustmentListener() {
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            Adjustable adjustable = e.getAdjustable();
	            adjustable.setValue(adjustable.getMaximum());
	            verticalBar.removeAdjustmentListener(this);
	        }
	    };
	    verticalBar.addAdjustmentListener(downScroller);
	}

	/**
	 * Adds a message to the conversation
	 *
	 * @param message the message
	 */
	public void addMessage(DataMessage message) {
		this.tableModel.addMessage(message);
		this.showLastMessage();
	}

	/**
	 * Update all the previous messages to new messages.
	 *
	 * @param messages the new messages
	 */
	public void setMessages(List<DataMessage> messages) {
		this.tableModel.setMessages(messages);
		this.showLastMessage();
	}

}
