<!-- This page is used in order an administrator to insert a new doctor to the system -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <title>Doctor appointments: add doctor</title>
        <link rel="stylesheet" href="CSS/styles.css">

        <style>

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

            /* style rules for the inputs boxes in the form */
            input[type=text],
            input[type=password],
            input[type=number]
            {
                width: 100%;
                padding: 12px 20px;
                margin: 8px 0;
                display: inline-block;
                box-sizing: border-box;
                border:1px solid mediumseagreen;
            }

            /* set a style for the buttons */
            button
            {
                background-color: mediumseagreen;
                color: white;
                padding: 6px 10px;
                border: none;
                margin: 10px;
                cursor: pointer;
                width: auto;
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

            /* set a hover effect for the button */
            button:hover
            {
                opacity: 0.8;
            }

        </style>

    </head>

    <body>

        <form method="post" action="admin">

            <div class="imgcontainer">
                <img src="img/logo1.png" alt="logo_image" class="avatar">
            </div>

            <div class="container">
                <label><b style="color:#012A6C"> Add a new doctor to the system:</b></label>
            </div>

            <div class="container">

                <label><b style="color:#012A6C">Username: *</b></label>
                <input type="text"  id="username" name="username"   size="20" maxlength="45" required>

                <label><b style="color:#012A6C">Password: *</b></label>
                <input type="password" id ="password"  name="password"   size="20" maxlength="45" required>

                <label><b style="color:#012A6C">First name: *</b></label>
                <input type="text"     id="firstname"  name="firstname"  size="20" maxlength="45" required>

                <label><b style="color:#012A6C">Last name: *</b></label>
                <input type="text"     id="surname"    name="surname"    size="20" maxlength="45" required>

                <label><b style="color:#012A6C">Age: *</b></label>
                <input type="number"   id="age"        name="age"    max="119" min="1" required>

                <label><b style="color:#012A6C">Speciality: *</b></label><br>

                    <select id="speciality" name="speciality" required>
                        <option value="Ophthalmologist">Ophthalmologist</option>
                        <option value="Pathologist">Pathologist</option>
                        <option value="Orthopedist">Orthopedist</option>
                    </select><br>

                <label><b style="color:#012A6C">Doctor's AMKA: *</b></label>
                <input type="text"     id="AMKA"       name="AMKA"       size="11" maxlength="11" minlength="11" required>

            </div>

            <button style="font-size:15px;" type="submit" name="admin_action" id="admin_action" value="add_doctor">Add doctor</button>

        </form>

        <div class=navbar>
            <p>Do you want to go back? Click <a href="admin_main_environment.jsp">here</a></p>
        </div>

    </body>

</html>
