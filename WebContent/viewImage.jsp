<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.Blob"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Blob"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Photos</title>
<style>
#forText {
    width: 160px;
    padding: 7px;
    border: 1px solid gray;
    margin: 0;
}

#forImg {
    width: 420px;
    padding: 7px;
    border: 2px solid gray;
    margin: 0;
}

</style>
</head>
<body>
<%
String url = "jdbc:sqlserver://assignmentserver.database.windows.net:1433;database=assignmentdb;user=******;password=*******";

// DB connection
Connection connection = null;
try {
	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	connection = DriverManager.getConnection(url);

	// Create and execute a SQL statement.

	Statement stmt = connection.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT id,title,rating FROM photos");

	while (rs.next()) {
		
		  int id = rs.getInt("id");
		  String title=rs.getString("title");
		  float rating=rs.getFloat("rating");
		  
		  //Add vote button in form and pass id to db
		  %>
		  <div id="forText">
		  <h4>Title : <%=title %></h4>
		  <h4 style="display: inline">Rating : <%=rating %></h4>
		  </div>
		  <center>
	 <form action="VoteServlet">
          <input type="hidden" name="id" value=<%=id%> >
          <input type="text" placeholder="You can rate 0-5" name="rating">
          <input type="submit" value="Vote">
      </form>
      
      <br><div id="forImg">
	<img src="getImage.jsp?id=<%=id%>" width="400px" />
	</div>
	</center>
	<br>
    <hr>
	<%
	}
	// End DB connection
	connection.close();

	
}catch (Exception e) {
	e.printStackTrace();
}
%>


</body>
</html>
