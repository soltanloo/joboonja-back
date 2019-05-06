package Servlets;

import DTOs.MessageWithStatusCode;
import Exceptions.SkillEndorsementException;
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

@WebServlet("/users/skills")
public class SkillsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = Integer.parseInt(request.getParameter("id"));
        String skillName = request.getParameter("skillName");
        PrintWriter out = response.getWriter();
        MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        try {
            SkillManager.addSkillToUser(skillName, UserManager.getUserByID(userId));
            messageWithStatusCode.setStatusCode(200);
            messageWithStatusCode.setMessage("UserSkill is added successfully.");
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

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer endorserId = Integer.parseInt(request.getParameter("endorserId"));
        Integer endorseeId = Integer.parseInt(request.getParameter("id"));
        int skillId = Integer.parseInt(request.getParameter("skillId"));
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
        try {
            SkillManager.endorseSkillOfUser(skillId, endorserId,
                    endorseeId);
            messageWithStatusCode.setStatusCode(200);
            messageWithStatusCode.setMessage("UserSkill is endorsed successfully.");
        } catch (SkillException e) {
            response.setStatus(404);
            messageWithStatusCode.setStatusCode(404);
            messageWithStatusCode.setMessage(e.getMessage());
        } catch (SkillEndorsementException e) {
            response.setStatus(400);
            messageWithStatusCode.setStatusCode(400);
            messageWithStatusCode.setMessage(e.getMessage());
        }
        String message = mapper.writeValueAsString(messageWithStatusCode);
        out.println(message);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
        Integer userId = Integer.parseInt(request.getParameter("id"));
        int skillId = Integer.parseInt(request.getParameter("skillId"));
        try {
            SkillManager.removeSkillFromUser(skillId, UserManager.getUserByID(userId));
            messageWithStatusCode.setStatusCode(200);
            messageWithStatusCode.setMessage("UserSkill is removed successfully.");
        } catch (SkillException | UserException e) {
            response.setStatus(404);
            messageWithStatusCode.setStatusCode(404);
            messageWithStatusCode.setMessage(e.getMessage());
        }
        String message = mapper.writeValueAsString(messageWithStatusCode);
        out.println(message);
    }
}
