package com.servlets;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "CheckCredentials", value = "/login")
public class CheckCredentials extends HttpServlet {
    private String message;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(username + ", " + password);
    }
}