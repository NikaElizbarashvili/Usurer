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
	Customer
}

public class CustomerPopupMenu extends JPopupMenu implements ActionListener {

	private JMenuItem updateScore;
	private JMenuItem newLoan;
	private String sourceID;
	private PopupType type;

	public CustomerPopupMenu(PopupType type, String id) {
		this(type);
		sourceID = id;
	}

	public CustomerPopupMenu(PopupType type) {
		this.type = type;
		createPopupMenu();
		this.setVisible(true);
	}

	
	private void createPopupMenu() {
		if (type == PopupType.Customer) {
			updateScore = new JMenuItem("Update Score");
			updateScore.addActionListener(this);
			newLoan = new JMenuItem ("Laon Calculator");
			newLoan.addActionListener(this);
			this.add(updateScore);
			this.add(newLoan);
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
	}
	
}
