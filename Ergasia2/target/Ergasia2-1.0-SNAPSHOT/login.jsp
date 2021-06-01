<!-- This is the login page -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Login page</title>
    <style>

        .td7{/*Μορφοποίηση των κελιών του πίνακα της φόρμας.*/
            text-align:left;
            color:#012A6C;
            font-size:19px;
            height:35px;
            width:150px;
        }

        .input1{/*Μορφοποίηση όλων των πεδίων της φόρμας.*/
            width:200px;
            border:1px solid #012A6C;
            padding: 12px 20px;
        }

        input:invalid{/*Αν ένα πεδίο έχει λάθος τιμή,τότε αλλάζει το χρώμα υποβάθρου του.*/
            background-color:#C20034;
        }

    </style>
</head>
<body>
<br>
<br>
<article class="article1">
    <br><br>
    <form action="login" method="post" name="me">
        <center><table>
            <tr><td colspan="2" id="firstcell" height="25px" class="td7">Φόρμα επικοινωνίας</td></tr>
            <tr><td></td><td></td></tr>
            <tr><td class="td7"><label for="username" class="td7">Username *:</label></td><td class="td7"><input type="text" id="username" name="username" class="input1"/></td></tr>
            <tr><td></td><td></td></tr>
            <tr><td class="td7"><label for="password">Password *:</label></td><td class="td7"><input type="password" id="password" name="password" class="input1"/></td></tr>
            <tr><td></td><td></td></tr>
            <tr><td colspan="2"><center><pre><input type="submit" name="send" value="Login" id="button" onmouseover="validname()" onclick="validateform()">           <input type="Reset" name="reset" value="RESET" id="buttons"/></pre></center></td></tr>
            <input type="hidden" name="con1" id="con1" value="0"><input type="hidden" name="con2" id="con2" value="0"><!--Κρυμμένα πεδία μη ορατά από το χρήστη.-->
        </table></center>
    </form>
    <br><br>
</article>
<footer>
</footer>
<script>

    function validateform(){//Συνάρτηση που ελέγχει την συμπλήρωση της φόρμας κατά το πάτημα του κουμπιού αποστολή. Έναρξη συνάρτησης validateform().
        var x=document.forms["me"]//Oρισμός των μεταβλητών που έχουν τιμές τα στοιχεία που εισήγαγε ο χρήστης.
            ["username"].value;
        var y=document.forms["me"]
            ["password"].value;

        if( x=="" || y==""){//Αν τα υποχεωτικά πεδία είναι κενά
            alert("You have to complete all the fields of the form");//Εμφανίζει μήνυμα λάθους.
        }
    }

    function validname(){//Συνάρτηση που ελέγχει την ορθή συμπλήρωση των πεδίων της φόρμας.

        var username = document.forms["me"]["username"].value;
        var password = document.forms["me"]["password"].value;

        if (/^[a-zA-Z0-9\s]+$/.test(username))//Έλεγχος αν το Ονοματεπώνυμο δέχεται ελληνικούς και λατινικούς χαρακτήρες.
        {
            document.getElementById("con1").value="1";//Δίνει την τιμή 1 σε ένα κρυφό πεδίο της φόρμας.
        }
        else
        {
            alert("Username consists of letters and/or numbers!");//Εμφάνιση σχετικού μηνύματος.
            document.getElementById("con1").value="0";//Δίνει την τιμή 0 σε ένα κρυφό πεδίο της φόρμας.
        }

        if (/^[a-zA-Z0-9\s]+$/.test(password))//Έλεγχος αν το Ονοματεπώνυμο δέχεται ελληνικούς και λατινικούς χαρακτήρες.
        {
            document.getElementById("con2").value="1";//Δίνει την τιμή 1 σε ένα κρυφό πεδίο της φόρμας.
        }
        else
        {
            alert("Password consists of letters and/or numbers!");//Εμφάνιση σχετικού μηνύματος.
            document.getElementById("con1").value="0";//Δίνει την τιμή 0 σε ένα κρυφό πεδίο της φόρμας.
        }
    }//Τέλος συνάρτησης.

</script>
</body>
</html>
