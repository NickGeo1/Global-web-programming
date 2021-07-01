<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.classes.Patient" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Τhis is the page in which a patient will be able to book an appointment with a doctor -->
<!DOCTYPE html>

<html>

    <head>

        <title>Doctor appointments: book appointment</title>
        <meta charset="utf-8">

        <style>

            /* set border and background colour to the form */
            form
            {
                border: 3px solid whitesmoke;
                background-color: white ;
            }

            /* the whole page has the same font */
            *
            {
                font-family:candara;
            }

            /* style rules for the body of the page */
            body
            {
                background-color: seagreen;
                margin: 0;
                position: absolute;
                top: 50%;
                left: 50%;
                -ms-transform: translate(-50%, -50%);
                transform: translate(-50%, -50%);
            }

            /* set styles for the inputs of the login form */
            input[type=text],
            input[type=password]
            {
                width: 30%;
                padding: 12px 20px;
                margin: 8px 0;
                box-sizing: border-box;
                border:1px solid mediumseagreen;
            }

            /* set a style for the buttons */
            button
            {
                background-color: mediumseagreen;
                color: white;
                padding: 10px 18px;
                margin: 8px 0;
                border: none;
                cursor: pointer;
                width: auto;
            }

            /* set a hover effect for the button */
            button:hover
            {
                opacity: 0.8;
            }

            /* centre the display image inside the container */
            .imgcontainer
            {
                text-align: center;
                margin: 24px 0 12px 0;
            }

            /* set image properties */
            img.avatar
            {
                width: 16%;
                border-radius: 70%;
            }

            /* set padding to the container */
            .container
            {
                padding: 16px;
            }

            /* style rules for the select section */
            select
            {
                width: 25%;
                height:30px;
                margin: 8px 0;
                display: inline-block;
                box-sizing: border-box;
                border:1px solid #012A6C;
            }

        </style>

    </head>

    <body>

        <form action="patient" method="post" id="form">

            <div class="imgcontainer">
                <img src="img/logo1.png" alt="logo_image" class="avatar">
            </div>

            <div class="container">
                <label><b style="color:#012A6C">Hello, let's book your appointment!</b></label>
            </div>

            <%  Date now = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                String date_now = df.format(now);
            %>

            <div class="container">
                <label><b>1) Please select the time interval you want to search appointments in:</b></label>
            </div>

            <br>

            <center>

                <b style="color:#012A6C;">Starting date:  </b><input type="date" name="start" id="start" onchange="set_min_end_date();" required min=<%= date_now %>>

                <br>
                <br>

                <b style="color:#012A6C;">Ending date:  </b><input type="date" name="end" id="end" min="" required>

            </center>

            <br>

            <div class="container">
                <label><b>2) Please select the category you want to search appointments by:</b></label>
            </div>

            <center>
                <select name="searchby" id="searchby" onclick="checkoption();">
                    <option selected value="Show all">Show all</option>
                    <option value="Doctor AMKA">Doctor AMKA</option>
                    <option value="Specialty">Specialty</option>
                    <option value="Full name">Full name</option>
                </select>
            </center>

            <br>

            <div class="container">
                <label><b>3) Please insert the value of the category you want to search appointments by:</b></label>
            </div>

            <br>

            <center>

                <b style="color:#012A6C;"  id="firstname"></b>
                <input type="text" id="value" name="value" disabled="true" required>

                <br>

                <b style="color:#012A6C;"  id="lastname" ></b>
                <input type="text" id="value2" name="value2" hidden="true">

                <br>
                <br>

                <button type="submit" onclick="setvalue(2)">Search</button>
            </center>

            <br>

            <input type="hidden" name="patient_action" id="patient_action" value="2">
            <input type="hidden" name="datevalue" id="datevalue" value="">
            <input type="hidden" name="startvalue" id="startvalue" value="">
            <input type="hidden" name="endvalue" id="endvalue" value="">
            <input type="hidden" name="dAMKA" id="dAMKA" value="">


            <br>
            <br>

            <%= Patient.getHTML() %>
            <% Patient.clearHTML(); %>

        </form>

        <script>

            function setvalue(v)
            {
                document.getElementById("patient_action").value = v;
            }

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
                var s = document.getElementById("searchby");
                var o = s.options[s.selectedIndex].value;

                if(o == "Show all")
                {
                    document.getElementById("value").value = document.getElementById("value2").value = document.getElementById("lastname").innerHTML = document.getElementById("firstname").innerHTML = "";
                    document.getElementById("value").disabled = true;
                    document.getElementById("value2").hidden = true;
                    document.getElementById("value2").required = false;
                }
                else if(o == "Full name")
                {
                    document.getElementById("firstname").innerHTML = "Firstname: ";
                    document.getElementById("value2").hidden = document.getElementById("value").disabled = false;
                    document.getElementById("value2").required = true;
                    document.getElementById("lastname").innerHTML = "Lastname: ";
                }
                else
                {
                    document.getElementById("value").value = document.getElementById("value2").value = document.getElementById("lastname").innerHTML = document.getElementById("firstname").innerHTML = "";
                    document.getElementById("value").disabled = false;
                    document.getElementById("value2").hidden = true;
                    document.getElementById("value2").required = false;
                }

            }

            function bookappointment(date,start,end,dAMKA)
            {
                var choice = confirm("Are you sure that you want to book this appointmnt?");

                if(choice)
                {
                    document.getElementById("datevalue").value = date;
                    document.getElementById("startvalue").value = start;
                    document.getElementById("endvalue").value = end;
                    document.getElementById("dAMKA").value = dAMKA;

                    document.forms[0].submit();
                }
            }

        </script>

    </body>

</html>
