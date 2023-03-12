package Presentation;

import java.awt.Frame;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import Business.Customer;
import Business.Loan;
import Data.QueryResult;

public class DisplayLoan extends MyDialog {

	private final int headerSize = 38;
	private final int columnWidth = 100;
	private final int columnHeight = 20;
	
//	private final int buttonXSize = 80;
	private final int ySize = 20;
//	private final int buttonDistance = 10;

	
	public DisplayLoan(Frame owner, String title, Vector<Loan> loans, boolean modal) {
		super(owner, title, modal);
		
		int width = 4 * columnWidth; //4 columns: Amount, Maturity, Rate, PMT
		int height = headerSize + (1 + 2) * columnHeight + ySize; // 1 - single loan
//		int buttonY = headerSize + (qr.getData().size() + 0) * columnHeight;
		this.setSize(width, height);
		this.setLocationRelativeTo(owner);

		/*
		 * JButton addScoreButton = new JButton(); addScoreButton.setBorder(null);
		 * addScoreButton.setFocusable(false); addScoreButton.setText("Update Score");
		 * addScoreButton.setBounds(buttonDistance, buttonY, buttonDistance +
		 * buttonXSize, ySize); addScoreButton.addActionListener(e ->
		 * Actions.updateScore(e, clientID));
		 */		JScrollPane scroll = Actions.displayLoans(loans, PopupType.Loan);

//		this.add(addScoreButton);
		this.add(scroll);

		this.setVisible(true);
		
	}
}
