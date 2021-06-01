<!-- This is the login page -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <meta charset="utf-8">
        <title>Login page</title>

        <style>

            *
            {/*Όλα το περιεχόμενο της ιστοσελίδας έχει την ίδια γραμματοσειρά.*/
                font-family:candara;
            }

            body
            {/*Μορφοποίηση του υπόβαθρου της ιστοσελίδας.*/
                background-color: seagreen;
            }

            .article1
            {/*Κλάση μορφοποίησης για το article.*/
                border-radius: 25px;
                background: white;
                margin-right:400px;
                margin-left:400px;
                margin-top:30px;
            }

            td
            {
                width:210px;
                height:20px;
                text-align:center;
                color:white;
            }

            .td7
            {/*Μορφοποίηση των κελιών του πίνακα της φόρμας.*/
                text-align:left;
                color:#012A6C;
                font-size:19px;
                height:35px;
                width:150px;
            }

            .input1
            {/*Μορφοποίηση όλων των πεδίων της φόρμας.*/
                width:200px;
                border:1px solid #012A6C;
                padding: 12px 20px;
            }

            #firstcell
            {/*Μορφοποίηση της πρώτης γραμμής του πίνακα στην form1*/
                background-color:#012A6C;
                font-size:20px;
                text-align:left;
                color:white;
                font-weight:bold;
            }

            #buttons
            {/*Τα κουμπιά της φόρμας στην σελίδα communication.html.*/
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

        <form action="login" method="post" name="me">
            <center><table>
                <tr><td colspan="2" id="firstcell" height="25px" class="td7"><pre> Login form</pre></td></tr>
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
