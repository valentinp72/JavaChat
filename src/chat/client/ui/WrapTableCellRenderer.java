/*
 * 
 */
package chat.client.ui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class WrapTableCellRenderer.
 */
public class WrapTableCellRenderer extends JTextArea implements TableCellRenderer {

	/**
	 * Instantiates a new wrap table cell renderer.
	 */
	public WrapTableCellRenderer() {
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
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
