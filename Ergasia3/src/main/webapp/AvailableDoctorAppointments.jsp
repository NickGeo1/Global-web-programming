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
    <h1>Please select the time interval you want to search appointments in:</h1><br>
    <form action="patient" method="post" id="form">

        <%  Date now = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            String date_now = df.format(now);
        %>

        Starting date:<input type="date" name="start" id="start" onchange="set_min_end_date();" min=<%= date_now %>>
    Ending date:<input type="date" name="end" id="end" min="">

    </form>

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

    </script>

</head>
<body>

</body>
</html>
