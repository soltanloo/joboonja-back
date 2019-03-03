package Servlets;

import Exceptions.UserException;
import Joboonja.SkillManager;
import Joboonja.UserManager;
import Models.Skill;
import Models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    private User currentUser = UserManager.getCurrentUser();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo().equals("/")) {
            response.sendRedirect("/user");
        } else {
            String userId = request.getPathInfo().substring(1);
            HashMap<String, Boolean> skillsEndorsed = new HashMap<>();
            try {
                User requestedUser = UserManager.getUserByID(userId);
                boolean isCurrentUser;
                isCurrentUser = currentUser.equals(requestedUser);
                for (Skill s:
                        requestedUser.getSkills()) {
                    skillsEndorsed.put(s.getName(), s.getEndorsers().contains(currentUser.getId()));
                }
                request.setAttribute("user", requestedUser);
                request.setAttribute("skillsEndorsed", skillsEndorsed);
                request.setAttribute("myId", currentUser.getId());
                request.setAttribute("addableSkills", SkillManager.getAddableSkillsOfUser(currentUser));
                request.setAttribute("isCurrentUser", isCurrentUser);
                request.getRequestDispatcher("/user.jsp").forward(request, response);
            } catch (UserException e) {
                response.setStatus(404);
                request.setAttribute("message",
                        e.getMessage());
                request.getRequestDispatcher("/404.jsp").forward(request, response);
            }
        }
    }
}


