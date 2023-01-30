package Presentation;

import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddCustomer extends MyDialog {

	private final int distance = 10;
	private final int labelXSize = 100;
	private final int fieldXSize = 150;
	private final int buttonXSize = 50;
	private final int ySize = 20;
	private final int headerSize = 38;
	private final int rightExtraSize = 18;
	private final int width = distance * 3 + labelXSize + fieldXSize;
	private final int height = distance * 5 + ySize * 4;

	public AddCustomer(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		this.setSize(width + rightExtraSize, headerSize + height);
		this.setLocationRelativeTo(owner);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel firstNameLabel = new JLabel("First Name *");
		firstNameLabel.setBounds(distance, distance, labelXSize, ySize);
		JTextField firstNameField = new JTextField();
		firstNameField.setBounds(2 * distance + labelXSize, distance, fieldXSize, ySize);
		JLabel lastNameLabel = new JLabel("Last Name *");
		lastNameLabel.setBounds(distance, 2 * distance + ySize, labelXSize, ySize);
		JTextField lastNameField = new JTextField();
		lastNameField.setBounds(2 * distance + labelXSize, 2 * distance + ySize, fieldXSize, ySize);
		JLabel idLabel = new JLabel("Identification *");
		idLabel.setBounds(distance, 3 * distance + 2 * ySize, labelXSize, ySize);
		JTextField idField = new JTextField();
		idField.setBounds(2 * distance + labelXSize, 3 * distance + 2 * ySize, fieldXSize, ySize);
		JButton saveButton = new JButton();
		saveButton.setBorder(null);
		saveButton.setFocusable(false);
		saveButton.setText("Save");
		saveButton.setBounds(2 * distance + labelXSize + fieldXSize - buttonXSize, 4 * distance + 3 * ySize,
				buttonXSize, ySize);
		;
		saveButton.addActionListener(e-> Actions.saveCustomer(e,firstNameField.getText(),lastNameField.getText(),idField.getText()));
		panel.add(firstNameLabel);
		panel.add(firstNameField);
		panel.add(lastNameLabel);
		panel.add(lastNameField);
		panel.add(idLabel);
		panel.add(idField);
		panel.add(saveButton);
		this.add(panel);
		this.setVisible(true);
	}
}
