package Servlets;

import Exceptions.UserException;
import Joboonja.SkillManager;
import Joboonja.UserManager;
import Models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/addskill")
public class AddSkillServlet extends HttpServlet {

    private User currentUser = UserManager.getCurrentUser();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skillName = request.getParameter("skillName");
        try {
            SkillManager.addSkillToUser(skillName, currentUser);
            response.sendRedirect("/user/" + currentUser.getId());
        } catch (UserException e) {
            response.setStatus(404);
            request.setAttribute("message",
                    e.getMessage());
            request.getRequestDispatcher("/404.jsp").forward(request, response);
        }
    }
}
