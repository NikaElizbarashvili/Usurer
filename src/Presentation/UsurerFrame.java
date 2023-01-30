package Presentation;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class UsurerFrame extends JFrame {

	private final int horizontalDistance = 20;
	private final int verticalDistance = 60;

	JMenuBar menuBar;
	JMenu menu;
	JMenuItem addCustomer;
	JMenuItem findCustomer;

	public UsurerFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(horizontalDistance, verticalDistance, (int) dim.getWidth() - horizontalDistance,
				(int) dim.getHeight() - verticalDistance);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		setupMenuBar();

		this.setVisible(true);
	}

	private void setupMenuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		addCustomer = new JMenuItem("Add customer");
		addCustomer.addActionListener(e-> Actions.addCustomer(e));
		findCustomer = new JMenuItem("Find custromer");
		findCustomer.addActionListener(e-> Actions.findCustomer(e));
				
		menu.add(addCustomer);
		menu.add(findCustomer);
		menuBar.add(menu);

		this.setJMenuBar(menuBar);

	}

}
