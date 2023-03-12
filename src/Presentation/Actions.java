package Presentation;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Business.Customer;
import Business.Loan;
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
		QueryResult qr = Main.findCustomer(null, firstName, lastName, id, date);
		int status = qr.getStatus();
		if (status != 0) {
			Errors.showErrorMessage(status);
			return;
		}
		((JDialog) (((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent())).dispose();

		new DisplayCustomerResult(Main.frame, "Search result", qr, true);
	}

	public static void getAllScores(ActionEvent e, String id) {
		QueryResult qr = Main.getAllScores(id);

		new DisplayScoreHistory(Main.frame, "Score History", qr, true, id);
	}

	public static JScrollPane displaySQLResult(QueryResult qr, PopupType type) {
		return generateScroll(qr.getColumns(), qr.getData(), type);
	}

	public static JScrollPane displayLoans(Vector<Loan> loans, PopupType type) {
		Vector<String> columns = new Vector<>();
		columns.addElement("Type");
		columns.addElement("Amount");
		columns.addElement("Term (M)");
		columns.addElement("Rate");
		columns.addElement("Payment");
		Vector<String[]> data = new Vector<>();

		for (int i = 0; i < loans.size(); i++) {
			String[] loanData = new String[5];
			if (i == 0)
				loanData[0] = "Maximum";
			else if (i == 1)
				loanData[0] = "Requested 1";
			else if (i == 2)
				loanData[0] = "Requested 2";
			loanData[1] = Double.toString(loans.get(i).getAmount());
			loanData[2] = Integer.toString(loans.get(i).getMaturity());
			loanData[3] = Float.toString(loans.get(i).getRate());
			loanData[4] = Double.toString(loans.get(i).getPmt());
			data.addElement(loanData);
		}

		return generateScroll(columns, data, type);
	}

	private static JScrollPane generateScroll(Vector<String> columns, Vector<String[]> data, PopupType type) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
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
		for (int i = 0; i < data.size(); i++)
			model.addRow(data.get(i));
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
					table.setComponentPopupMenu(new MyPopupMenu(type, id));
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

	public static void displayNewLoanWindow(ActionEvent e, Customer customer) {
		QueryResult qr = Main.findCustomer(customer.getCustomerID(), null, null, null, LocalDate.now());

		new NewLoanInput(Main.frame, "Loan Calculator", qr, true, customer);
	}

	public static void calculateLoanTerms(ActionEvent e, Customer customer, double income, double requestedAmount,
			boolean isHedged, int term) {
		if ((term <= 0) || (income <= 0) || (requestedAmount < 0)) {
			Errors.showErrorMessage(9, null, null);
			return;
		}

		float pti = Main.getPTI(income, isHedged, LocalDate.now());
		double existingPayment = customer.getExistingPayment();
		double maxNewPayment = Math.round((income * pti - existingPayment) * 100.0) / 100.0;
		if (maxNewPayment <= 0) {
			Errors.showErrorMessage(10, null, null);
			return;
		}
		float proposedIntRate = Main.getProposedIntRate(customer.getScore(), LocalDate.now());
		double loanAmount = Math.round(
				maxNewPayment * (1 - Math.pow((1 + proposedIntRate / 12 / 100), -term)) / (proposedIntRate / 12 / 100));

		Vector<Loan> loans = new Vector<Loan>();
		loans.addElement(new Loan(loanAmount, term, proposedIntRate, maxNewPayment));

		if (requestedAmount > 0) {
			double pmt;
			pmt = Math.round((requestedAmount * (proposedIntRate / 12 / 100)
					/ (1 - Math.pow((1 + proposedIntRate / 12 / 100), -term))) * 100.0) / 100.0;
			if (pmt <= maxNewPayment)
				loans.addElement(new Loan(requestedAmount, term, proposedIntRate, pmt));
			int t = (int) Math.ceil(-Math.log10(1 - requestedAmount * (proposedIntRate / 12 / 100) / maxNewPayment)
					/ Math.log10(1 + proposedIntRate / 12 / 100));
			if (t > 0) {
				pmt = Math.round((requestedAmount * (proposedIntRate / 12 / 100)
						/ (1 - Math.pow((1 + proposedIntRate / 12 / 100), -t))) * 100.0) / 100.0;
				loans.addElement(new Loan(requestedAmount, t, proposedIntRate, pmt));
			}
		}
		new DisplayLoan(Main.frame, "Available Loan", loans, true);
	}

	public static void issueLoan(ActionEvent e, String id) {
		
	}
	
	public static Customer getCustomerFromID(String customerID) {
		QueryResult qr = Main.findCustomer(customerID, null, null, null, LocalDate.now());
		String[] data = qr.getData().get(0);
		String firstName = data[1];
		String lastName = data[2];
		String id = data[3];
		float score = Float.parseFloat(data[4]);
		double existingPayment = Math.round(Double.parseDouble(data[6]) * 100.0) / 100.0;
		return new Customer(customerID, id, firstName, lastName, score, existingPayment);
	}

}
