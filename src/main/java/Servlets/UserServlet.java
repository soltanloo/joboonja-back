package Servlets;

import Exceptions.UserNotFoundException;
import Joboonja.Database;
import Joboonja.SkillManager;
import Joboonja.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getPathInfo().substring(1);
        boolean isCurrentUser = false;
        try {
            isCurrentUser = UserManager.getCurrentUser().equals(UserManager.getUserByID(userId));
            request.setAttribute("user", UserManager.getUserByID(userId));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        request.setAttribute("addableSkills", SkillManager.getAddableSkills(UserManager.getCurrentUser()));
        request.setAttribute("isCurrentUser", isCurrentUser);
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }
}


