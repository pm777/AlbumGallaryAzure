<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.Blob"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String url = "jdbc:sqlserver://assignmentserver.database.windows.net:1433;database=assignmentdb;user=s*****;password=******";
Connection connection = null;
int id=Integer.parseInt(request.getParameter("id"));
try {
	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	connection = DriverManager.getConnection(url);

	// Create and execute a SQL statement.

	Statement stmt = connection.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT photo FROM photos where id="+id);

	while (rs.next()) {
		Blob blob = rs.getBlob("photo");
        byte byteArray[] = blob.getBytes(1, (int)blob.length());
 
        response.setContentType("image/JPEG");
        OutputStream os = response.getOutputStream();
        os.write(byteArray);
        os.flush();
        os.close();
       
	}
}catch(Exception e){
	
    e.printStackTrace();
} 
%>
</body>
</html>
