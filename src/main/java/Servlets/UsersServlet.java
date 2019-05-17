package Servlets;

import DTOs.MessageWithStatusCode;
import DTOs.UserWithMeta;
import Exceptions.SkillException;
import Exceptions.UserException;
import Joboonja.ProjectManager;
import Joboonja.SkillManager;
import Joboonja.UserManager;
import Models.Project;
import Models.Skill;
import Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (request.getParameter("id") == null) {
            ArrayList<User> users;
            if (request.getParameter("query") == null) {
                users = UserManager.getUsers();
            } else {
                users = UserManager.getUsersByQuery(request.getParameter("query"));
            }
            String usersJson = mapper.writeValueAsString(users);
            out.println(usersJson);
        } else {
            Integer userId = Integer.parseInt(request.getParameter("id"));
            HashMap<String, Boolean> skillsEndorsed = new HashMap<>();
            try {
                UserWithMeta userWithMeta = new UserWithMeta();
                User requestedUser = UserManager.getUserByID(userId);
                boolean isCurrentUser;
                isCurrentUser = UserManager.getCurrentUser().getId().equals(requestedUser.getId());
                for (Skill s:
                        requestedUser.getSkills()) {
                    skillsEndorsed.put(s.getName(), s.getEndorsers().contains(UserManager.getCurrentUser().getId()));
                }
                userWithMeta.setUser(requestedUser);
                userWithMeta.setSkillsEndorsed(skillsEndorsed);
                userWithMeta.setAddableSkills(SkillManager.getAddableSkillsOfUser(UserManager.getCurrentUser().getId()));
                userWithMeta.setIsCurrentUser(isCurrentUser);
                userWithMeta.setMyId(UserManager.getCurrentUser().getId());
                String userJson = mapper.writeValueAsString(userWithMeta);
                out.println(userJson);
            } catch (UserException e) {
                response.setStatus(404);
                MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
                messageWithStatusCode.setStatusCode(404);
                messageWithStatusCode.setMessage(e.getMessage());
                String message = mapper.writeValueAsString(messageWithStatusCode);
                out.println(message);
            }
        }
    }
}
