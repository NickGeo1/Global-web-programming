<!-- This is the sign-up page. Using this page, patients are now able to sign-up to the application. -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <meta charset="utf-8">
        <title>SIGN-UP</title>

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

            /* style rules for the article part of body section */
            .article1
            {
                border-radius: 25px;
                background: white;
                width: 500px;
            }

            /* style rules for every field of the table (inside the login form) */
            td
            {
                width:200px;
                height:20px;
                text-align:center;
                color:white;
            }

            /* style rules for every field of the table (inside the login form) */
            .td7
            {
                text-align:left;
                color:#012A6C;
                font-size:19px;
                height:35px;
                width:150px;
            }

            /* style rules for every input field of the form */
            .input1
            {
                width:150px;
                border:1px solid #012A6C;
                padding: 12px 20px;
            }

            /* style rules of the first row of the table (inside the login form) */
            #firstcell
            {
                background-color:#012A6C;
                font-size:20px;
                text-align:left;
                color:white;
                font-weight:bold;
            }

            /* style the two buttons of the form */
            #buttons
            {
                font-size:17px;
                font-weight:bold;
                text-align:center;
                color:white;
                background-color:#012A6C;
            }

            /* style rules for the footer section of the website */
            footer
            {
                border-radius: 25px;
                background: white;
                width: 500px;
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

            /* style rules for every paragraph */
            p
            {
                font-size: 19px;
                text-align:center;
                color: #012A6C;
            }

        </style>

    </head>

    <body>

    <br>
    <br>

    <article class="article1">

        <br>
        <br>

        <form action="patient" method="post" name="signup" onsubmit="return validateForm()">
            <center><table>
                <tr><td colspan="2" id="firstcell" height="25px" class="td7"><pre> Hello patient, please sign-up:</pre></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="username" class="td7">Username: *</label></td><td class="td7"><input type="text" id="username" name="username" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="password">Password: *</label></td><td class="td7"><input type="password" id="password" name="password" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="fn" class="td7">First name: *</label></td><td class="td7"><input type="text" id="fn" name="fn" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="ln">Last name: *</label></td><td class="td7"><input type="text" id="ln" name="ln" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="AMKA">AMKA: *</label></td><td class="td7"><input type="text" id="AMKA" name="AMKA" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="age">Age: *</label></td><td class="td7"><input type="text" id="age" name="age" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td colspan="2" class="td7">Fields with * are necessary.</td></tr>
                <tr><td></td><td></td></tr>
                <tr><td colspan="2"><center><pre><input type="submit" name="send" value="SUBMIT" id="buttons" onmouseover="validname()">           <input type="Reset" name="rEset" value="RESET" id="buttons"/></pre></center></td></tr>
            </table></center>
        </form>

        <br>
        <br>

    </article>

    <br>
    <br>

    <footer>
        <center>
            <p>Go back to the start page. Click <a href="index.jsp">here</a></p>
        </center>
    </footer>

    <script>

        // this function checks if all the fields of the form have been completed by the user
        function validateform()
        {
            // take the values that user inserted to the sign up form

            var username = document.forms["signup"]["username"].value;
            var password = document.forms["signup"]["password"].value;
            var fn = document.forms["signup"]["fn"].value; // fn = first name
            var ln = document.forms["signup"]["ln"].value; // ln = last name
            var AMKA = document.forms["signup"]["AMKA"].value;
            var age = document.forms["signup"]["age"].value;

            // if any field is empty, then show a message to user
            if( username == "" || password == "" || fn == "" || ln == "" || AMKA == "" | age == "")
            {

                alert("All fields of the form are necessary!");
                return false;
            }
            else
            {
                return true;
            }

        }

        // this function checks if the form has been correctly completed by the user
        function validname()
        {
            // take the values that user inserted to the sign up form

            var username = document.forms["signup"]["username"].value;
            var password = document.forms["signup"]["password"].value;
            var fn = document.forms["signup"]["fn"].value; // fn = first name
            var ln = document.forms["signup"]["ln"].value; // ln = last name
            var AMKA = document.forms["signup"]["AMKA"].value;
            var age = document.forms["signup"]["age"].value;


            if (!/^.$/.test(username))
            {
                alert("Username should include at least one character.")
            }

            if (!/^....*$/.test(password))
            {
                alert("Password should consist of only six characters.")
            }

            if (!/^A-z][a-z]*$/.test(fn))
            {
                alert("First name includes only letters and should begin with a capital letter.")
            }

            if (!/^[A-Z][a-z]*$/.test(ln))
            {
                alert("Last name includes only letters and should begin with a capital letter.")
            }

            if (!/^[0-9]+$/.test(age))
            {
                alert("Age field should consist of only numbers.")
            }

            if (!/^\d{11}$/.test(AMKA))
            {
                alert("AMKA should be a 11-digit number.")
            }

        }

    </script>

    </body>

</html>