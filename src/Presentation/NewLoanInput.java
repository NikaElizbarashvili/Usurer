package Presentation;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Business.Customer;
import Data.QueryResult;

public class NewLoanInput extends MyDialog {

	private final int distance = 10;
	private final int labelXSize = 120;
	private final int fieldXSize = 50;
	private final int buttonXSize = 70;
	private final int ySize = 20;
	private final int headerSize = 38;
	private final int columnWidth = 100;
	private final int columnHeight = 20;
	private final int rightExtraSize = 18;

	private int width;
	private int height;

	public NewLoanInput(Frame owner, String title, QueryResult qr, boolean modal, Customer customer) {
		super(owner, title, modal);

		this.width = qr.getColumns().size() * columnWidth;
		this.height = headerSize + (qr.getData().size() + 2) * columnHeight + (ySize + distance) * 6;
		JScrollPane scroll = Actions.displaySQLResult(qr, PopupType.Customer);
		this.setSize(width + rightExtraSize, headerSize + height);
		this.setLocationRelativeTo(owner);

//		JPanel panel = new JPanel();
//		panel.setLayout(null);

		JLabel incomeLabel = new JLabel("Monthly income *");
		incomeLabel.setBounds(distance, 3 * distance + columnHeight, labelXSize, ySize);
		NumberFormat decimalFormat = new DecimalFormat("###0.00");
		decimalFormat.setMinimumFractionDigits(2);
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setRoundingMode(RoundingMode.DOWN);
		JFormattedTextField incomeField = new JFormattedTextField(decimalFormat);
		incomeField.setBounds(2 * distance + labelXSize, 3 * distance + columnHeight, fieldXSize, ySize);

		JLabel desiredAmountLabel = new JLabel("Desired amount");
		desiredAmountLabel.setBounds(distance, 4 * distance + columnHeight + ySize, labelXSize, ySize);
		JFormattedTextField desiredAmountField = new JFormattedTextField(decimalFormat);
		desiredAmountField.setBounds(2 * distance + labelXSize, 4 * distance + columnHeight + ySize, fieldXSize, ySize);

		JLabel termLabel = new JLabel("Desired term (M) *");
		termLabel.setBounds(distance, 5 * distance + columnHeight + 2 * ySize, labelXSize, ySize);
		NumberFormat wholeNumberFormat = new DecimalFormat("###0");
		wholeNumberFormat.setMinimumFractionDigits(0);
		wholeNumberFormat.setMaximumFractionDigits(0);
//		wholeNumberFormat.setRoundingMode(RoundingMode.DOWN);		
		JFormattedTextField termField = new JFormattedTextField(wholeNumberFormat);
		termField.setBounds(2 * distance + labelXSize, 5 * distance + columnHeight + 2 * ySize, fieldXSize, ySize);

		JCheckBox isHedgedCheckBox = new JCheckBox("Is hedged?", true);
		isHedgedCheckBox.setBounds(distance, 6 * distance + 3 * ySize + columnHeight, labelXSize, ySize);
		JButton calculateButton = new JButton();
		calculateButton.setBorder(null);
		calculateButton.setFocusable(false);
		calculateButton.setText("Calculate");
		calculateButton.setBounds(distance, 7 * distance + 4 * ySize + columnHeight, buttonXSize, ySize);
		calculateButton.addActionListener(e -> this.calculateButtonClick(e, customer, incomeField.getText(), desiredAmountField.getText(),
				isHedgedCheckBox.isSelected(), termField.getText()));
		this.add(incomeLabel);
		this.add(incomeField);
		this.add(desiredAmountField);
		this.add(desiredAmountLabel);
		this.add(isHedgedCheckBox);
		this.add(termField);
		this.add(termLabel);
		this.add(calculateButton);
//		this.add(panel);
		this.add(scroll);
		this.setVisible(true);
	}

	private void calculateButtonClick(ActionEvent e, Customer customer, String incomeString,
			String requestedAmountString, boolean isHedged, String termString) {
		if ((incomeString.isEmpty()) || (termString.isEmpty())) {
			Errors.showErrorMessage(1, null, null);
			return;
		}
		double requestedAmount;
		try {
			requestedAmount = Double.parseDouble(requestedAmountString);
		} catch (Exception ex) {
			requestedAmount = 0;
		}

		Actions.calculateLoanTerms(e, customer, Double.parseDouble(incomeString), requestedAmount, isHedged, Integer.parseInt(termString));
	}
}
