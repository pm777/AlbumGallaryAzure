package com.cloud.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		// Connect to database
		String url = "jdbc:sqlserver://*****.database.windows.net:1433;database=assignmentdb;user=********;password=******";

		// DB connection
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(url);
			String schema = connection.getSchema();
			System.out.println("Successful connection - Schema: " + schema);

			InputStream inputStream = null; // input stream of the upload file
			// obtains the upload file part in this multipart request
			Part filePart = request.getPart("file");
			//Refer
			//https://www.programcreek.com/java-api-examples/index.php?class=javax.servlet.http.part&method=getSize
			if (filePart != null) {
				// prints out some information for debugging
				System.out.println(filePart.getName());
				System.out.println(filePart.getSize());
				System.out.println(filePart.getContentType());
				
				if(filePart.getSize()>1048576)
					response.sendRedirect("userLogged.jsp");

				// obtains input stream of the upload file
				inputStream = filePart.getInputStream();
			}

			System.out.println("Query data example:");
			System.out.println("=========================================");

			// Create and execute a SQL statement.
			String selectSql = "INSERT INTO photos (title,time,date,likes,photo,count,totalRatings,Rating) values (?, ?, ?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(selectSql);  
			statement.setString(1, request.getParameter("title"));
			//statement.setString(1, filePart.getName());
			
			//Get today's date
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date today = new Date();
			String todayWithZeroTime = formatter.format(today);
			
			//Get Current Time			
		     SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm:ss" );
		     String time = sdf.format( today);
		     
			statement.setString(2,time);
			statement.setString(3, todayWithZeroTime);
			statement.setString(4, "2");
			
			if (inputStream != null) {
				// fetches input stream of the upload file for the blob column
				statement.setBlob(5, inputStream);
			}
			statement.setString(6, "0");
			statement.setString(7, "0");
			statement.setString(8, "0");
			
			
			// sends the statement to the database server
						int row = statement.executeUpdate();
						if (row > 0) {
							System.out.print("File uploaded and saved into database");
						}
			
			/*
			 * try (Statement statement = connection.createStatement(); ResultSet resultSet
			 * = statement.executeQuery(selectSql)) {
			 * 
			 * // Print results from select statement
			 * System.out.println("Top 20 categories:"); while (resultSet.next()) {
			 * System.out.println(resultSet.getString(1)); } connection.close();
			 * 
			 * }
			 */
						
			// End DB connection
			connection.close();
			response.sendRedirect("userLogged.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
