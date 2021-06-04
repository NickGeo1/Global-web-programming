<!-- This is the start page of the web application -->
<!-- source for the html and css code: https://www.geeksforgeeks.org/html-responsive-modal-login-form/ -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html lang="en">

    <head>

        <meta charset="utf-8">
        <title>Doctor appointments: Register</title>

        <style>

            /*set border to the form*/
            form
            {
                border: 3px solid whitesmoke;
                background-color: white ;
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
                margin: 0;
                position: absolute;
                top: 50%;
                left: 50%;
                -ms-transform: translate(-50%, -50%);
                transform: translate(-50%, -50%);
            }

            /*assign full width inputs*/
            input[type=text],
            input[type=password]
            {
                width: 100%;
                padding: 12px 20px;
                margin: 8px 0;
                display: inline-block;
                box-sizing: border-box;
                border:1px solid mediumseagreen;
            }

            /*set a style for the buttons*/
            button
            {
                background-color: mediumseagreen;
                color: white;
                padding: 10px 18px;
                margin: 8px 0;
                border: none;
                cursor: pointer;
                width: auto;
            }

            /* set a hover effect for the button*/
            button:hover
            {
                opacity: 0.8;
            }

            /*set extra style for the cancel button*/
            .cancelbtn
            {
                width: auto;
                padding: 10px 18px;
                background-color: #f44336;
            }

            /*centre the display image inside the container*/
            .imgcontainer
            {
                text-align: center;
                margin: 24px 0 12px 0;
            }

            /*set image properties*/
            img.avatar
            {
                width: 16%;
                border-radius: 70%;
            }

            /*set padding to the container*/
            .container
            {
                padding: 16px;
            }

            /*set the forgot password text*/
            span.psw
            {
                float: right;
                padding-top: 16px;
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
                font-size:16px;
                color: #012A6C;
            }

            /* style rules for the menu at the bottom of the page */
            .navbar
            {
                font-size: 14px;
                bottom: 0;
                text-align: center;
                background-color: #f1f1f1;
                width: 100%;
                height:18px;
            }

        </style>

    </head>

    <body>

        <form action="patient" method="post" name="signup">

            <input type="hidden", value="5", name="patient_action">

            <div class="imgcontainer">
                <img src="img/logo1.png" alt="logo_image" class="avatar">
            </div>

            <div class="container">
                <label><b style="color:#012A6C">Hello patient, please create your account!</b></label>
            </div>

            <div class="container">

                <label><b style="color:#012A6C">First name: *</b></label>
                <input type="text" placeholder="Enter your first name" name="fn" required>

                <label><b style="color:#012A6C">Last name: *</b></label>
                <input type="text" placeholder="Enter your last name" name="ln" required>

                <label><b style="color:#012A6C">Username: *</b></label>
                <input type="text" placeholder="Enter username" name="username" required>

                <label><b style="color:#012A6C">Password: *</b></label>
                <input type="password" placeholder="Enter password" name="password" required>

                <label><b style="color:#012A6C">Age: *</b></label>
                <input type="text" placeholder="Enter your age" name="age" required>

                <label><b style="color:#012A6C">AMKA: *</b></label>
                <input type="text" placeholder="Enter your AMKA number" name="AMKA" required>

            </div>

            <div class="container" style="background-color:#f1f1f1">
                <button type="submit">Register</button>
                <button type="reset" class="cancelbtn">Reset</button>
                <span class="psw">Already have an account? <a href="login.jsp">login</a></span>
            </div>

        </form>

        <br>

        <div class="navbar">
            <p>Do you need help? Check the <a href="instructions.jsp" class="active" style="font-size: 14px;">instuctions</a></p>
        </div>

    </body>

</html>