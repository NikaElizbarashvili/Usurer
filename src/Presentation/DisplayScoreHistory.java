package Presentation;

import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Data.QueryResult;

public class DisplayScoreHistory extends MyDialog {

	private final int headerSize = 38;
	private final int columnWidth = 100;
	private final int columnHeight = 20;

	private final int buttonXSize = 80;
	private final int ySize = 20;
	private final int buttonDistance = 10;

	public DisplayScoreHistory(Frame owner, String title, QueryResult qr, boolean modal, String clientID) {
		super(owner, title, modal);
		int width = qr.getColumns().size() * columnWidth;
		int height = headerSize + (qr.getData().size() + 2) * columnHeight + ySize;
		int buttonY = headerSize + (qr.getData().size() + 0) * columnHeight;
		this.setSize(width, height);
		this.setLocationRelativeTo(owner);

//		JPanel panel = new JPanel();
//		panel.setLayout(null);
		JButton addScoreButton = new JButton();
		addScoreButton.setBorder(null);
		addScoreButton.setFocusable(false);
		addScoreButton.setText("Update Score");
		addScoreButton.setBounds(buttonDistance, buttonY, buttonDistance + buttonXSize, ySize);
		addScoreButton.addActionListener(e -> Actions.updateScore(e, clientID));
		JScrollPane scroll = Actions.displaySQLResult(qr, null);

		this.add(addScoreButton);
		this.add(scroll);

		this.setVisible(true);
	}

}
