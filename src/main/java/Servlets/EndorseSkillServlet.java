package Servlets;

import DTOs.MessageWithStatusCode;
import Exceptions.SkillEndorsementException;
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

@WebServlet("/users/endorseskill")
public class EndorseSkillServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String endorserId = request.getParameter("endorserId");
        String endorseeId = request.getParameter("id");
        String skillName = request.getParameter("skillName");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
        try {
            SkillManager.endorseSkillOfUser(skillName, endorserId,
                    UserManager.getUserByID(endorseeId));
            messageWithStatusCode.setStatusCode(200);
            messageWithStatusCode.setMessage("Skill is endorsed successfully.");
        } catch (UserException | SkillException e) {
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
}
