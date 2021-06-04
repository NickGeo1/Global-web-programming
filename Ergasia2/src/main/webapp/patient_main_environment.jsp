<!-- This is the main environment of the patient -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html lang="en">

    <head>

        <meta charset="utf-8">
        <title>ONLINE DOCTOR APPOINTMENTS</title>

        <style>

            /* style rules for the buttons */
            #buttons
            {
                font-size:17px;
                text-align:center;
                color:white;
                background-color: #012A6C;
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

            /* style rules for the article part of body section */
            .article1
            {
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
            }

            /* style rules for every h1 */
            h1
            {
                font-size:25px;
                text-align:center;
                color: #012A6C;
                letter-spacing:6px;
            }

            /* style rules for the menu section of the web page */
            nav
            {
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
            }

            /* style rules when hyperlinks are active */
            a:active
            {
                background-color: seagreen;
            }

            /* style rules when hyperlinks are pressed */
            a:visited
            {
                color: #012A6C;
            }

            /* style the hyperlinks in the nav section */
            a
            {
                font-size:19px;
                color: #012A6C;
            }

            /* style rules for the header part of the page */
            header
            {
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
                margin-top:30px;
            }

            /* style rules for every field of the table (inside the login form) */
            td
            {
                width:200px;
                height:20px;
                text-align:center;
                color:white;
            }

        </style>

    </head>

    <br>

    <body id="head">

        <header>
            <h1>Welcome to the online doctor appointments!</h1>
        </header>

        <br>
        <br>

        <nav>

            <table align="center">

                <tr>
                    <!-- Form that contains a hidden input html tag. The value of the tag is being passed in the patient servlet -->
                    <form method="post" id="form" action="patient">
                        <input type="hidden" id="patient_action" name="patient_action" value="">
                    </form>

                    <td><button id="buttons" onclick="setAction(1);">Appointment history</button></td>
                    <td><button id="buttons" onclick="setAction(2);">Book an appointment</button></td>
                    <td><button id="buttons" onclick="setAction(3);">Scheduled appointments</button></td>
                    <td><button id="buttons" onclick="setAction(4);">Logout</button></td>
                </tr>

            </table>

        </nav>

        <br>
        <br>

        <article class="article1">

            <br>
            <br>

            <center>
                <h5>Edo tha fenontai ta stoixeia tou kiriou astheni</h5>
            </center>

            <br>
            <br>

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
