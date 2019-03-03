package Servlets;

import Exceptions.ProjectNotFoundException;
import Exceptions.UserNotFoundException;
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
        }catch (UserNotFoundException | ProjectNotFoundException e){
            e.printStackTrace();
        }
        response.sendRedirect("/project/"+ projectId);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/bid.jsp").forward(request, response);
    }
}
