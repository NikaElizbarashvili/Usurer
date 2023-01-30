package Business;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.sql.Date;

import Data.DatabaseActions;
import Data.QueryResult;
import Presentation.UsurerFrame;

public class Main {

	private static final int maxScore = 400;

	public static UsurerFrame frame;
	private static DatabaseActions db;

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		db = new DatabaseActions();
		frame = new UsurerFrame();

	}

	public static QueryResult saveCustomer(String firstName, String lastName, String id) {
		QueryResult qr = new QueryResult();
		// Check for invalid inputs
		if ((firstName.isEmpty()) || (lastName.isEmpty()) || (id.isEmpty())) {
			qr.setStatus(1); // Empty field
			return qr;
		}
		if ((!isAlphabetic(firstName)) || (!isAlphabetic(lastName))) {
			qr.setStatus(2); // Non-alphabetic input
			return qr;
		}
		try {
			Long.parseLong(id);
		} catch (NumberFormatException ex) {
			qr.setStatus(3); // Non-numeric input
			return qr;
		}

		// Check if customer already exists
		qr = findCustomer(null, null, id, LocalDate.now());
		if (qr.getStatus() == 0) // 0 means successfully found an entry, which is error in case of AddCustomer
			// function
			qr.setStatus(7);
		else if (qr.getStatus() == 6) { // test passes if status = 6 - empty result set - no existing customer found
			qr.setStatus(0);
			String query = "Execute AddClient '" + stringNullToEmpty(firstName) + "', '" + stringNullToEmpty(lastName)
					+ "', '" + stringNullToEmpty(id) + "'";
			db.executeQuerywithoutResultSet(query);
		}
		return qr;
	}

	public static QueryResult findCustomer(String firstName, String lastName, String id, LocalDate date) {
		QueryResult qr = new QueryResult();
		String query = "Execute FindClient '" + stringNullToEmpty(firstName) + "', '" + stringNullToEmpty(lastName)
				+ "', '" + stringNullToEmpty(id) + "', '" + date.toString() + "'";
		qr = db.executeQuerywithResultSet(query);
		try {
			qr.getResult().beforeFirst();
		} catch (SQLException e) {
			qr.setStatus(4);
		}
		return qr;
	}

	public static QueryResult getAllScores(String id) {
		QueryResult qr = new QueryResult();
		String query = "Execute GetAllScores '" + stringNullToEmpty(id) + "'";
		qr = db.executeQuerywithResultSet(query);
		try {
			qr.getResult().beforeFirst();
		} catch (SQLException e) {
			qr.setStatus(4);
		}
		return qr;
	}

	public static QueryResult AddScore(String clientID, String score, String fromDate) {
		QueryResult qr = new QueryResult();
		// Check for invalid inputs
		if ((score.isEmpty()) || (fromDate.isEmpty())) {
			qr.setStatus(1); // Empty field
			return qr;
		}
		if (!isNumeric(score)) {
			qr.setStatus(3); // Non-numeric input
			return qr;
		}
		if ((Double.parseDouble(score) > maxScore) || (Double.parseDouble(score) < 0)) {
			qr.setStatus(9); // Incorrect input
			qr.setAdditionalInfo("Score greater than maxScore");
			return qr;
		}

		try {
			Date date = Date.valueOf(fromDate);
			if (date.compareTo(java.sql.Date.valueOf(LocalDate.now())) > 0) {
				qr.setStatus(9); // Incorrect input
				qr.setAdditionalInfo("Date greater than today");
				return qr;
			}
		} catch (Exception ex) {
			qr.setStatus(8); // Non-date input
			return qr;
		}

		// Check if customer already exists

		String query = "Execute CheckScoreExistance '" + stringNullToEmpty(clientID) + "', '"
				+ stringNullToEmpty(fromDate) + "'";
		qr = db.executeQuerywithResultSet(query);
		if (qr.getStatus() == 0) // 0 means successfully found an entry, which is error in case of AddCustomer
			// function
			qr.setStatus(7);
		else if (qr.getStatus() == 6) { // test passes if status = 6 - empty result set - no existing customer found
			qr.setStatus(0);
			query = "Execute AddScore '" + stringNullToEmpty(clientID) + "', '" + stringNullToEmpty(score) + "', '"
					+ stringNullToEmpty(fromDate) + "'";
			db.executeQuerywithoutResultSet(query);
		}
		return qr;

	}

	private static boolean isAlphabetic(String s) {
		char[] chars = s.toCharArray();
		for (char c : chars) {
			if ((!Character.isLetter(c)) && (c != ' ')) {
				return false;
			}
		}
		return true;
	}

	private static boolean isNumeric(String s) {
		char[] chars = s.toCharArray();
		for (char c : chars) {
			if ((!Character.isDigit(c)) && (c != '.')) {
				return false;
			}
		}
		return true;
	}

	private static String stringNullToEmpty(String s) {
		if (s == null)
			return "";
		else
			return s;
	}
}
