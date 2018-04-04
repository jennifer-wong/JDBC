/*****************************************************/
/* CS31A - Summer 2017                               */
/* HW3: JDBC                                         */
/* Student Name: Jennifer Wong                       */
/* SID: 11211711                                     */
/*****************************************************/

package dbms.jen;
import java.sql.*;
import java.io.*;
import java.util.*;

public class SQLBuilder {
	
	public static boolean createTables(Connection connection, String fileURL) throws IOException, SQLException {
		
		ArrayList<String> SQLStatements = FileIO.readStatementsFromFile(fileURL);
		connection.setAutoCommit(false);
		Statement stmt = connection.createStatement();
		
		int rowsAffected;

		for(String query: SQLStatements) {
			rowsAffected = stmt.executeUpdate(query);

			if (rowsAffected != 0) {
				return false;
			}
		}

		connection.commit();
		connection.setAutoCommit(true);
		stmt.close();
		
		return true;
	}
	
}