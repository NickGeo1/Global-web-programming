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
                width: 16%;
                border-radius: 70%;
            }

            /* set padding to the container */
            .container
            {
                padding: 16px;
            }

            /* set styles for span and cancel button on small screens */
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
                font-size:16px;
                color: #012A6C;
            }

            /* style rules for the menu at the bottom of the page */
            .navbar
            {
                font-size: 18px;
                bottom: 0;
                text-align: center;
                background-color: #f1f1f1;
                width: 100%;
                height:20px;
            }

        </style>

    </head>

    <body>

        <br>
        <br>

        <center>
            <div class="imgcontainer">
                <img src="img/logo1.png" alt="logo_image" class="avatar">
            </div>
        </center>

        <br>

        <article>
            <div class="container">
                <label><h3 style="color:#012A6C">Hello user, view how to use this website:</h3></label>
            </div>

            <div class="container">



            </div>

            <div class="navbar">
                <p>Go back to the <a href="index.jsp" class="active" style="font-size: 18px;">start</a> page</p>
            </div>

        </article>

    </body>
</html>
