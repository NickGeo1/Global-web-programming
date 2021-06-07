<!-- This is the main environment of the patient. From this page, every patient that successfully logs in, will be
able to view the history of his appointments, to book a new appointment or watch all the appointments that he has
scheduled in the past-->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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

            /*set styles for span and cancel button on small screens*/
            @media screen and (max-width: 300px)
            {
                span.psw
                {
                    display: block;
                    float: none;
                }
                .cancelbtn
                {
                    width: 100%;
                }
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

            /* style rules for the menu at the bottom of the page */
            .navbar
            {
                font-size: 16px;
                bottom: 0;
                text-align: center;
                background-color: #f1f1f1;
                width: 100%;
                height:20px;
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
                background-color: #012A6C;
                color: white;
                margin-bottom: 15px;
            }

            /* The close button of the message at the upper of the web page */
            .closebtn
            {
                margin-left: 15px;
                color: white;
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

            /*set border and background color of form */
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

        </style>

    </head>

    <!-- Show to user a message -->
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <strong>Success!</strong> Now you can use the application in order to manage your appointments or schedule a new one.
    </div>

    <body>

        <article>

            <!-- Form that contains a hidden input html tag. The value of the tag is being passed in the patient servlet -->
            <form method="post" id="form" action="patient">

                <input type="hidden" id="patient_action" name="patient_action" value="">
                <input type="hidden" id= "showby" name="showby" value="Show all">
                <input type="hidden" id="value" name="value" value="">

                <div class="imgcontainer">
                    <img src="img/logo1.png" alt="logo_image" class="avatar">
                </div>

                <div class="container">
                    <label><b style="color:#012A6C">Please select what do you want to do!</b></label>
                </div>

                <center>

                    <div class="container">

                        <button id="buttons" onclick="setAction(1);">Appointment history</button>

                        <button id="buttons" onclick="setAction(2);">Book an appointment</button>

                        <button id="buttons" onclick="setAction(3);">Scheduled appointments</button>

                        <button id="buttons" onclick="setAction(4);">Logout</button>

                    </div>

                </center>

            </form>

            <br>

            <div class="navbar">
                <p>Do you need help? Check the <a href="instructions.jsp" class="active" style="font-size: 16px;">instructions</a></p>
            </div>

        </article>

        <script>

            //Javascript function that executes for every patient action

            //Depending on the button clicked that describes the action,
            //an action value is being stored in the hidden html input
            //tag (name="patient_action").At the end, we submit the
            //form and we are being redirected to the patient servlet.

            function setAction(a)
            {
                document.getElementById("patient_action").value = a;

                document.getElementById("form").submit();
            }

        </script>

    </body>

</html>
