package Servlets;

import Exceptions.UserNotFoundException;
import Joboonja.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST request to user id: " + request.getPathInfo().substring(1));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET request to user id: " + request.getPathInfo().substring(1));
        try {
            Database.getInstance();
            request.setAttribute("user", Database.userManager.getUserByID("1"));
            request.setAttribute("skills", Database.userManager.getUserByID("1").getSkills());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        request.setAttribute("myId", 1);
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }
}


