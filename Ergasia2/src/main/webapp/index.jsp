<!-- This is the start page of the application -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <title>Online doctor appointments</title>

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
            article
            {
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
                margin-top:30px;
            }

            /* style rules for all the hyperlinks */
            a
            {
                font-size:20px;
            }

            /* style rules when hyperlinks are active */
            a:active
            {
                background-color: chocolate;
            }

            /* style rules when hyperlinks are pressed */
            a:visited
            {
                color: #012A6C;
            }

            a{/*Όλοι οι σύνδεσμοι.*/
                font-size:17px;
            }

            /* style rules for every h1 */
            h1
            {
                font-size:25px;
                text-align:center;
                color: #012A6C;
                letter-spacing:6px;
            }

            /* style rules for every field of the table */
            td
            {
                width:210px;
                height:20px;
                text-align:center;
                color:white;
            }

            /* style rules for the menu section of the web page */
            nav
            {
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
            }

            /* style rules for the header part of the page */
            header
            {
                border-radius: 25px;
                background: white;
                margin-right:100px;
                margin-left:100px;
                margin-top:30px;
            }

        </style>

    </head>

    <br>

    <body id="head">

        <header>
            <h1>Welcome to the online doctor appointments!</h1>
        </header>

        <br>

        <nav>

            <table align="center">
                <tr>
                    <td><a href="sign-up.jsp">sign-up</a></td>
                    <td><a href="login.jsp">login</a></td>
                    <td><a href="instructions.jsp">Instruction use</a></td>
                </tr>
            </table>

        </nav>

        <article>
                <h1>EDO THA BEI MIA POUTANA IKONA POU DEN XERO POS MPENI SE JSP ARXIA.</h1>
        </article>

        <br>
        <br>

    </body>

</html>
