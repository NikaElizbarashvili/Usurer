package Presentation;

import java.awt.Frame;

import Data.QueryResult;

public class DisplayCustomerResult extends MyDialog{

	private final int headerSize = 38;
	private final int columnWidth = 100;
	private final int columnHeight = 20;	

	public DisplayCustomerResult(Frame owner, String title, QueryResult qr, boolean modal) {
		super(owner, title, modal);
		int width = qr.getColumns().size() * columnWidth;
		int height = headerSize + (qr.getData().size()+2) * columnHeight;
		this.setSize(width, height);
		this.setLocationRelativeTo(owner);
		
		this.add(Actions.displaySQLResult(qr, PopupType.Customer));
		
		this.setVisible(true);
	}

}
