package Presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Business.Customer;
import Business.Main;
import Data.QueryResult;

enum PopupType {
	Customer,
	Loan
}

public class MyPopupMenu extends JPopupMenu implements ActionListener {

	// Customer menuItems
	private JMenuItem updateScore;
	private JMenuItem newLoan;

	// Loan menuItems
	private JMenuItem issueLoan;

	private String sourceID;
	private PopupType type;

	public MyPopupMenu(PopupType type, String id) {
		this(type);
		sourceID = id;
	}

	public MyPopupMenu(PopupType type) {
		this.type = type;
		createPopupMenu();
		this.setVisible(true);
	}

	private void createPopupMenu() {
		if (type == PopupType.Customer) {
			updateScore = new JMenuItem("Update Score");
			updateScore.addActionListener(this);
			newLoan = new JMenuItem("Laon Calculator");
			newLoan.addActionListener(this);
			this.add(updateScore);
			this.add(newLoan);
		}
		if (type == PopupType.Loan) {
			issueLoan = new JMenuItem("Issue Loan");
			issueLoan.addActionListener(this);
			this.add(issueLoan);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) e.getSource();
		if (source == updateScore) {
			Actions.getAllScores(e, this.sourceID);
		}
		if (source == newLoan) {
			Actions.displayNewLoanWindow(e, Actions.getCustomerFromID(sourceID));
		}
		if (source == issueLoan) {
//			Actions.displayNewLoanWindow(e, Actions.getCustomerFromID(sourceID));
		}
	}

}
