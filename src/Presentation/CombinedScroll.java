package Presentation;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CombinedScroll {

	private JScrollPane scroll;
	private JTable table;
	private DefaultTableModel model;

	public CombinedScroll() {
		model = new DefaultTableModel();
		table = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // Disallow the editing of any cell
			}
		};
		table.setModel(model);
		scroll = new JScrollPane(table);
	}

	public CombinedScroll(JScrollPane scroll, JTable table, DefaultTableModel model) {
		this();
		this.scroll = scroll;
		this.table = table;
		this.model = model;
	}

	public JScrollPane getScroll() {
		return scroll;
	}

	public void setScroll(JScrollPane scroll) {
		this.scroll = scroll;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

}
