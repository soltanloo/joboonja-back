package Servlets;

import DTOs.MessageWithStatusCode;
import Exceptions.SkillException;
import Exceptions.UserException;
import Joboonja.SkillManager;
import Joboonja.UserManager;
import Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/addskill")
public class AddSkillServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");
        String skillName = request.getParameter("skillName");
        PrintWriter out = response.getWriter();
        MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        try {
            SkillManager.addSkillToUser(skillName, UserManager.getUserByID(userId));
            messageWithStatusCode.setStatusCode(200);
            messageWithStatusCode.setMessage("Skill is added successfully.");
        } catch (UserException e) {
            response.setStatus(404);
            messageWithStatusCode.setStatusCode(200);
            messageWithStatusCode.setMessage(e.getMessage());
        } catch (SkillException e) {
            response.setStatus(400);
            messageWithStatusCode.setStatusCode(400);
            messageWithStatusCode.setMessage(e.getMessage());
        }
        String message = mapper.writeValueAsString(messageWithStatusCode);
        out.println(message);
    }
}
