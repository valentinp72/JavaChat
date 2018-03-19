package chat.client.ui;

import java.awt.Dimension;
import java.util.function.Supplier;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;


/**
 * The Field class is a class with a JTextField and a JLabel displaying information.
 */
public class Field extends JPanel {

	/** The label */
	private JLabel label;

	/** The field */
	private JTextField field;

	/**
	 * Instantiates a new field.
	 *
	 * @param name the name of the field
	 */
	public Field(String name) {
		this.label = new JLabel(name + " : ");
		this.field = new JTextField("");
		this.field.setPreferredSize(new Dimension(200, 30));
		this.label.setPreferredSize(new Dimension(50, 30));

		this.add(label);
		this.add(field);
	}

	/**
	 * Instantiates a new field with a default value for the field
	 *
	 * @param name the name of the field
	 * @param value the default value
	 */
	public Field(String name, String value) {
		this(name);
		this.field.setText(value);
	}

	/**
	 * Sets the preferred size of the field
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setPreferredSize(int x, int y) {
		this.field.setPreferredSize(new Dimension(x, y));
	}

	/**
	 * Gets the text that is set by the user in the field.
	 *
	 * @return the text
	 */
	public String getText() {
		return field.getText();
	}

	/**
	 * Gets the real Java field
	 *
	 * @return the field
	 */
	public JTextField getField() {
		return this.field;
	}

	/**
	 * Gets the integer in the field
	 *
	 * @return the int
	 * @throws NumberFormatException the number format exception
	 */
	public int getInt() throws NumberFormatException {
		return Integer.parseInt(this.getText());
	}

	/**
	 * Add an action to be executed when a change happen in the field
	 *
	 * @param sup the function to be executed
	 */
	public void addActionUpdate(Supplier sup) {
		this.field.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				sup.get();
			}
			public void removeUpdate(DocumentEvent e) {
				sup.get();
			}
			public void insertUpdate(DocumentEvent e) {
				sup.get();
			}
		});
	}

}
