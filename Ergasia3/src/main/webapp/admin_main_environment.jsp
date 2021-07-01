<!-- This is the main environment of the Administrator. From this page, every admin who successfully logs in, will be
able to insert a new doctor to the system or delete one doctor from the system -->

<%@ page import="com.classes.Admin" %>
<%@ page import="com.servlets.AdminServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>  <!-- JSP import packages -->

<!DOCTYPE html>

<html lang="en">

    <head>

        <meta charset="utf-8">
        <title>Doctor appointments: Dashboard</title>

        <style>

            /* style rules for the buttons */
            #buttons
            {
                font-size:17px;
                text-align:center;
                color:white;
                background-color: #012A6C;
            }

            /* style rules when hyperlinks are pressed */
            a:visited
            {
                color: #012A6C;
            }

            /* style the hyperlinks in the nav section */
            a
            {
                font-size:14px;
                color: #012A6C;
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
            }

            /* style rules for the logo image that is appeared in the body of the web page */
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

            /* set style rules for the container class */
            .container
            {
                padding: 10px;
            }

            /* style rules about the alert message box */
            .alert
            {
                padding: 20px;
                background-color: #f1f1f1;
                color: #012A6C;
                margin-bottom: 15px;
            }

            /* The close button of the message at the upper of the web page */
            .closebtn
            {
                margin-left: 15px;
                color: #012A6C;
                font-weight: bold;
                float: right;
                font-size: 22px;
                line-height: 20px;
                cursor: pointer;
                transition: 0.3s;
            }

            /* style rules for the article section of the web page */
            article
            {
                margin: 0;
                position: absolute;
                top: 50%;
                left: 50%;
                -ms-transform: translate(-50%, -50%);
                transform: translate(-50%, -50%);
            }

            /* set border and background color of form */
            form
            {
                border: 3px solid whitesmoke;
                background-color: white ;
            }

            /* when moving the mouse over the close button, make it black */
            .closebtn:hover
            {
                color: black;
            }

            /* style rules for the table that is showed to the user */
            table
            {
                align:center;
            }

            /* style rules for every table heading of the table */
            th
            {
                width:120px;
                height:20px;
                text-align:center;
                color:black;
                border:1px solid mediumseagreen;
                padding: 12px 20px;
            }

            /* style rules for every table row of the table */
            tr
            {
                text-align: center;
                padding: 16px;
            }

        </style>

    </head>

    <!-- Show to user a message -->
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <strong>Welcome administrator!</strong> Feel free to use the menu, in order to use the application.
    </div>

    <body>

        <article>

            <br>
            <br>

            <!-- Form that contains a hidden input html tag. The value of the tag is being passed in the patient servlet -->
            <form method="post" id="form" action="admin">

                <input type="hidden" id="admin_action" name="admin_action" value="">

                <div class="imgcontainer">
                    <img src="img/logo1.png" alt="logo_image" class="avatar">
                </div>

                <br>

                <div class="container">
                    <label><pre><b style="color:#012A6C"> Welcome back, admin!</b></pre></label>
                </div>

                <div class="container">

                    <!-- JSP patient instantiation.The patient is the one who just logged on -->
                    <% Admin a = AdminServlet.getAdmin();%>

                    <!--Showing admin's attributes-->
                    <table>
                        <tr><th>Username</th><th>Name</th><th>Surname</th><th>Age</th></tr>
                        <tr><td><%= a.getUsername() %></td><td><%= a.getFirstname() %></td><td><%= a.getSurname() %></td><td><%= a.getAge() %></td></tr>
                    </table>

                </div>

                <br>

                <div class="container">
                    <label><pre><b style="color:#012A6C"> Please select what do you want to do!</b></pre></label>
                </div>

                <center>

                    <div class="container">

                        <button id="buttons" onclick="setAction(2);">Add new doctor</button>

                        <button id="buttons" onclick="setAction(3);">Delete doctor</button>

                        <button id="buttons" onclick="setAction(4);">Logout</button>

                        <br>
                        <br>

                    </div>

                </center>

            </form>

        </article>

        <script>

            //Javascript function that executes for every patient action

            //Depending on the button clicked that describes the action,
            //an action value is being stored in the hidden html input
            //tag (name="patient_action").At the end, we submit the
            //form and we are being redirected to the patient servlet.

            function setAction(a)
            {
                document.getElementById("admin_action").value = a;

                document.getElementById("form").submit();
            }

        </script>

    </body>

</html>
