package chat.client.ui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 * This class wraps the content of a JTextArea in a table if the
 * line is too long to be displayed.
 */

public class WrapTableCellRenderer extends JTextArea implements TableCellRenderer {

	/**
	 * Instantiates a new wrap table cell renderer.
	 */
	public WrapTableCellRenderer() {
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
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
		this.setSize(width, getPreferredSize().height);

		if(table.getRowHeight(row) != getPreferredSize().height)
			table.setRowHeight(row, getPreferredSize().height);

		return this;
	}
}
