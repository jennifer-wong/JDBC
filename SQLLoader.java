package dbms.jen;
import java.sql.*;
import java.io.*;
import java.util.*;

public class SQLLoader {
	
	public static boolean insertValues(Connection connection, String fileURL) throws IOException, SQLException {
		
		ArrayList<String> SQLStatements = FileIO.readStatementsFromFile(fileURL);
		connection.setAutoCommit(false);
		Statement stmt = connection.createStatement();
		
		int rowsAffected;

		for(String query: SQLStatements) {
			rowsAffected = stmt.executeUpdate(query);

			if (rowsAffected != 1) {
				if (query.toLowerCase().contains("commit")) {
					continue;
		   		}
				else {
					return false;
		    		}
			}
		}

		connection.commit();
		connection.setAutoCommit(true);
		stmt.close();
		
		return true;
	}
	
}
