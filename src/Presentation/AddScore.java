package Presentation;

import java.awt.Frame;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;


import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddScore extends MyDialog {

	private final int distance = 10;
	private final int labelXSize = 180;
	private final int fieldXSize = 100;
	private final int buttonXSize = 80;
	private final int ySize = 20;
	private final int headerSize = 38;
	private final int rightExtraSize = 18;
	private final int width = distance * 3 + labelXSize + fieldXSize;
	private final int height = distance * 5 + ySize * 4;

	public AddScore(Frame owner, String title, boolean modal, String clientID) {
		super(owner, title, modal);
		this.setSize(width + rightExtraSize, headerSize + height);
		this.setLocationRelativeTo(owner);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel scoreLabel = new JLabel("Score");
		scoreLabel.setBounds(distance, distance, labelXSize, ySize);
		
		NumberFormat format = new DecimalFormat("###0.00");
		format.setMinimumFractionDigits(2);
		format.setMaximumFractionDigits(2);
		format.setRoundingMode(RoundingMode.HALF_UP);
		JFormattedTextField scoreField = new JFormattedTextField(format);
		scoreField.setBounds(2 * distance + labelXSize, distance, fieldXSize, ySize);
		JLabel fromDateLabel = new JLabel("Score From Date (yyyy-MM-dd)");
		fromDateLabel.setBounds(distance, 2 * distance + ySize, labelXSize, ySize);
		JFormattedTextField fromDateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
		fromDateField.setColumns(10);
		fromDateField.setBounds(2 * distance + labelXSize, 2 * distance + ySize, fieldXSize, ySize);

		JButton addButton = new JButton();
		addButton.setBorder(null);
		addButton.setFocusable(false);
		addButton.setText("Add Score");
		addButton.setBounds(2 * distance + labelXSize + fieldXSize - buttonXSize, 4 * distance + 3 * ySize, buttonXSize,
				ySize);
		;
		addButton.addActionListener(e-> Actions.addScore(e,clientID, scoreField.getText(),fromDateField.getText()));
		panel.add(scoreLabel);
		panel.add(scoreField);
		panel.add(fromDateLabel);
		panel.add(fromDateField);
		panel.add(addButton);
		this.add(panel);
		this.setVisible(true);
	}

}
