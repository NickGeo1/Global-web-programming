<!-- This page is used in order an administrator to delete a patient -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

  <head>

      <%response.setHeader("Cache-Control","no-cache, no-store, must-invalidate");

      if(session.getAttribute("adminusername") == null)
      {
        request.setAttribute("message",1);
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
      }%>

    <meta charset="utf-8">
    <title>Doctor appointments: delete patient</title>
    <link rel="stylesheet" href="CSS/styles.css">
  </head>

  <body>

    <form method="post" action="admin">

      <div class="imgcontainer">
        <img src="img/logo1.png" alt="logo_image" class="avatar">
      </div>

      <div class="container">
        <center><label><b style="color:#012A6C">Enter the patient's AMKA you want to delete: </b><input type="text" id="patientAMKA" name="patientAMKA"></label></center>
      </div>

      <center><button type="submit" style="font-size:15px;" name="admin_action" id="admin_action" value="delete_patient">Delete patient</button></center>

    </form>

    <div class=navbar>
      <p>Do you want to go back? Click <a href="admin_main_environment.jsp">here</a></p>
    </div>

  </body>

</html>
