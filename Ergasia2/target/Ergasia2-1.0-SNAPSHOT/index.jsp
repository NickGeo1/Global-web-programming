<!-- This is the start page of the application -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <title>Online doctor appointments</title>

        <style>

            img
            {
                width:25px;
                height:25px;
            }

            *
            {/*Όλα το περιεχόμενο της ιστοσελίδας έχει την ίδια γραμματοσειρά.*/
                font-family:candara;
            }

            body
            {/*Μορφοποίηση του υπόβαθρου της ιστοσελίδας.*/
                background-color: seagreen;
            }

            article
            {/*Κλάση μορφοποίησης για το article.*/
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
                margin-top:30px;
            }

            a:active
            {/*Σύνδεσμοι που όταν τους πατάει ο χρήστης έχουν μπλε επισύναψη.*/
                background-color:#012A6C;
            }

            a:visited
            {/*Σύνδεσμοι που όταν τους επισκέπτεται ο χρήστης έχουν κόκκινο χρώμα.*/
                color:#C20034;
            }

            a
            {/*Όλοι οι σύνδεσμοι.*/
                font-size:17px;
            }

            section
            {/*Κανόνες μορφοποίησης για το section.*/
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
                margin-top:30px;
            }

            h1
            {/*Κανόνες μορφοποίησης για όλα τα h1.*/
                font-size:25px;
                text-align:center;
                color: ;
                letter-spacing:6px;
            }

            td
            {
                width:210px;
                height:20px;
                text-align:center;
                color:white;
            }

            header
            {/*Κανόνες μορφοποίησης για την επικεφαλίδα του body.*/
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
                margin-top:30px;
            }

            h3
            {/*Κοινή μορφοποίηση για όλα τα h3.*/
                font-size:19px;
                text-decoration:bold;
                color:#C20034;
            }

        </style>

    </head>

    <br>

    <body id="head">

        <header>
            <h1>Welcome to the online doctor appointments!</h1>
        </header>

        <br>

        <section>
            <center>
                <br>
                <h3>In this application, you will have the chance to create or handle your appointments. Εδω θα έχει κείμενο και καλά</h3>
                <br>
                <h3>-- Please, login or sign-up to the application --</h3>
                <br>
            </center>
        </section>

        <br>

        <article>
            <table align="center">
                <tr>
                    <td><h3><a href="login.jsp">Login</a></h3></td>
                    <td><h3><a href="sign-up.jsp">Sign-up</a></h3></td>
                </tr>
            </table>
        </article>

        <br>

    </body>

</html>
