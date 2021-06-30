<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %><%--
  Created by IntelliJ IDEA.
  User: nikos
  Date: 30/6/2021
  Time: 8:05 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book an appointment</title>
    <h1>Book an appointment</h1>
</head>
<body>

<form action="patient" method="post" id="form">

    <%  Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String date_now = df.format(now);
    %>

    <b>Please select the time interval you want to search appointments in:</b>
    <br>
    Starting date:<input type="date" name="start" id="start" onchange="set_min_end_date();" min=<%= date_now %>>
    Ending date:<input type="date" name="end" id="end" min="">
    <br><br>

    <b>Please select the category you want to search appointments by:</b>
    <br>
    <select name="showby" id="showby" onclick="checkoption();">
        <option selected value="Show all">Show all</option>
        <option value="Doctor AMKA">Doctor AMKA</option>
        <option value="Specialty">Specialty</option>
        <option value="Full name">Full name</option>
    </select>
    <br><br>
    <b>Please insert the value of the category you want to search appointments by:</b>
    <br>
    <div id="firstname"></div>
    <input type="text" id="value" name="value" disabled="true"><br>
    <div id="lastname"></div>
    <input type="text" id="value2" name="value2" hidden="true">



    <script>
        function set_min_end_date() //set min date for ending date tag equal to start_date + 1
        {
            var start_date = new Date(document.getElementById("start").value);
            start_date.setDate(start_date.getDate() + 1)

            document.getElementById("end").setAttribute("min", convert_date(start_date));
            document.getElementById("end").value = convert_date(start_date);
        }

        function convert_date(start_date) //format date to yyyy-MM-dd
        {
            var year = start_date.getFullYear();

            var month = start_date.getMonth() + 1;

            if(month.toString().length === 1)
                month = "0" + month;

            var day = start_date.getDate();

            if(day.toString().length === 1)
                day = "0" + day;

            start_date = year + "-" + month + "-" + day

            return start_date;
        }

        function checkoption()
        {
            var s = document.getElementById("showby");
            var o = s.options[s.selectedIndex].value;

            if(o == "Show all")
            {
                document.getElementById("value").value = document.getElementById("value2").value = document.getElementById("lastname").innerHTML = document.getElementById("firstname").innerHTML = "";
                document.getElementById("value2").hidden = document.getElementById("value").disabled = true;
            }
            else if(o == "Full name")
            {
                document.getElementById("firstname").innerHTML = "Firstname:";

                document.getElementById("value").disabled = false;
                document.getElementById("value2").hidden = false;
                document.getElementById("lastname").innerHTML = "Lastname:";
            }
            else
            {
                document.getElementById("value").value = document.getElementById("value2").value = document.getElementById("lastname").innerHTML = document.getElementById("firstname").innerHTML = "";
                document.getElementById("value").disabled = false;
                document.getElementById("value2").hidden = true;
            }

        }

    </script>

</form>

</body>
</html>
