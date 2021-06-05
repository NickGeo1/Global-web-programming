<!-- In this page, user is able to watch all the instructions in order to use the application -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <meta charset="utf-8">
        <title>Doctor appointmets: Instructions</title>

        <style>

            /* style rules for the article section of the page */
            article
            {
                border: 3px solid whitesmoke;
                background-color: white ;
                margin-left: 70px;
                margin-right: 70px;
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

            /* centre the display image inside the container */
            .imgcontainer
            {
                text-align: center;
                margin: 24px 0 12px 0;
            }

            /* set image properties */
            img.avatar
            {
                width: 8%;
                border-radius: 70%;
            }

            /* set padding to the container */
            .container
            {
                padding-right:16px;
                padding-left:16px;
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
                height:20px;
                margin-left: 70px;
                margin-right: 70px;
            }

        </style>

    </head>

    <body>

        <br>
        <br>

        <article>

            <center>
                <div class="imgcontainer">
                    <img src="img/logo1.png" alt="logo_image" class="avatar">
                </div>
            </center>

            <div class="container">
                <label><h3 style="color:#012A6C">&emsp;Hello user, view how to use this website:</h3></label>
            </div>

            <div class="container">

                <p>&emsp;This application is a complete environment for booking, scheduling or managing all your
                appointments you have with a doctor. Platfom supports three (3) kinds of users: Administrators,
                Doctors and Patients. Each category of users have separate rights.</p>


            </div>

        </article>

        <br>

        <div class="navbar">
            <p>Go back to the <a href="index.jsp" class="active" style="font-size: 16px;">start</a> page</p>
        </div>

    </body>

</html>
