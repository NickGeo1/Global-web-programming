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

            /* change the background colour of the page */
            body
            {
                background-color: seagreen;
            }

            /* style rules for the article part of body section */
            .article1
            {
                border-radius: 25px;
                background: white;
                width: 500px;
                margin: 0;
                position: absolute;
                top: 50%;
                left: 50%;
                -ms-transform: translate(-50%, -50%);
                transform: translate(-50%, -50%);
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

        </style>

    </head>

    <body>

    <br>
    <br>

    <article class="article1">

        <br>
        <br>

        <form action="" method="post" name="signup">
            <center><table>
                <tr><td colspan="2" id="firstcell" height="25px" class="td7"><pre> Hello patient, please sign-up:</pre></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="username" class="td7">Username: *:</label></td><td class="td7"><input type="text" id="username" name="username" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="password">Password: *:</label></td><td class="td7"><input type="password" id="password" name="password" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td colspan="2" class="td7">Fields with * are necessary.</td></tr>
                <tr><td></td><td></td></tr>
                <tr><td colspan="2"><center><pre><input type="submit" name="send" value="SUBMIT" id="buttons" onmouseover="validname()" onclick="validateform()">           <input type="Reset" name="rEset" value="RESET" id="buttons"/></pre></center></td></tr>
                <input type="hidden" name="con1" id="con1" value="0"><input type="hidden" name="con2" id="con2" value="0"><!--Κρυμμένα πεδία μη ορατά από το χρήστη.-->
            </table></center>
        </form>

        <br>
        <br>

    </article>

    <script>

        function validateform()
        {
            var username = document.forms["me"]
                ["username"].value;

            var password = document.forms["me"]
                ["password"].value;

            if( username == "" || password == "")
            {
                alert("Both fields of the form are necessary!");
            }
        }

    </script>

    </body>

</html>