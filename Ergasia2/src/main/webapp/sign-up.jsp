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

        <form action="" method="post" name="signup" onsubmit="return validateForm()">
            <center><table>
                <tr><td colspan="2" id="firstcell" height="25px" class="td7"><pre> Hello patient, please sign-up:</pre></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="username" class="td7">Username: *:</label></td><td class="td7"><input type="text" id="username" name="username" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="password">Password: *:</label></td><td class="td7"><input type="password" id="password" name="password" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="fn" class="td7">First name: *:</label></td><td class="td7"><input type="text" id="fn" name="fn" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="ln">Last name: *:</label></td><td class="td7"><input type="text" id="ln" name="ln" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="AMKA">AMKA: *:</label></td><td class="td7"><input type="text" id="AMKA" name="AMKA" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="age">Age: *:</label></td><td class="td7"><input type="text" id="age" name="age" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td colspan="2" class="td7">Fields with * are necessary.</td></tr>
                <tr><td></td><td></td></tr>
                <tr><td colspan="2"><center><pre><input type="submit" name="send" value="SUBMIT" id="buttons" onmouseover="validname()">           <input type="Reset" name="rEset" value="RESET" id="buttons"/></pre></center></td></tr>
                <input type="hidden" name="con1" id="con1" value="0"><input type="hidden" name="con2" id="con2" value="0"><!--Κρυμμένα πεδία μη ορατά από το χρήστη.-->
            </table></center>
        </form>

        <br>
        <br>

    </article>

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

            if (/^[a-zA-Zα-ωΑ-Ωίόάήώύϊϋΐέΰ\s]+$/.test(name))
            {
                document.getElementById("con1").value="1";//Δίνει την τιμή 1 σε ένα κρυφό πεδίο της φόρμας.
            }
            else
            {
                alert("Το όνομά σας πρέπει να περιλαμβάνει μόνο ελληνικούς ή και λατινικούς χαρακτήρες!");//Εμφάνιση σχετικού μηνύματος.
                document.getElementById("con1").value="0";//Δίνει την τιμή 0 σε ένα κρυφό πεδίο της φόρμας.
            }

            if (/^[a-z\d\._-]+@([a-z\d-]+\.)+[a-z]{2,6}$/i.test(email))
            {
                document.getElementById("con2").value="1";//Δίδει την τιμή 1 σε ένα κρυφό πεδίο της φόρμας.
            }
            else
            {
                alert("Το email σας πρέπει να είναι της μορφής xxxx@xxxx.xx !");//Εμφάνιση σχετικού μηνύματος.
                document.getElementById("con2").value="0";//Δίδει την τιμή 0 σε ένα κρυφό πεδίο της φόρμας.
            }
        }
    </script>

    </body>

</html>