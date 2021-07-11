<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!-- This is the page in which a doctor will be able to set his availability -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>

  <head>

    <%response.setHeader("Cache-Control","no-cache, no-store, must-invalidate");

      if(session.getAttribute("doctorusername") == null)
      {
        request.setAttribute("message",1);
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
      }%>

    <title>Doctor appointments: set availability</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="CSS/styles.css">
  </head>

  <body>

    <form name="set_availability" method="post" action="doctor">

      <div class="imgcontainer">
        <img src="img/logo1.png" alt="logo_image" class="avatar">
      </div>

      <br>

      <div class="container">
        <label><b style="color:#012A6C"> Please insert one day that you are available for appointment:</b></label>
      </div>

      <br>
      <br>

      <%  Date now = new Date();
          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

          String date_now = df.format(now);
      %>

      <center>
        <b>Enter date that you are available:  </b><input type="datetime-local" name="date_of_appointment" id="date_of_appointment" min="<%= date_now %>" required>
      </center>

      <br>

      <p align="right">
        <button align="right" id="doctor_action" name="doctor_action" value="set availability" type="submit">Add date</button>
      </p>

    </form>

    <br>

    <div class="navbar">
      <p>Do you want to go back? Click <a href="doctor_main_environment.jsp">here</a></p>
    </div>

  </body>

</html>
