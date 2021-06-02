<!-- This page is used when a user inserts wrong credentials to the login form -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <meta charset="utf-8">
        <title>Failed to login</title>

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
                margin: 0;
                position: absolute;
                top: 50%;
                left: 50%;
                -ms-transform: translate(-50%, -50%);
                transform: translate(-50%, -50%);
            }

            /* style rules for the section part of body */
            section
            {
                border-radius: 25px;
                background: white;
                width: 500px;
            }

            /* style rules for the image */
            img
            {
                width:400px;
                height:400px;
            }

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

            /* style rules for every paragraph */
            p
            {
                font-size: 19px;
                text-align:center;
                color: #012A6C;
            }

            /* style rules for the footer section of the website */
            footer
            {
                border-radius: 25px;
                background: white;
                width: 500px;
            }

        </style>

    </head>

    <body>

        <section>
            <center>
                <img src="img/stop.png" alt="failed to login">
            </center>
        </section>

        <br>
        <br>

        <footer>
            <center>
                <p>Go back to the start page. Click <a href="index.jsp">here</a></p>
            </center>
        </footer>

    </body>

</html>
