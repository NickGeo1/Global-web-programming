<%@ page import="com.servlets.PatientServlet" %>
<%@ page import="com.classes.Patient" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
<title>Scheduled appointments</title>
<link rel="stylesheet" href="CSS/styles.css">
</head>

<body>

<form action="patient" method="post" id="form">
<div class="imgcontainer">
<img src="img/logo1.png" alt="logo_image" class="avatar">
</div>
<br>

<%=
    Patient.getHTML()
%>


</table>

<br><br>
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
<input type="text" id="value" name="value" disabled="true">
<button type="submit">Search</button>
<input type="hidden" name="patient_action" value="3">
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
</script>

</form>

<br><br>
<div class=navbar>
<p>Do you want to go back? Click <a href="patient_main_environment.html">here</a></p>
</div>


</body>
</html>
