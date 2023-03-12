package Presentation;

import javax.swing.JOptionPane;

public class Errors {

	public static void showErrorMessage(int error, String fieldName, String additionalInfo) {
		String message;
		int messageType;
		switch (error) {
		case 0:
			message =  "Action performed";
			messageType=JOptionPane.PLAIN_MESSAGE;
			break;
		case 1:
			message =  "Required field is empty";
			messageType=JOptionPane.ERROR_MESSAGE;
			break;
		case 2:
			message =  "Non-alphabetic input in an aplhabetic field";
			messageType=JOptionPane.ERROR_MESSAGE;
			break;
		case 3:
			message =  "Non-numeric input in a numeric field";
			messageType=JOptionPane.ERROR_MESSAGE;
			break;
		case 4:
			message =  "Database error";
			messageType=JOptionPane.ERROR_MESSAGE;
			break;			
		case 5:
			message =  "Connection error";
			messageType=JOptionPane.ERROR_MESSAGE;
			break;	
		case 6:
			message =  "Empty result set";
			messageType=JOptionPane.ERROR_MESSAGE;
			break;	
		case 7:
			message =  "Entry already exists";
			messageType=JOptionPane.ERROR_MESSAGE;
			break;	
		case 8:
			message =  "Non-Date input in a date field";
			messageType=JOptionPane.ERROR_MESSAGE;
			break;	
		case 9:
			message =  "Input out of range";
			messageType=JOptionPane.ERROR_MESSAGE;
			break;
		case 10:
			message = "No new loan available";
			messageType=JOptionPane.ERROR_MESSAGE;
			break;			
		default :
			message = "Default";
			messageType=JOptionPane.PLAIN_MESSAGE;
		}
		if (fieldName!=null)
			message = message + " | Field: " + fieldName;
		if (additionalInfo!=null)
			message = message + " | Additional info: " + additionalInfo;
		
		JOptionPane.showMessageDialog(null, message, null, messageType);		

	}
	
	public static void showErrorMessage (int error) {
		showErrorMessage(error, null, null);		
	}

}
