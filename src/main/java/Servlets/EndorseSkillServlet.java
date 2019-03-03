package Servlets;

import Exceptions.SkillException;
import Exceptions.UserException;
import Joboonja.SkillManager;
import Joboonja.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/endorseskill")
public class EndorseSkillServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skillName = request.getParameter("skillName");
        String endorseeId = request.getParameter("endorseeId");
        try {
            SkillManager.endorseSkillOfUser(skillName, UserManager.getCurrentUser().getId(),
                    UserManager.getUserByID(endorseeId));
            response.sendRedirect("/user/" + endorseeId);
        } catch (UserException | SkillException e) {
            response.setStatus(404);
            request.setAttribute("message",
                    e.getMessage());
            request.getRequestDispatcher("/404.jsp").forward(request, response);
        }
    }
}
