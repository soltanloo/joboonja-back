package Servlets;

import Exceptions.ProjectException;
import Exceptions.UserException;
import Joboonja.ProjectManager;
import Joboonja.UserManager;
import Models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/project/bid")
public class BidServlet extends HttpServlet {

    private User currentUser = UserManager.getCurrentUser();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bidAmount = Integer.valueOf(request.getParameter("bidAmount"));
        String projectId = request.getParameter("projectId");
        try {
            ProjectManager.addBidToProject(bidAmount, currentUser.getId(), projectId);
            response.sendRedirect("/project/"+ projectId);
        } catch (UserException | ProjectException e){
            response.setStatus(404);
            request.setAttribute("message",
                    e.getMessage());
            request.getRequestDispatcher("/404.jsp").forward(request, response);
        }

    }
}
