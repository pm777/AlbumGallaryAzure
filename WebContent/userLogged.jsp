<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body><center>
<h1>Picture viewing and upload options for : <%= (session.getAttribute("currentSessionUser")) %> </h1>
 
 <h3>You can upload an image here</h3>
 <form action = "UploadServlet" method = "post" enctype = "multipart/form-data">
         <br>
         <input type = "file" name = "file" size = "50" />
         <br><br>
         <input type="text" name="title"  placeholder="Enter Title for Image"/>
         <br><br>
         <input type = "submit" value = "Upload File" />
      </form>
     <br><br>
     <hr><hr>
     <h3>You can view all photos here : </h3>
     <a href="viewImage.jsp">Photos</a>
     </center>
</body>
</html>