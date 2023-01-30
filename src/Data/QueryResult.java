package Data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class QueryResult {
	private Vector<String[]> data = new Vector<String[]>();
	private Vector<String> columns = new Vector<String>();
	private ResultSet result = null;
	private int status = -1;
	private String additionalInfo;

	public QueryResult() {
		data = new Vector<String[]>();
		columns = new Vector<String>();
		result = null;
		status = -1;
		additionalInfo = null;
	}

	private int getDatafromRS(ResultSet rs) {

		ResultSetMetaData rsmd;
		data.clear();
		columns.clear();
		int n;
		try {
			rsmd = rs.getMetaData();
			n = rsmd.getColumnCount();
			for (int i = 0; i < n; i++)
				columns.addElement(rsmd.getColumnName(i + 1));
			rs.beforeFirst();
			while (rs.next()) {
				String[] record = new String[n];
				for (int i = 0; i < n; i++)
					record[i] = rs.getString(i + 1);
				data.addElement(record);
			}
		} catch (SQLException e) {
			return 4;
		}
		return 0;
	}

	public Vector<String[]> getData() {
		return data;
	}

	public Vector<String> getColumns() {
		return columns;
	}

	public ResultSet getResult() {
		return result;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String s) {
		additionalInfo = s;
	}

	public void setResult(ResultSet result) {
		try {
			this.result = result;
			if (!result.next()) {
				status = 6;
			} else {
				status = 0;
				;
			}
		} catch (SQLException e) {
			status = 4;
		}
		this.getDatafromRS(result);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
