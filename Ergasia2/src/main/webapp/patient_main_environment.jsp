<!-- This is the main environment of the patient -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <meta charset="utf-8">
        <title>ONLINE DOCTOR APPOINTMENTS</title>

        <style>

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
                    <td><a href="patientappointments.jsp">Appointment history</a></td>
                    <td><a href="">Book an appointment</a></td>
                    <td><a href="">Cancel appointment</a></td>
                    <td><a href="">Logout</a></td>
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

    </body>

</html>
