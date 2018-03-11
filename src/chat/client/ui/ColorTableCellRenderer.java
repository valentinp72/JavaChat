/*
 * 
 */
package chat.client.ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class ColorTableCellRenderer.
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

		//if(value instanceof DataUser) {
		//	System.out.println("hey");
		//	JLabel l = (JLabel) this.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		//	DataUser user = (DataUser) value;
		l.setForeground(tableModel.getColorAt(row, column));

		//}

		return this;
	}
}
