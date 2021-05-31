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
    <form action="" method="post" name="me">
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
            ["first_name"].value;
        var y=document.forms["me"]
            ["email"].value;
        var z=document.forms["me"]
            ["comments"].value;
        if( x=="" || y=="" || z==""){//Αν τα υποχεωτικά πεδία είναι κενά
            alert("Πρέπει να συμπληρώσετε όλα τα υποχρεωτικά πεδία.");//Εμφανίζει μήνυμα λάθους.
        }else if (x!="" && y!="" && z!=""){//Αν όλα τα υποχρεωτικά πεδία είναι συμπληρωμένα.
            alert("Είστε σίγουροι ότι θέλετε να στείλετε το παρακάτω email;");
            var myWindow=window.open("","Confirm","width=400 height=400");//Εμφανίζει σε νέο παράθυρο τις τιμές που έχει ορίσει ο χρήστης για τα πεδία της φόρμας.
            myWindow.document.write("Ονοματεπώνυμο*:"+ x+"<br><br>");
            myWindow.document.write("E-mail*:"+y+"<br><br>");
            myWindow.document.write("Σχόλιo*:"+ z+"<br><br><br><br>");
            //Ακολουθούν οι συνδέσμοι ΑΠΟΣΤΟΛΗ και ΑΚΥΡΩΣΗ που υπάρχουν στο παράθυρο αυτό.
            myWindow.document.write("<pre><a href='mailto:stratoskarkanis2@gmail.com?subject=Φόρμα%20επκοινωνίας&body=Ονοματεπώνυμο:%20"+x+"%0D%0A%0D%0AΣχόλιο:%20"+z+"'>ΑΠΟΣΤΟΛΗ</a>        <a onclick='javascript:window.close()' href='formme.html'>ΑΚΥΡΩΣΗ</a></pre>");
        }
    }//Τέλος συνάρτησης validateform().

    function validname(){//Συνάρτηση που ελέγχει την ορθή συμπλήρωση των πεδίων της φόρμας.
        var email = document.forms["me"]["email"].value;
        var name = document.forms["me"]["first_name"].value;
        if (/^[a-zA-Zα-ωΑ-Ωίόάήώύϊϋΐέΰ\s]+$/.test(name))//Έλεγχος αν το Ονοματεπώνυμο δέχεται ελληνικούς και λατινικούς χαρακτήρες.
        {
            document.getElementById("con1").value="1";//Δίνει την τιμή 1 σε ένα κρυφό πεδίο της φόρμας.
        }else{
            alert("Το όνομά σας πρέπει να περιλαμβάνει μόνο ελληνικούς ή και λατινικούς χαρακτήρες!");//Εμφάνιση σχετικού μηνύματος.
            document.getElementById("con1").value="0";//Δίνει την τιμή 0 σε ένα κρυφό πεδίο της φόρμας.
        }

        if (/^[a-z\d\._-]+@([a-z\d-]+\.)+[a-z]{2,6}$/i.test(email))
        {
            document.getElementById("con2").value="1";//Δίδει την τιμή 1 σε ένα κρυφό πεδίο της φόρμας.
        }else{
            alert("Το email σας πρέπει να είναι της μορφής xxxx@xxxx.xx !");//Εμφάνιση σχετικού μηνύματος.
            document.getElementById("con2").value="0";//Δίδει την τιμή 0 σε ένα κρυφό πεδίο της φόρμας.
        }
    }//Τέλος συνάρτησης.

</script>
</body>
</html>
