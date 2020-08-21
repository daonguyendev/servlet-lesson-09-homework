package com.daonguyen;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@WebServlet(name = "userServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.print("<form action='" + req.getContextPath() +"/user' method='POST'>");
        writer.print("<label>Full Name:</label></br>");
        writer.print("<input type='text' placeholder='Please enter your full name' name='fullname'></input></br></br>");

        writer.print("<label>Date Of Birth:</label></br>");
        writer.print("<input type='datetime-local' placeholder='Please enter your birthday time' name='birthdaytime'></input></br></br>");

        writer.print("<label>Gender:</label></br>");
        writer.print("<input type='radio' name='gender' value='male'></input>");
        writer.print("<label for='male'>Male</label></br>");
        writer.print("<input type='radio' name='gender' value='female'></input>");
        writer.print("<label for='female'>Female</label></br>");
        writer.print("<input type='radio' name='gender' value='other'></input>");
        writer.print("<label for='other'>Other</label></br></br>");

        writer.print("<button>Submit</button>");
        writer.print("</form>");
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Step 1: Get user info from user's request
        String fullName = req.getParameter("fullname");
        String birthdayTime = req.getParameter("birthdaytime");
        String gender = req.getParameter("gender");

        // Step 2: Calculate user's age
        Date birthDate;
        int userAge = 0;
        if (!birthdayTime.isEmpty()) {
            birthDate = Timestamp.valueOf(LocalDateTime.parse(birthdayTime));
            userAge = calculateAgeFromBirthdayTime(birthDate, new Date());
        }

        // Step 3: Pass user info from form to userDetailServlet to display
        ServletContext userContext = getServletContext();
        userContext.setAttribute("fullName", fullName);
        userContext.setAttribute("userAge", String.valueOf(userAge));
        userContext.setAttribute("gender", gender);

        // Step 4: Go to userDetailServlet using send redirect
        resp.sendRedirect(req.getContextPath() + "/userDetail");
    }

    private int calculateAgeFromBirthdayTime(Date birthDate, Date currentDate) {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(birthDate));
        int d2 = Integer.parseInt(formatter.format(currentDate));
        int age = (d2 - d1) / 10000;
        return age;
    }
}
