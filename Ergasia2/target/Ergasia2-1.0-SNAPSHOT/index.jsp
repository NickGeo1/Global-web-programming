<!DOCTYPE html>
<html>
<head> <!-- head section of the page -->
    <meta charset="utf-8">
    <title>Doctor appointments - Login Window</title>
    <style>

        /* this web page has only 'candara' font */
        *{
            font-family:candara;
        }

        td{
            width:210px;
            height:20px;
            text-align:center;
            color:white;
        }

        /* set a background for this web page */
        body{
            background-color: mediumaquamarine;
        }

        /* style rules for the login form */
        .td7{
            text-align:left;
            color:#012A6C;
            font-size:19px;
            height:35px;
            width:150px;
        }

        /* style rules for the login form */
        .input1{
            width:200px;
            border:1px solid #012a6c;
            padding: 12px 20px;
        }

        /* style rule of the first row of the login form */
        #firstcell{
            background-color: #012A6C;
            font-size:20px;
            text-align:left;
            color:white;
            font-weight:bold;
        }

        /* style rules for the 'article' section of the web page */
        .article1{
            border-radius: 25px;
            background: white;
            margin-right:400px;
            margin-left:400px;
            margin-top:30px;
        }

        /* style rule of the 'submit button' of the login form */
        #buttons{
            font-size:17px;
            font-weight:bold;
            text-align:center;
            color:white;
            background-color:#012A6C;
        }

        /* style rule for the 'reset' button of the login form */
        #button{
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
    <br><br>
    <form action="" method="post" name="login-form"> <!-- login form -->
        <center><table>
            <tr><td colspan="2" id="firstcell" height="25px" class="td7">  Login Form</td></tr>
            <tr><td></td><td></td></tr>
            <tr><td class="td7"><label for="username" class="td7">Username *:</label></td><td class="td7"><input type="text" id="username" name="username" class="input1"/></td></tr>
            <tr><td></td><td></td></tr>
            <tr><td class="td7"><label for="password" class="td7">Password *:</label></td><td class="td7"><input type="password" id="password" name="password" class="input1"/></td></tr>
            <tr><td></td><td></td></tr>
            <tr><td colspan="2"><center><pre><input type="submit" name="send" value="login" id="buttons" onclick="validateform()">           <input type="Reset" name="reset" value="reset form" id="button"/></pre></center></td></tr>
        </table></center>
    </form>
    <br><br>
</article>
<br>
</body>
<script>

    // This is a function that checks if user has already filled the fields of the login form.
    function validateform()
    {
        // take 'username' and 'password' values that user inserted in the login form
        var uname=document.forms["login-form"]["username"].value;
        var pass=document.forms["login-form"]["password"].value;

        if( uname=="" || pass==""){ // if the fields of the form are empty, then show a message to the user

            alert("Please, complete all the fields of the form!");

        }
    }

</script>
</html>