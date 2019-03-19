package Servlets;

import DTOs.MessageWithStatusCode;
import Exceptions.SkillException;
import Exceptions.UserException;
import Joboonja.SkillManager;
import Joboonja.UserManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/removeskill")
public class RemoveSkillServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
        String userId = request.getParameter("id");
        String skillName = request.getParameter("skillName");
        try {
            SkillManager.removeSkillFromUser(skillName, UserManager.getUserByID(userId));
            messageWithStatusCode.setStatusCode(200);
            messageWithStatusCode.setMessage("Skill is removed successfully.");
        } catch (SkillException | UserException e) {
            response.setStatus(404);
            messageWithStatusCode.setStatusCode(404);
            messageWithStatusCode.setMessage(e.getMessage());
        }
        String message = mapper.writeValueAsString(messageWithStatusCode);
        out.println(message);
    }
}
