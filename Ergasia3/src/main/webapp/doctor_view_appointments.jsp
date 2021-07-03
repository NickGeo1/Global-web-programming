<!-- In this page, a doctor is able to view all his appointments -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <%if(session.getAttribute("username") == null)
        {
            request.setAttribute("message",1);
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }%>

        <title>Doctor appointments: scheduled appointments</title>
        <link rel="stylesheet" href="CSS/styles.css">
        <meta charset="utf-8">
    </head>

    <body>

        <article>

            <div class="imgcontainer">
                <img src="img/logo1.png" alt="logo_image" class="avatar">
            </div>

            <br>

            <div class="container">
                <label><pre><b style="color:#012A6C"> Hello doctor! Here are your appointments:</b></pre></label>
            </div>

            <div class="container">

                <!--Showing doctor's appointments-->
                <table>
                    <tr><th>Date</th><th>Start time</th><th>End time</th><th>Patient's AMKA</th><th>Doctor's AMKA</th></tr>
                </table>

            </div>

        </article>

        <br>

        <div class=navbar>
            <p>Do you want to go back? Click <a href="doctor_main_environment.jsp">here</a></p>
        </div>

    </body>

</html>
