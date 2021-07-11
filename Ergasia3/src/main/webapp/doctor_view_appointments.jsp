<!-- In this page, a doctor is able to view all his appointments -->
<%@ page import="java.util.Calendar" %>
<%@ page import="com.classes.Users" %>
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

        <title>Doctor appointments: scheduled appointments</title>
        <link rel="stylesheet" href="CSS/styles.css">
        <meta charset="utf-8">
    </head>

    <body>

    <form action="doctor" method="post" id="form">

        <div class="imgcontainer">
            <img src="img/logo1.png" alt="logo_image" class="avatar">
        </div>

        <br>

        <%= Users.getHTML() %>
        <% Users.clearHTML(); %>

        <br>
        <br>

        <div class="container">
            <label><b style="color:#012A6C">Choose an interval to show appointments by:  </b></label>

            <select name="showby" id="showby" onclick="checkoption();">
                <option selected value="Week">Week</option>
                <option value="Month">Month</option>
            </select>
        </div>


        <%  Calendar cal = Calendar.getInstance();
            int curr_week = cal.get(Calendar.WEEK_OF_YEAR);
            int curr_year = cal.get(Calendar.YEAR);
            Integer month = cal.get(Calendar.MONTH) + 1;

            String curr_month =  month.toString();

            if(curr_month.length() == 1)
                curr_month =  "0"+curr_month;
        %>



        <div class="container">
            <label for="week"><b style="color:#012A6C" id="txt">Choose a week: </b></label>
            <input type="week" id="week" min="<%=curr_year%>-W<%=curr_week%>" name="week" required>
            <input type="month" id="month" min="<%=curr_year%>-<%=curr_month%>" name="month" hidden = "true">
            <button type="submit">Search</button>

            <input type="hidden" name="doctor_action" id="doctor_action" value="view appointments">

            <input type="hidden" name="datevalue" id="datevalue" value="">
            <input type="hidden" name="patientAMKA" id="patientAMKA" value="">


        </div>

    </form>

    <br>

    <div class="navbar">
        <p>Do you want to go back? Click <a href="doctor_main_environment.jsp">here</a></p>
    </div>

    <script>
        function checkoption()
        {
            var s = document.getElementById("showby");
            var o = s.options[s.selectedIndex].value;

            if(o == "Week")
            {
                document.getElementById("txt").innerHTML = "Choose a week:";
                document.getElementById("month").hidden = true;
                document.getElementById("month").required = false;
                document.getElementById("month").value = "";
                document.getElementById("week").hidden = false;
                document.getElementById("week").required = true;
            }
            else
            {
                document.getElementById("txt").innerHTML = "Choose a month:";
                document.getElementById("week").hidden = true;
                document.getElementById("week").required = false;
                document.getElementById("week").value = "";
                document.getElementById("month").required = true;
                document.getElementById("month").hidden = false;
            }

        }

         function cancelappointment(d,pAMKA)
         {
             var choice = confirm("Are you sure that you want to cancel this appointment?");

             if(choice)
             {
                 document.getElementById("datevalue").value = d;
                 document.getElementById("patientAMKA").value = pAMKA;

                 document.forms[0].submit();
            }
         }

    </script>

    </body>

</html>
