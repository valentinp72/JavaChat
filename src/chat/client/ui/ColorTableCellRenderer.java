package chat.client.ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;

/**
 * The class ColorTableCellRenderer, allowing to update the foreground color a
 * cell, according to the color given by MessagesTableModel.getColorAt.
 */

public class ColorTableCellRenderer extends DefaultTableCellRenderer {

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(
		JTable table,
		Object value,
		boolean isSelected,
		boolean hasFocus,
		int row,
		int column
	) {

		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		MessagesTableModel tableModel = (MessagesTableModel) table.getModel();

		l.setForeground(tableModel.getColorAt(row, column));

		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.TOP);

		return this;
	}
}
