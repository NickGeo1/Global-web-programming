<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html lang="en">

    <head>

        <title>INSTRUCTIONS</title>
        <meta charset="utf-8">

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
            article
            {
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
                margin-top:30px;
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

            /* style rules for every h1 */
            h1
            {
                font-size:25px;
                text-align:center;
                color: #012A6C;
                letter-spacing:6px;
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

            /* style rules for the footer section of the website */
            footer
            {
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
                margin-top:30px;
            }

            /* style rules for every paragraph in the footer section */
            #footer
            {
                font-size: 19px;
                text-align:center;
                color: #012A6C;
            }

        </style>

    </head>

    <body>

        <header>
            <h1>How to use this application...</h1>
        </header>

        <article>

            <br>

            <p>
                This web application allows you to manage your doctor appointments. This platform supports three (3) kinds of users with separate righs each: Administrators, Doctors and Patietns.
            </p>

            <br>

        </article>

        <footer>
            <center>
                <p id="footer">Go back to the start page. Click <a href="index.jsp">here</a></p>
            </center>
        </footer>

    </body>

</html>
