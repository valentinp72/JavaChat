package chat.client.ui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;


public class WrapTableCellRenderer extends JTextArea implements TableCellRenderer {

	public WrapTableCellRenderer() {
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
	}

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
