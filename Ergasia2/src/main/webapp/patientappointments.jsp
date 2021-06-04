<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html lang="en">

    <head>

        <meta charset="UTF-8">
        <title>Title</title>

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

            /* style rules for every h2 */
            h2
            {
                text-align:center;
                color: #012A6C;
                letter-spacing:6px;
            }

            /* style rules for the article part of header section */
            header
            {
                border-radius: 25px;
                background: white;
                width: 500px;
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

            /* style rules for every field of the table (inside the login form) */
            .td7
            {
                text-align:left;
                color:#012A6C;
                font-size:19px;
                height:25px;
                width:100px;
            }

            /* style rules for every input field of the form */
            .input1
            {
                width:200px;
                border:1px solid #012A6C;
                padding: 12px 20px;
            }

            /* style rules for every field of the table (inside the login form) */
            td
            {
                width:100px;
                height:20px;
                text-align:center;
                color:white;
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

        <header>
            <h2>Show patient's appointments</h2>
        </header>

        <br>
        <br>

        <article class="article1">
            <br>
            <form action="patient" method="post" name="form1">
                <center>
                    <table>
                        <tr>
                            <td colspan="2" id="firstcell" height="25px" class="td7"><pre> Insert the name of the patient:</pre></td>
                        </tr>

                        <tr><td></td><td></td></tr>

                        <tr>
                            <td class="td7"><label for="patient" class="td7">Name: </label></td>
                            <td class="td7"><input type="text" id="patient" name="fn" class="input1" required/></td>
                        </tr>

                        <tr><td></td><td></td></tr>

                        <tr><td colspan="2" style="text-align: right"><input type="submit" name="send" value="SHOW" id="buttons"></td></tr>
                    </table>
                </center>
            </form>

            <br>

        </article>

        <br>
        <br>

        <footer>
            <center>
                <p>Go back to the start page. Click <a href="index.jsp">here</a></p>
            </center>
        </footer>

    </body>

</html>
