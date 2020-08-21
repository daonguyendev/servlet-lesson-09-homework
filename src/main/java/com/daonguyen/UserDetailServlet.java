package com.daonguyen;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "userDetailServlet", urlPatterns = {"/userDetail"})
public class UserDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        ServletContext userContext = getServletContext();
        String fullName = (String) userContext.getAttribute("fullName");
        String userAge = (String) userContext.getAttribute("userAge");
        String gender = (String) userContext.getAttribute("gender");

        PrintWriter writer = resp.getWriter();
        writer.print("<h3>Welcome to user detail page</h3>");
        writer.print("Full Name: " + fullName + "</br>");
        writer.print("Age: " + userAge + "</br>");
        writer.print("Gender: " + gender + "</br></br>");
        writer.print("<a href='"+ req.getContextPath() +"/user'>Back</a>");
        writer.close();
    }
}
