<!-- This page is used in order an administrator to delete a doctor -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>
        <meta charset="utf-8">
        <title>Doctor appointments: delete doctor</title>
        <link rel="stylesheet" href="CSS/styles.css">
    </head>

    <body>

        <form method="post" action="admin">

            <div class="imgcontainer">
                <img src="img/logo1.png" alt="logo_image" class="avatar">
            </div>

            <div class="container">
                <label><b style="color:#012A6C"> Delete a doctor:</b></label>
            </div>

            <br>

            <button type="submit" name="admin_action" id="admin_action" value="delete_doctor">Go back</button>

        </form>

    </body>

</html>
