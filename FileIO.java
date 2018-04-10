package dbms.jen;
import java.sql.*;
import java.io.*;
import java.util.*;

public class FileIO {
	
	/**
	 * This function readStatementsFromFile reads all the SQL statements from a text file
	 * and save each of these statements into a separate element of an ArrayList for easy access
	 * - input parameter: fileURL - a location of a SQL file
	 * - output parameter: an ArrayList in which each element is a complete SQL statement 
	 */
	public static ArrayList<String> readStatementsFromFile(String fileURL) throws IOException{
		
		// Create a file reader and a buffer reader
		// that help to establish a reading path 
		// between your text file and this Java application
		FileReader fr = new FileReader(fileURL);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> SQLstatements = new ArrayList<String>();
		String currentLine, stmt = "";
		
		// Keep reading one line after another until
		// there is no other line or End-Of-File
		while ((currentLine = br.readLine()) != null) {
			// for each line, if that line does not contain ";", we know that
			// the SQL statement has not completed, keep concatenating
			stmt += currentLine + " ";
			if(currentLine.contains(";")){
				// once encountering a ";", we know that the SQL statement is completed
				// save it in the ArrayList SQLstatements
				SQLstatements.add(stmt);
				stmt = "";
			}
		}

		br.close();
		return SQLstatements;
	}

	public static void writeToHTML(String query, ResultSet rs) throws IOException, SQLException{
		// Note: each query has the form as follow
		// "/* 1. The purpose of this query */
		//  Select ... From ... Where"
		//
		// Hence, we will extract "/* 1. The purpose of this query */" as
		// the display information in a HTML, and "1" will be used as a file name
		int sIdx = query.indexOf("select");
		if (sIdx < 0) sIdx = query.indexOf("Select");
		if (sIdx < 0) sIdx = query.indexOf("SELECT");	
		String queryPrompt = query.substring(0, sIdx);
		
		int startIdx = queryPrompt.indexOf("/*");
		int endIdx = queryPrompt.indexOf(".");
		String queryNumber = query.substring(startIdx+2,endIdx);

		// Location and name of the output HTML file that we want to write to
		String htmlFile = "C://Users/jenni_000/Desktop/"+queryNumber+".html";
		
		// Create an output file if it does not exist
		File file = new File(htmlFile);
		if (!file.exists())	file.createNewFile();
		
		// Create a file writer and a buffer writer
		// that establish a writing path 
		// between your output file and this Java application
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		ResultSetMetaData rsMetaData= rs.getMetaData();
		int rsColumnCount = rsMetaData.getColumnCount();
		
		// Prepare the content of a HTML file
		String content = "<!DOCTYPE html><html>" +
						 "<head><title>JDBC - PostgreSQL</title></head>" +
						 "<body><h2>Query Execution Output</h2>" +
						 "<h4>"+ queryPrompt +"</h4>"+
						 "<table border=\"1\">";
		
		// Fill in the rest of the content
		// from ResultSet data
		content += "<tr>";
		for(int i=1; i<=rsColumnCount; i++)
			content += "<th>"+rsMetaData.getColumnName(i)+"</th>";
		content += "</tr>";
		
		while(rs.next()){
			content += "<tr>";
			for(int i=1; i<=rsColumnCount; i++)
				content += "<td>"+rs.getString(i)+"</td>";
			content += "</tr>";
		}
		
		content += "</table></body></html>";	

		// Write to output file 
		// and close the writing path, once finish
		bw.write(content);
		bw.close();
	}
}
