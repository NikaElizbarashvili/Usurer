package Presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

enum PopupType {
	Customer
}

public class CustomerPopupMenu extends JPopupMenu implements ActionListener {

	private JMenuItem updateScore;
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
			this.add(updateScore);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) e.getSource();
		if (source == updateScore) {
			Actions.GetAllScores(e, this.sourceID);
		}
	}

}
