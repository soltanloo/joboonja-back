package Servlets;

import DTOs.MessageWithStatusCode;
import Exceptions.BidException;
import Exceptions.ProjectException;
import Exceptions.UserException;
import Joboonja.ProjectManager;
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

@WebServlet("/projects/bids")
public class BidServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
        int bidAmount = Integer.valueOf(request.getParameter("bidAmount"));
        String projectId = request.getParameter("id");
        try {
            ProjectManager.addBidToProject(bidAmount, UserManager.getCurrentUser().getId(), projectId);
            messageWithStatusCode.setStatusCode(200);
            messageWithStatusCode.setMessage("Bid is submitted successfully.");
        } catch (UserException | ProjectException e){
            response.setStatus(404);
            messageWithStatusCode.setStatusCode(404);
            messageWithStatusCode.setMessage(e.getMessage());
        } catch (BidException e) {
            response.setStatus(400);
            messageWithStatusCode.setStatusCode(400);
            messageWithStatusCode.setMessage(e.getMessage());
        }
        String message = mapper.writeValueAsString(messageWithStatusCode);
        out.println(message);
    }
}
