/*****************************************************/
/* CS31A - Summer 2017                               */
/* HW3: JDBC                                         */
/* Student Name: Jennifer Wong                       */
/* SID: 11211711                                     */
/*****************************************************/

package dbms.jen;
import java.io.IOException;
import java.sql.*;

public class MyJDBC {
	public final static String PORT = "5432";
	public final static String DATABASE_NAME = "Mondial";
	public final static String USERNAME = "postgres";
	public final static String PSSWD = "pUser";
	public final static String URL = "jdbc:postgresql://localhost:" + PORT + "/" + DATABASE_NAME;
	public final static String CREATE_TABLES_DDL_URL = "C://Users/jenni_000/Desktop/Foothill/CS 31A (Database)/Assignment 3/mondial-schema.txt";
	public final static String INSERT_VALUES_DDL_URL = "C://Users/jenni_000/Desktop/Foothill/CS 31A (Database)/Assignment 3/mondial-inputs.txt";
	public final static String QUERY_DML_URL = "C://Users/jenni_000/Desktop/Foothill/CS 31A (Database)/Assignment 3/mondial-queries.txt";
	
	public static void main(String[] args) throws SQLException, IOException {
		Connection connection = DriverManager.getConnection(URL, USERNAME, PSSWD);
		
		if(SQLBuilder.createTables(connection, CREATE_TABLES_DDL_URL)) 
			System.out.println("Successfully created all tables.");
		
		if(SQLLoader.insertValues(connection, INSERT_VALUES_DDL_URL))
			System.out.println("Successfully inserted all values.");
		
		if(SQLRunner.execute(connection, QUERY_DML_URL))
			System.out.println("Successfully executed and outputed all results.");
		
		connection.close();
	}
}