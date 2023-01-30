package Presentation;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Business.Main;
import Data.QueryResult;

public class Actions {

	public static void addCustomer(ActionEvent e) {
		new AddCustomer(Main.frame, "Customer registration form", true);
	}

	public static void saveCustomer(ActionEvent e, String firstName, String lastName, String id) {
		QueryResult qr = Main.saveCustomer(firstName, lastName, id);
		Errors.showErrorMessage(qr.getStatus());
	}

	public static void findCustomer(ActionEvent e) {
		new FindCustomer(Main.frame, "Find customer", true);
	}

	public static void searchCustomer(ActionEvent e, String firstName, String lastName, String id, LocalDate date) {
		QueryResult qr = Main.findCustomer(firstName, lastName, id, date);
		int status = qr.getStatus();
		if (status != 0) {
			Errors.showErrorMessage(status);
			return;
		}
		((JDialog) (((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent())).dispose();

		new DisplayCustomerResult(Main.frame, "Search result", qr, true);
	}

	public static void GetAllScores(ActionEvent e, String id) {
		QueryResult qr = Main.getAllScores(id);

		new DisplayScoreHistory(Main.frame, "Score History", qr, true, id);
	}

	public static JScrollPane displaySQLResult(QueryResult qr, PopupType type) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(qr.getColumns());
		JTable table = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // Disallow the editing of any cell
			}
		};
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		for (int i = 0; i < qr.getData().size(); i++)
			model.addRow(qr.getData().get(i));
//		table.setComponentPopupMenu(new CustomerPopupMenu());
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());
				if (r >= 0 && r < table.getRowCount()) {
					table.setRowSelectionInterval(r, r);
				} else {
					table.clearSelection();
				}
				String id = (String) model.getValueAt(r, 0);
				if (SwingUtilities.isRightMouseButton(e))
					table.setComponentPopupMenu(new CustomerPopupMenu(type, id));
//				System.out.println(id);
			}
		});
		return scroll;
	}

	public static void updateScore(ActionEvent e, String clientID) {
		new AddScore(Main.frame, "Credit score update form", true, clientID);

	}

	public static void addScore(ActionEvent e, String clientID, String score, String fromDate) {
		QueryResult qr = Main.AddScore(clientID, score, fromDate);
		Errors.showErrorMessage(qr.getStatus(), null, qr.getAdditionalInfo());
		if (qr.getStatus() == 0)
			((JDialog) (((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent()))
					.dispose();
	}
}
