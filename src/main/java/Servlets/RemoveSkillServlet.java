package Servlets;

import Exceptions.SkillException;
import Joboonja.SkillManager;
import Joboonja.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/removeskill")
public class RemoveSkillServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skillName = request.getParameter("skillName");
        try {
            SkillManager.removeSkillFromUser(skillName, UserManager.getCurrentUser());
            response.sendRedirect("/user/" + UserManager.getCurrentUser().getId());
        } catch (SkillException e) {
            response.setStatus(404);
            request.setAttribute("message",
                    e.getMessage());
            request.getRequestDispatcher("/404.jsp").forward(request, response);
        }
    }
}
