package chat.client.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Field extends JPanel {

	private JLabel     label;
	private JTextField field;

	public Field(String name) {
		this.label = new JLabel(name + " : ");
		this.field = new JTextField("");
		this.field.setPreferredSize(new Dimension(200, 30));
		this.label.setPreferredSize(new Dimension(50, 30));

		this.add(label);
		this.add(field);
	}

	public Field(String name, String value) {
		this(name);
		this.field.setText(value);
	}

	public void setPreferredSize(int x, int y) {
		this.field.setPreferredSize(new Dimension(x, y));
	}

	public String getText() {
		return field.getText();
	}

	public int getInt() throws NumberFormatException {
		return Integer.parseInt(this.getText());
	}

}
