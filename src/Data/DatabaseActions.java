package Data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import Business.Main;

public class DatabaseActions {

	private static final String connectionString = "jdbc:sqlserver://DESKTOP-IPHNEBP;database=USURER;trustServerCertificate=true;integratedSecurity=true;";
	Connection connection;
	public ResultSet result;

	public DatabaseActions() {
		connection = createConnection(connectionString);
	}

//	public void addCustomer(String firstName, String lastName, String id) {
//		String query = "Execute AddClient '" + stringNullToEmpty(firstName) + "', '" + stringNullToEmpty(lastName)
//				+ "', '" + stringNullToEmpty(id) + "'";
//		executeQuerywithoutResultSet(query);
//	}

//	public QueryResult findCustomer(String firstName, String lastName, String id, LocalDate date) {
//		QueryResult qr = new QueryResult();
//		String query = "Execute FindClient '" + stringNullToEmpty(firstName) + "', '" + stringNullToEmpty(lastName)
//				+ "', '" + stringNullToEmpty(id) + "', '" + date.toString() + "'";
//
//		qr = executeQuerywithResultSet(query);
//		return qr;
//	}

//	public QueryResult getAllScores(String clientID) {
//		QueryResult qr = new QueryResult();
//		String query = "Execute GetAllScores '" + stringNullToEmpty(clientID) + "'";
//
//		qr = executeQuerywithResultSet(query);
//		return qr;
//	}

//	public QueryResult checkScoreExistance(String clientID, Date fromDate) {
//		QueryResult qr = new QueryResult();
//		String query = "Execute CheckScoreExistance '" + stringNullToEmpty(clientID) + "', '" + fromDate.toString() + "'";
//		qr = executeQuerywithResultSet(query);
//		return qr;
//	}

//	public void addScore(String clientID, String score, String fromDate) {
//		String query = "Execute AddScore '" + stringNullToEmpty(clientID) + "', '" + stringNullToEmpty(score) + "', '"
//				+ stringNullToEmpty(fromDate) + "'";
//		executeQuerywithoutResultSet(query);
//	}

	public void executeQuerywithoutResultSet(String query) {
		QueryResult qr = new QueryResult();
		if (connection == null)
			qr.setStatus(5);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(query);
			qr.setStatus(0);
		} catch (SQLException e) {
			qr.setStatus(4);
		}
	}

	private String stringNullToEmpty(String s) {
		if (s == null)
			return "";
		else
			return s;
	}

	public QueryResult executeQuerywithResultSet(String query) {
		QueryResult qr = new QueryResult();
		if (connection == null) {
			qr.setStatus(5);
		}
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			qr.setResult(stmt.executeQuery(query));
		} catch (SQLException e) {
			qr.setStatus(4);
		}
		return qr;
	}

	private Connection createConnection(String connectionString) {
		try {
			Connection con = DriverManager.getConnection(connectionString);
//			System.out.println("connection ok");
			return con;
		} catch (SQLException e) {
//			System.out.println("connection error");
//			System.out.println(e);
		}
		return null;
	}

}
