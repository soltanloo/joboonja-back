package Servlets;

import Exceptions.UserNotFoundException;
import Joboonja.SkillManager;
import Joboonja.UserManager;
import Models.Skill;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getPathInfo().substring(1);
        HashMap<String, Boolean> skillsEndorsed = new HashMap<>();
        boolean isCurrentUser = false;
        try {
            isCurrentUser = UserManager.getCurrentUser().equals(UserManager.getUserByID(userId));
            request.setAttribute("user", UserManager.getUserByID(userId));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        try {
            for (Skill s:
                    UserManager.getUserByID(userId).getSkills()) {
                skillsEndorsed.put(s.getName(), s.getEndorsers().contains(UserManager.getCurrentUser().getId()));
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        request.setAttribute("skillsEndorsed", skillsEndorsed);
        request.setAttribute("myId", UserManager.getCurrentUser().getId());
        request.setAttribute("addableSkills", SkillManager.getAddableSkillsOfUser(UserManager.getCurrentUser()));
        request.setAttribute("isCurrentUser", isCurrentUser);
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }
}


