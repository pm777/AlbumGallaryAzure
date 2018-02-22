package com.cloud.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Servlet implementation class VoteServlet
 */
@WebServlet("/VoteServlet")
public class VoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int id=Integer.parseInt(request.getParameter("id"));
		int rating=Integer.parseInt(request.getParameter("rating"));
		float avg=0;
		int count=1;
		int totalRatings=0;
		String url = "jdbc:sqlserver://******.database.windows.net:1433;database=*****;user=*****;password=*******";

		// DB connection
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(url);

			// Create and execute a SQL statement.

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT count,totalRatings FROM photos where id="+id);
			System.out.println("id55="+id);
			System.out.println("Count55="+count);
			
			
			while (rs.next()) {
				count=count+Integer.parseInt(rs.getString(1));
				
				totalRatings=Integer.parseInt(rs.getString(2));
			}
			System.out.println("Count64="+count);
			
			totalRatings=totalRatings+rating;
			rating=(totalRatings)/count;
			
			//Insert rating into DB
			String updateSql = "UPDATE Photos SET totalRatings =? , count=?,rating=? WHERE id=?";
			PreparedStatement statement = connection.prepareStatement(updateSql);  
			statement.setInt(1, totalRatings);
			statement.setInt(2, count);
			statement.setFloat(3, rating);
			statement.setInt(4, id);
			
			System.out.println("totalRating="+totalRatings);
			System.out.println("Rating="+rating);
			System.out.println("Count="+count);
			// Sends the statement to the database server
			int row = statement.executeUpdate();
			if (row > 0) {
				System.out.print("Ratings saved into database");
			}
			
			// End DB connection
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("viewImage.jsp"); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
