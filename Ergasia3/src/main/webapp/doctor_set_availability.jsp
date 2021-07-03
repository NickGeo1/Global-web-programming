<!-- This is the page in which a doctor will be able to set his availability -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>

  <head>

    <%if(session.getAttribute("username") == null)
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

    <form name="set_availability">

      <div class="imgcontainer">
        <img src="img/logo1.png" alt="logo_image" class="avatar">
      </div>

      <br>

      <div class="container">
        <label><b style="color:#012A6C"> Please insert one day that you are available for appointment:</b></label>
      </div>

      <br>
      <br>

      <center>
        <b>Enter date that you are available:  </b><input type="date" name="date_of_appointment" id="date_of_appointment" min="" required>
      </center>

      <br>

      <p align="right">
        <button align="right" type="submit">Add date</button>
      </p>

    </form>

    <br>

    <div class="navbar">
      <p>Do you want to go back? Click <a href="doctor_main_environment.jsp">here</a></p>
    </div>

  </body>

</html>
