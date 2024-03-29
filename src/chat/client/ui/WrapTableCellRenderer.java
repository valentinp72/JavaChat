package chat.client.ui;

import java.io.File;
import java.io.IOException;

import java.awt.Insets;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import chat.messages.DataMessage;

/**
 * This class wraps the content of a JTextArea in a table if the
 * line is too long to be displayed.
 */

public class WrapTableCellRenderer extends JTextArea implements TableCellRenderer {

	/**
	 * The font to use when displaying messages
	 */
	public static Font FONT;

	static {
		/* We load a font that allow unicode emojis */
		try {
			FONT = Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/OpenSansEmoji.ttf")).deriveFont(14f);
		}
		catch(FontFormatException e) {
			System.out.println("Invalid font! Emojis won't work");
		}
		catch(IOException e) {
			System.out.println("Font not found! Emojis won't work");
		}
	}

	/**
	 * Instantiates a new wrap table cell renderer.
	 */
	public WrapTableCellRenderer() {
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setMargin(new Insets(0, 0, 2, 0));
	}

	/**
	 * Update the row height if the content is too wide
	 * to be displayed in the cell.
	 * @param table the table
	 * @param value the value of the cell
	 * @param isSelected true if the cell is selected
	 * @param hasFocus   true if the cell has focus
	 * @param row    the current row
	 * @param column the current column
	 */
	public Component getTableCellRendererComponent(
		JTable table,
		Object value,
		boolean isSelected,
		boolean hasFocus,
		int row,
		int column
	) {
		int width  = table.getColumnModel().getColumn(column).getWidth();
		int height = getPreferredSize().height;

		this.setText(value.toString());

		if(FONT != null)
			this.setFont(FONT);
		this.setSize(width, getPreferredSize().height);

		if(table.getRowHeight(row) != getPreferredSize().height)
			table.setRowHeight(row, getPreferredSize().height);

		MessagesTableModel tableModel = (MessagesTableModel) table.getModel();
		this.setForeground(tableModel.getColorAt(row, column));


		return this;
	}
}
