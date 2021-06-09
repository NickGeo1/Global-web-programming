<!-- This page is appeared when a user inserts wrong credentials to the login form -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv = "refresh" content = "5; url = login.jsp" />
        <title>Doctor appointments: failed to login</title>

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

            /* style rules for the image */
            img
            {
                width:400px;
                height:400px;
            }

            /* style rules when hyperlinks are pressed */
            a:visited
            {
                color: #012A6C;
            }

            /* style the hyperlinks in the nav section */
            a
            {
                font-size:16px;
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

        </style>

    </head>

    <body>

        <img src="img/failed_to_login.png" alt="failed to login">

    </body>

</html>
