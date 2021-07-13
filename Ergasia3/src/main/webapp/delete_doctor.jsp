<!-- This page is used in order an administrator to delete a doctor -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

       <%//make page invalidate in every access and dont store it to cache in order to prevent access with back button after logout
       response.setHeader("Cache-Control","no-cache, no-store, must-invalidate");

        if(session.getAttribute("adminusername") == null) //if admin username attribute is null that means admin is no more logged on
        {
            request.setAttribute("message",1);
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response); //forward the user back to login page and show "Session time out message"
        }%>

        <meta charset="utf-8">
        <title>Doctor appointments: delete doctor</title>
        <link rel="stylesheet" href="CSS/styles.css">

    </head>

    <body>

        <form method="post" action="admin">

            <div class="imgcontainer">
                <img src="img/logo1.png" alt="logo_image" class="avatar">
            </div>

            <div class="container">
                <center><label><b style="color:#012A6C">Enter the doctor's AMKA you want to delete: </b><input type="text" id="doctorAMKA" name="doctorAMKA" minlength="11" maxlength="11"></label></center>
            </div>

            <center><button style="font-size:15px;" type="submit" name="admin_action" id="admin_action" value="delete_doctor">Delete doctor</button></center>

        </form>

        <div class=navbar>
            <p>Do you want to go back? Click <a href="admin_main_environment.jsp">here</a></p>
        </div>

    </body>

</html>
