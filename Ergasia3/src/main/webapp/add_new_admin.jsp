<!-- This page is used in order an administrator to insert a new administrator to the system -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <meta charset="utf-8">
        <title>Doctor appointments: add administrator</title>
        <link rel="stylesheet" href="CSS/styles.css">
    </head>

    <body>

    <form method="post" action="admin">

        <div class="imgcontainer">
            <img src="img/logo1.png" alt="logo_image" class="avatar">
        </div>

        <div class="container">
            <label><b style="color:#012A6C"> Add a new administrator to the system:</b></label>
        </div>
        <br>

        <label>Username</label> <br>
        <input type="text"     id="username"  name="username"  size="20" maxlength="45">
        <br>

        <label>Password</label><br>
        <input type="password" id ="password" name="password"  size="20" maxlength="45">
        <br>

        <label>First Name</label><br>
        <input type="text"     id="firstname" name="firstname" size="20" maxlength="45">
        <br>

        <label>Last Name</label><br>
        <input type="text"     id="surname"   name="surname"   size="20" maxlength="45">
        <br>

        <label>Age</label><br>
        <input type="number"   id="age"       name="age"       max="119" min="1">
        <br>

        <button type="submit" name="admin_action" id="admin_action" value="add_doctor">Add Administrator</button>

    </form>

        <div class=navbar>
            <p>Do you want to go back? Click <a href="admin_main_environment.jsp">here</a></p>
        </div>
    </body>

</html>
