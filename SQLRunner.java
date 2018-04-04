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

public class SQLRunner {
	
	public static boolean execute(Connection connection, String fileURL) throws IOException, SQLException {
		
		ArrayList<String> SQLStatements = FileIO.readStatementsFromFile(fileURL);
		connection.setAutoCommit(false);
		Statement stmt = connection.createStatement();
		
		ResultSet rs = null;

		for(String query: SQLStatements) {
			rs = stmt.executeQuery(query);
			
			if(rs == null) {
				return false;
			}
			
			FileIO.writeToHTML(query, rs);
		}
		
		connection.commit();
		connection.setAutoCommit(true);
		stmt.close();
		
		if(rs != null) {
			rs.close();
		}
		
		return true;
	}

}