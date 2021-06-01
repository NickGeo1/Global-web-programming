<!-- This is the sign - up page -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <meta charset="utf-8">
        <title>Sign-up page</title>

    </head>

    <body>

    <br>
    <br>

    <article class="article1">

        <br>
        <br>

        <form action="" method="post" name="signup">
            <center><table>
                <tr><td colspan="2" id="firstcell" height="25px" class="td7"><pre> Sign-up form</pre></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="username" class="td7">Username: *:</label></td><td class="td7"><input type="text" id="username" name="username" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><label for="password">Password: *:</label></td><td class="td7"><input type="password" id="password" name="password" class="input1"/></td></tr>
                <tr><td></td><td></td></tr>
                <tr><td class="td7"><input type="radio" id="Admin" name="category" value="Admin"><label for="Admin">Admin</label></td><td class="td7"><input type="radio" id="Patient" name="category" value="Patient"><label for="Patient">Patient</label></td></tr>
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