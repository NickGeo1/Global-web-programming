<!-- In this page, patient will be able to watch all of his scheduled appointments -->
<%@ page import="com.classes.Patient" %>
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

        <title>Doctor appointments: scheduled appointments</title>
        <link rel="stylesheet" href="CSS/styles.css">
        <meta charset="utf-8">

    </head>

    <body>

        <form action="patient" method="post" id="form">

            <div class="imgcontainer">
                <img src="img/logo1.png" alt="logo_image" class="avatar">
            </div>

            <br>

            <%= Patient.getHTML() %>

            <% Patient.clearHTML();%>

            </table>

            <br>
            <br>

            <div class="container">
                <label><b style="color:#012A6C">Choose a category to search appointments by:  </b></label>

                <select name="showby" id="showby" onclick="checkoption();">
                    <option selected value="Show all">Show all</option>
                    <option value="Doctor AMKA">Doctor AMKA</option>
                    <option value="Date">Date</option>
                    <option value="Specialty">Specialty</option>
                </select>
            </div>

            <div class="container">
                <label for="value"><b style="color:#012A6C">Insert the doctor's AMKA/appointment date/speciality:  </b></label>
                <input type="text" id="value" name="value" disabled="true" required>
                <button type="submit" onclick="setvalue(3);">Search</button>

                <input type="hidden" name="patient_action" id="patient_action" value="3">

                <input type="hidden" name="datevalue" id="datevalue" value="">
                <input type="hidden" name="patientAMKA" id="patientAMKA" value="">
                <input type="hidden" name="doctorAMKA" id="doctorAMKA" value="">
            </div>

        </form>

        <br>

        <div class=navbar>
            <p>Do you want to go back? Click <a href="patient_main_environment.jsp">here</a></p>
        </div>

        <script>
            function checkoption()
            {
                var s = document.getElementById("showby");
                var o = s.options[s.selectedIndex].value;

                if(o == "Show all")
                {
                    document.getElementById("value").disabled = true;
                    document.getElementById("value").value = "";
                }
                else
                {
                    document.getElementById("value").disabled = false;
                }

            }

            function setvalue(v)
            {
                document.getElementById("patient_action").value = v;
            }

            function cancelappointment(d,pAMKA,dAMKA)
            {
                var choice = confirm("Are you sure that you want to cancel this appointmnt?");

                if(choice)
                {
                    document.getElementById("datevalue").value = d;
                    document.getElementById("patientAMKA").value = pAMKA;
                    document.getElementById("doctorAMKA").value = dAMKA;

                    document.forms[0].submit();
                }
            }

        </script>

    </body>

</html>
